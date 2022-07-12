package io.github.hlg212.cas.handler;

import io.github.hlg212.fcf.api.Constants;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.security.web.authentication.logout.ForwardLogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CusLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

    private RequestMatcher matcher = new RequestHeaderRequestMatcher("X-Requested-With", "XMLHttpRequest");

    private ForwardLogoutSuccessHandler forwardLogoutSuccessHandler = new ForwardLogoutSuccessHandler("/logoutSucc");

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        if( !matcher.matches(request) )
        {
            super.onLogoutSuccess(request, response, authentication);
            return;
        }
        forwardLogoutSuccessHandler.onLogoutSuccess(request,response,authentication);
    }
}
