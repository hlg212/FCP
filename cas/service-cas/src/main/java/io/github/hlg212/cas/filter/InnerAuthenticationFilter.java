package io.github.hlg212.cas.filter;

import io.github.hlg212.cas.handler.oauth.AccountAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class InnerAuthenticationFilter extends AbstractAuthenticationProcessingFilter {


    public InnerAuthenticationFilter() {
        super(new AntPathRequestMatcher("/accountLogin", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String account =  request.getParameter("account");
        String value = request.getHeader(io.github.hlg212.fcf.web.util.Constants.FEIGN_API_SECRET_KEY);
        if( !io.github.hlg212.fcf.web.util.Constants.FEIGN_API_SECRET_VALUE.equals(value)    )
        {
           throw  new BadCredentialsException("没有权限调动接口!");
        }
        return getAuthenticationManager().authenticate(new AccountAuthenticationToken(account));

    }
}
