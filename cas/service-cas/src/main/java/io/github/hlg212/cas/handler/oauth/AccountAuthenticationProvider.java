package io.github.hlg212.cas.handler.oauth;

import io.github.hlg212.fcf.util.ExceptionHelper;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public class AccountAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    public AccountAuthenticationProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name =  authentication.getName();
        UserDetails ud =  userDetailsService.loadUserByUsername(name);
        if( ud == null )
        {
            ExceptionHelper.throwBusinessException("找不到用户["+name+"]");
        }
        AccountAuthenticationToken auth = new AccountAuthenticationToken(authentication.getName(),ud.getAuthorities());
        auth.setAuthenticated(true);
        return auth;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AccountAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
