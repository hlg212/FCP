package io.github.hlg212.cas.conf;

import io.github.hlg212.cas.handler.ResFilterInvocationSecurityMetadataSource;
import io.github.hlg212.cas.properties.CasProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.vote.AbstractAccessDecisionManager;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

@Configuration
@EnableResourceServer
public class ResouceSecurityConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private CasProperties casProperties;

    @Autowired(required = false)
    private ResFilterInvocationSecurityMetadataSource resFilterInvocationSecurityMetadataSource;


    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.cors().and().headers().frameOptions().disable();
        http.authorizeRequests().requestMatchers(new ApiRequestMatcher()).permitAll();
        if( casProperties.getAnonymous() != null )
        {
            http.authorizeRequests().antMatchers(casProperties.getAnonymous().toArray(new String[0])).permitAll();
        }

        // 一般是集成项目，开启权限验证模块
        if( resFilterInvocationSecurityMetadataSource != null )
        {
            http.authorizeRequests().withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {

                @Override
                public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                    resFilterInvocationSecurityMetadataSource.setSuperMetadataSource(o.getSecurityMetadataSource());
                    o.setSecurityMetadataSource(resFilterInvocationSecurityMetadataSource);
                    return o;
                }
            });

            http.authorizeRequests().withObjectPostProcessor(new ObjectPostProcessor<AccessDecisionManager>(){

                @Override
                public <O extends AccessDecisionManager> O postProcess(O o) {
                    if( o instanceof AbstractAccessDecisionManager)
                    {
                        ((AbstractAccessDecisionManager) o).getDecisionVoters().add(new AuthorityVoter() );
                    }

                    return o;
                }
            });
        }
        super.configure(http);
        /*http.antMatcher("/userinfo")
                .authorizeRequests().anyRequest().authenticated();*/
    }

    private static class  AuthorityVoter extends RoleVoter
    {
        public AuthorityVoter()
        {
            setRolePrefix("");
        }
    }

    private class ApiRequestMatcher implements RequestMatcher
    {
        @Override
        public boolean matches(HttpServletRequest request) {
            String value = request.getHeader(io.github.hlg212.fcf.web.util.Constants.FEIGN_API_SECRET_KEY);
            if( io.github.hlg212.fcf.web.util.Constants.FEIGN_API_SECRET_VALUE.equals(value)    )
            {
                return true;
            }
            return false;
        }
    }
}
