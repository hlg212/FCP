package io.github.hlg212.cas.conf;

import io.github.hlg212.cas.filter.CookieSecurityContextPersistenceFilter;
import io.github.hlg212.cas.filter.DelegateFworkContextFilter;
import io.github.hlg212.cas.filter.InnerAuthenticationFilter;
import io.github.hlg212.cas.handler.*;
import io.github.hlg212.cas.handler.oauth.AccountAuthenticationProvider;
import io.github.hlg212.cas.properties.CasProperties;
import io.github.hlg212.cas.service.impl.MyUserDetailsService;
import io.github.hlg212.fcf.util.ExceptionHelper;
import io.github.hlg212.fcf.web.filter.Constants;
import io.github.hlg212.fcf.web.util.HttpServletHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.reflect.FieldUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.ExceptionMappingAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.SessionRepository;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;
import org.springframework.util.Base64Utils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements Ordered {

    /*@Autowired
    private AuthenticationManager authenticationManager;*/

    @Autowired
    private MyUserDetailsService userDetailsFitService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private LogoutHandler logoutHandler;
    @Autowired
    private SessionRegistry sessionRegistry;
    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private CasProperties casProperties;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        DelegateAuthenticationManager authenticationManager = new DelegateAuthenticationManager(super.authenticationManager());
        return authenticationManager;
    }

    @Bean
    public LogoutHandler logoutHandler() {
        return new LogoutHandler();
    }


