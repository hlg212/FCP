package io.github.hlg212.cas.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.ForwardAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DelegateAuthenticationSuccessHandler implements AuthenticationSuccessHandler {


    private RequestMatcher requestMatcher =  new RequestHeaderRequestMatcher("X-Requested-With", "XMLHttpRequest");

    private ForwardAuthenticationSuccessHandler forwardAuthenticationSuccessHandler = new ForwardAuthenticationSuccessHandler("/loginRest/succ");

    private SavedRequestAwareAuthenticationSuccessHandler savedRequestAwareAuthenticationSuccessHandler = new SavedRequestAwareAuthenticationSuccessHandler();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        if( requestMatcher.matches(request) )
        {
            forwardAuthenticationSuccessHandler.onAuthenticationSuccess(request,response,authentication);
            return;
        }
        savedRequestAwareAuthenticationSuccessHandler.onAuthenticationSuccess(request, response, authentication);
    }
}
