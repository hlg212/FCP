package io.github.hlg212.gateway.conf;


import io.github.hlg212.gateway.annotation.SecurityConditional;
import io.github.hlg212.gateway.handler.AuthenticationFailureHandler;
import io.github.hlg212.gateway.handler.CorsHttpStatusServerAccessDeniedHandler;
import io.github.hlg212.gateway.handler.UserIpBlackWhiteListAuthenticationSuccessHandler;
import io.github.hlg212.gateway.matcher.AnonymousServerWebExchangeMatcher;
import io.github.hlg212.gateway.matcher.NeServerWebExchangeMatcher;
import io.github.hlg212.gateway.matcher.RequestHeaderRequestMatcher;
import io.github.hlg212.gateway.oauth.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2ClientProperties;
import org.springframework.boot.autoconfigure.security.oauth2.authserver.AuthorizationServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.web.server.DelegatingServerAuthenticationEntryPoint;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;

@Configuration
@EnableWebFluxSecurity
@EnableConfigurationProperties(value = {OAuth2ClientProperties.class,AuthorizationServerProperties.class})
@SecurityConditional
public class OAuthSecurityConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private AuthorizationServerProperties authorizationServerProperties;

    @Autowired
    private OAuth2ClientProperties oauth2ClientProperties;
    @Autowired
    private AnonymousServerWebExchangeMatcher anonymousServerWebExchangeMatcher;
    @Autowired
    private SsoServerAuthenticationEntryPoint ssoServerAuthenticationEntryPoint;

    @Autowired(required = false)
    private UrlAuthorizationManager urlAuthorizationManager;

    @Autowired(required = false)
    private CorsHttpStatusServerAccessDeniedHandler corsHttpStatusServerAccessDeniedHandler;

    @Autowired(required = false)
    private UserIpBlackWhiteListAuthenticationSuccessHandler userIpBlackWhiteListAuthenticationSuccessHandler;

    @Bean
    SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity security) throws Exception {
        security.cors().disable();
        security.httpBasic().disable()
                .csrf().disable()
                .logout().disable()
                .formLogin().disable()
                .headers().frameOptions().disable().and()
//                .authorizeExchange().pathMatchers("/*/v2/api-docs").permitAll()
//                .pathMatchers("/v2/api-docs").permitAll()
//                .pathMatchers("/*/swagger-ui.html").permitAll()
//                .pathMatchers("/*/webjars/**").permitAll()
//                // .pathMatchers("/**").permitAll()
//                .pathMatchers("/cas/**").permitAll()
//                .pathMatchers("/sso/**").permitAll()
//                .pathMatchers("/hi").permitAll()
//                .pathMatchers("/login").permitAll()
//                .pathMatchers("/loginSucc").permitAll()
//                .pathMatchers("/favicon.ico").permitAll()
                .authorizeExchange()

                .matchers(anonymousServerWebExchangeMatcher).permitAll();
                //.anyExchange().permitAll();
                if( corsHttpStatusServerAccessDeniedHandler != null ) {
                    security.exceptionHandling().accessDeniedHandler(corsHttpStatusServerAccessDeniedHandler);
                }
                if( urlAuthorizationManager != null ) {
                    security.authorizeExchange().anyExchange().access(urlAuthorizationManager);
                }
                else {
                    security.authorizeExchange().anyExchange().authenticated();
                }
                //.anyExchange().authenticated();

        AuthenticationWebFilter authenticationFilter = new AuthenticationWebFilter(authenticationManager());
        authenticationFilter.setAuthenticationConverter(new OAuthTokenConverter());
        if( userIpBlackWhiteListAuthenticationSuccessHandler != null ) {
            authenticationFilter.setAuthenticationSuccessHandler(userIpBlackWhiteListAuthenticationSuccessHandler);
        }

        DelegatingServerAuthenticationEntryPoint entryPoint = new DelegatingServerAuthenticationEntryPoint(new DelegatingServerAuthenticationEntryPoint.DelegateEntry(new RequestHeaderRequestMatcher("X-Requested-With", "XMLHttpRequest"),new HttpStatusAjaxEntryPoint()));
        entryPoint.setDefaultEntryPoint(ssoServerAuthenticationEntryPoint);

        authenticationFilter.setAuthenticationFailureHandler(new AuthenticationFailureHandler(entryPoint));

        authenticationFilter.setRequiresAuthenticationMatcher(new NeServerWebExchangeMatcher(anonymousServerWebExchangeMatcher));

        security.addFilterAt(authenticationFilter, SecurityWebFiltersOrder.HTTP_BASIC);

        security.exceptionHandling().authenticationEntryPoint(entryPoint);

        return  security.build();

    }


    @Bean
    public SsoServerAuthenticationEntryPoint ssoServerAuthenticationEntryPoint()
    {
        return new SsoServerAuthenticationEntryPoint();
    }

    @Bean
    @ConditionalOnProperty(value = "urlAuthEnabled",prefix = "htcf.gateway.security",matchIfMissing = true)
    public UrlAuthorizationManager urlAuthorizationManager()
    {
        return new UrlAuthorizationManager();
    }

    @Bean
    @ConditionalOnProperty(value = "corsAccessDeniedEnabled",prefix = "htcf.gateway.security",matchIfMissing = true)
    public CorsHttpStatusServerAccessDeniedHandler corsHttpStatusServerAccessDeniedHandler()
    {
        return new CorsHttpStatusServerAccessDeniedHandler();
    }


    private ReactiveAuthenticationManager authenticationManager()
    {
        return new OAuth2AuthenticationManagerAdapter( tokenServices() );
    }

    @Bean
    public ResourceServerTokenServices tokenServices() {
        RemoteTokenServices tokenServices = new CacheRemoteTokenServices();
        tokenServices.setClientId(oauth2ClientProperties.getClientId());
        tokenServices.setClientSecret(oauth2ClientProperties.getClientSecret());
        tokenServices.setCheckTokenEndpointUrl(authorizationServerProperties.getCheckTokenAccess());
        return tokenServices;
    }

    @Bean
    public AuthenticationManager oauthManager(ResourceServerTokenServices tokenServices) {
        OAuth2AuthenticationManager oauthAuthenticationManager = new OAuth2AuthorityScopeAuthenticationManager();
        oauthAuthenticationManager.setResourceId("");
        oauthAuthenticationManager.setTokenServices(tokenServices);
        return oauthAuthenticationManager;
    }
}