///*    @Bean
//    public static PasswordEncoder passwordEncoder() throws Exception {
//        //return new MessageDigestPasswordEncoder("MD5");
//        return PasswordUtils.md5;
//       // return PasswordUtils.bcrypt;
//    }*/


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // http.formLogin().loginPage("/login").permitAll().and().authorizeRequests()
        //         .anyRequest().authenticated();
        //super.configure(http);
        // http.requestMatchers().antMatchers("/","/oauth/authorize","/login","/views/**","/layuiadmin/**","/logout","/actuator/**");

        http.requestMatchers().antMatchers(casProperties.getWebRequestMatchers().toArray(new String[0]));
        if (casProperties.getConcurrencyLoginControl() != null && casProperties.getConcurrencyLoginControl()) {
            if (sessionRegistry != null) {
                http.sessionManagement()
                        .withObjectPostProcessor(new ObjectPostProcessor<ConcurrentSessionControlAuthenticationStrategy>() {
                            @Override
                            public <O extends ConcurrentSessionControlAuthenticationStrategy> O postProcess(O o) {
                                CusConcurrentSessionControlAuthenticationStrategy cus = new CusConcurrentSessionControlAuthenticationStrategy(sessionRegistry, sessionRepository);
                                cus.setMaximumSessions(1);
                                cus.setExceptionIfMaximumExceeded(true);

                                return (O) cus;
                            }
                        })

                        .maximumSessions(1)

                        .sessionRegistry(sessionRegistry)
                        .maxSessionsPreventsLogin(true)
                        .expiredUrl(StringUtils.isNotEmpty(casProperties.getLoginPage()) ? casProperties.getLoginPage() : null);
            } else {
                http.sessionManagement()

                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(true)
                        .expiredUrl(StringUtils.isNotEmpty(casProperties.getLoginPage()) ? casProperties.getLoginPage() : null);
            }
        }
        // http.rememberMe().rememberMeServices()
        http.exceptionHandling();
        http.httpBasic().and()
                .logout().logoutSuccessHandler(simpleUrlLogoutSuccessHandler()).addLogoutHandler(logoutHandler).and()
                .cors().and()
                .csrf().disable();

        if (casProperties.getAnonymous() != null) {
            http.authorizeRequests().antMatchers(casProperties.getAnonymous().toArray(new String[0])).permitAll();
        }

        http.authorizeRequests().anyRequest().authenticated();
        if (StringUtils.isNotEmpty(casProperties.getLoginPage())) {
            FormLoginConfigurer c = http.formLogin();
            //MethodUtils.invokeMethod(c,"setAuthenticationFilter",new CusUsernamePasswordAuthenticationFilter());
            FieldUtils.getField(c.getClass(), "authFilter", true).set(c, new CusUsernamePasswordAuthenticationFilter());
            // PropertyUtils.setProperty(c,"authFilter",new CusUsernamePasswordAuthenticationFilter());
            http.formLogin().successHandler(delegateAuthenticationSuccessHandler()).failureHandler(failureHandler()).loginPage(casProperties.getLoginPage()).loginProcessingUrl("/login").permitAll();
        } else {
            http.formLogin().successHandler(delegateAuthenticationSuccessHandler()).failureHandler(failureHandler()).loginProcessingUrl("/login").permitAll();
        }

        http.addFilterAfter(cookieSecurityContextPersistenceFilter(), SecurityContextPersistenceFilter.class);
        // http.addFilterBefore(cookieAuthenticationFilter(),UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(innerAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }


    @Bean
    public CookieSecurityContextPersistenceFilter cookieSecurityContextPersistenceFilter() {
        CookieSecurityContextPersistenceFilter f = new CookieSecurityContextPersistenceFilter();
        return f;
    }


    @Bean
    public InnerAuthenticationFilter innerAuthenticationFilter() {
        InnerAuthenticationFilter f = new InnerAuthenticationFilter();
        try {
            f.setAuthenticationManager(authenticationManagerBean());
            f.setAuthenticationSuccessHandler(delegateAuthenticationSuccessHandler());
            f.setAuthenticationFailureHandler(failureHandler());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return f;
    }

    @Bean
    public DelegateAuthenticationSuccessHandler delegateAuthenticationSuccessHandler() {
        return new DelegateAuthenticationSuccessHandler();

    }

    @Bean
    public AuthenticationFailureHandler failureHandler() {
        ExceptionMappingAuthenticationFailureHandler handler = new ExceptionMappingAuthenticationFailureHandler();
        Map failureUrlMap = new HashMap();
        String loginPage = casProperties.getLoginPage();
        if (StringUtils.isEmpty(loginPage)) {
            loginPage = "/login";
        }
        String failUrl = loginPage + "?error=00";

        failureUrlMap.put(SessionAuthenticationException.class.getName(), failUrl);
        failUrl = loginPage + "?error=01";
        failureUrlMap.put(LockedException.class.getName(), failUrl);

        handler.setDefaultFailureUrl(loginPage + "?error=");
        handler.setExceptionMappings(failureUrlMap);

        handler.setRedirectStrategy(new LoginErrorRedirectStrategy());

        DelegateAuthenticationFailureHandler delegateAuthenticationFailureHandler = new DelegateAuthenticationFailureHandler();
        delegateAuthenticationFailureHandler.setExceptionHandler(handler);
        return delegateAuthenticationFailureHandler;
    }

    @Bean
    @ConditionalOnClass(name = "org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession")
    @ConditionalOnBean(RedisOperationsSessionRepository.class)
    public SpringSessionBackedSessionRegistry sessionRegistry(@Autowired RedisOperationsSessionRepository redisOperationsSessionRepository) {
        return new SpringSessionBackedSessionRegistry<>(redisOperationsSessionRepository);
    }

    @Bean
    @ConditionalOnMissingBean
    public SessionRegistry defSessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public SessionRepository defSessionRepository() {
        return new MapSessionRepository(new ConcurrentHashMap<>(1024));
    }

    private SimpleUrlLogoutSuccessHandler simpleUrlLogoutSuccessHandler() {
        SimpleUrlLogoutSuccessHandler sh = new CusLogoutSuccessHandler();
        sh.setTargetUrlParameter("redirect");
        return sh;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth.authenticationProvider();
        auth.authenticationProvider(new AccountAuthenticationProvider(userDetailsFitService));
        auth.userDetailsService(userDetailsFitService).passwordEncoder(passwordEncoder);

        //  auth.authenticationProvider( new CusAuthenticationProvider() );
        //auth.parentAuthenticationManager(new CusAuthenticationManager());

        //auth.parentAuthenticationManager(authenticationManager);
    }


    @Bean
    public DelegateFworkContextFilter delegateFworkContextFilter() {
        return new DelegateFworkContextFilter();
    }

    @Bean
    public FilterRegistrationBean delegateFworkContextFilterReg(DelegateFworkContextFilter delegateFworkContextFilter) {
        FilterRegistrationBean registration = new FilterRegistrationBean(delegateFworkContextFilter);
        registration.addUrlPatterns("/*");
        registration.setOrder(Constants.Order.AccessLogFilter);
        return registration;
    }

    @Override
    public int getOrder() {
        return 1;
    }

    private class CusUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
        private static final String PARAM_ENCODE = "encode";
        private static final String PARAM_USERNAMEPASSWORD = "usernamePassword";

        @Override
        protected String obtainUsername(HttpServletRequest request) {
            String encode = request.getParameter(PARAM_ENCODE);
            boolean isEncode = StringUtils.isNotEmpty(encode) && !"false".equals(encode);
            if (isEncode) {
                String usernamePassword[] = getUsernamePassword();
                if (usernamePassword.length > 0) {
                    return usernamePassword[0];
                }
                return "";
            }

            return super.obtainUsername(request);
        }

        private String[] getUsernamePassword() {
            String usernamePassword = HttpServletHelper.getRequest().getParameter(PARAM_USERNAMEPASSWORD);

            try {
                String decodeStr = new String(Base64Utils.decodeFromString(usernamePassword), "utf-8");
                return decodeStr.split(" ");
            } catch (UnsupportedEncodingException e) {
                ExceptionHelper.throwBusinessException("账号密码不正确!");
            }
            return null;
        }

        @Override
        protected String obtainPassword(HttpServletRequest request) {
            String encode = request.getParameter(PARAM_ENCODE);
            boolean isEncode = StringUtils.isNotEmpty(encode) && !"false".equals(encode);
            if (isEncode) {
                String usernamePassword[] = getUsernamePassword();
                if (usernamePassword.length > 1) {
                    return usernamePassword[1];
                }
                return "";
            }

            return super.obtainPassword(request);
        }
    }


}
