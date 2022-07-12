package io.github.hlg212.cas.conf;

import io.github.hlg212.cas.handler.CacheAuthorizationCodeServices;
import io.github.hlg212.cas.handler.oauth.*;
import io.github.hlg212.cas.listener.TokenMessageListener;
import io.github.hlg212.cas.properties.OAuthTokenProperties;
import io.github.hlg212.cas.service.impl.MyClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.util.ClassUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableConfigurationProperties(OAuthTokenProperties.class)
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MyClientDetailsService clicentDetailsService;

    @Autowired
    private OAuthTokenProperties oAuthTokenProperties;

   @Autowired
   @Lazy
   private TokenStore tokenStore;

    @Autowired(required = false)
    @Lazy
    private CacheAuthorizationCodeServices cacheAuthorizationCodeServices;

    @Bean
    @ConditionalOnClass(name = "org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession")
    @ConditionalOnBean(RedisConnectionFactory.class)
    public RedisTokenStore redisTokenStore(@Autowired RedisConnectionFactory redisConnectionFactory){
        return new RedisTokenStore(redisConnectionFactory);
    }

    @Bean
    @ConditionalOnClass(name = "org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession")
    public CacheAuthorizationCodeServices redisCacheAuthorizationCodeServices(){
        return new CacheAuthorizationCodeServices();
    }

    @Bean
    @ConditionalOnMissingBean
    public TokenStore inMemoryTokenStore(){

        return new InMemoryTokenStore();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
/*        clients.inMemory()
                .withClient("acme")
                .secret(passwordEncoder.encode("acmesecret"))
                .authorizedGrantTypes("authorization_code", "refresh_token",
                        "password").scopes("openid");*/
        clients.withClientDetails(clicentDetailsService);


    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints)
            throws Exception {
        endpoints.redirectResolver(new AntRedirectResolver());
        endpoints.authenticationManager(authenticationManager);

        if( tokenStore != null ){
            endpoints.tokenStore(tokenStore);
        }

                //.userDetailsService(userDetailsService);
        endpoints.setClientDetailsService(clicentDetailsService);
        AuthorizationServerTokenServices tokenServices = delegateAuthorizationServerTokenServices(endpoints.getDefaultAuthorizationServerTokenServices());
        endpoints.tokenServices( tokenServices );

        if( cacheAuthorizationCodeServices != null )
        {
            endpoints.authorizationCodeServices(cacheAuthorizationCodeServices);
        }

        endpoints.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
        endpoints.exceptionTranslator(new CusWebResponseExceptionTranslator());
        TokenGranter tokenGranter = endpoints.getTokenGranter();
        List<TokenGranter> tokenGranters = new ArrayList();
        tokenGranters.add(tokenGranter);
        tokenGranters.add(new AccountTokenGranter(tokenServices,clicentDetailsService,endpoints.getOAuth2RequestFactory(),authenticationManager));

        CompositeTokenGranter ctg = new CompositeTokenGranter(tokenGranters);
        endpoints.tokenGranter(ctg);

    }




    @Bean
    public AuthorizationServerTokenServices delegateAuthorizationServerTokenServices(AuthorizationServerTokenServices authorizationServerTokenServices)
    {
        if( authorizationServerTokenServices instanceof DefaultTokenServices && oAuthTokenProperties != null )
        {
            ((DefaultTokenServices) authorizationServerTokenServices).setAccessTokenValiditySeconds(oAuthTokenProperties.getAccessTokenValiditySeconds());
            ((DefaultTokenServices) authorizationServerTokenServices).setRefreshTokenValiditySeconds(oAuthTokenProperties.getRefreshTokenValiditySeconds());
        }
        return new DelegateAuthorizationServerTokenServices(authorizationServerTokenServices);

    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer)
            throws Exception {
        
        oauthServer.allowFormAuthenticationForClients();

        oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess(
                "isAuthenticated()");
    }


    @Bean
    @ConditionalOnClass(name = "org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession")
    @ConditionalOnBean(RedisConnectionFactory.class)
    public RedisMessageListenerContainer tokenRedisMessageListenerContainer(@Autowired RedisConnectionFactory redisConnectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);

        int database = resolveDatabase(redisConnectionFactory);
        String delTopic = "__keyevent@" + database + "__:del";
        String expiredTopic = "__keyevent@" + database + "__:expired";

        container.addMessageListener(tokenMessageListener(), Arrays.asList(new ChannelTopic(delTopic), new ChannelTopic(expiredTopic)));
        return container;
    }

    @Bean
    public TokenMessageListener tokenMessageListener()
    {
        return new TokenMessageListener();
    }

    private int resolveDatabase(RedisConnectionFactory redisConnectionFactory) {
        if (ClassUtils.isPresent("io.lettuce.core.RedisClient", (ClassLoader)null) && redisConnectionFactory instanceof LettuceConnectionFactory) {
            return ((LettuceConnectionFactory)redisConnectionFactory).getDatabase();
        } else {
            return ClassUtils.isPresent("redis.clients.jedis.Jedis", (ClassLoader)null) && redisConnectionFactory instanceof JedisConnectionFactory ? ((JedisConnectionFactory)redisConnectionFactory).getDatabase() : 0;
        }
    }
}
