package io.github.hlg212.cas.handler;

import io.github.hlg212.fcf.web.util.HttpServletHelper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.ExceptionMappingAuthenticationFailureHandler;
import org.springframework.security.web.authentication.ForwardAuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DelegateAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private RequestMatcher requestMatcher =  new RequestHeaderRequestMatcher("X-Requested-With", "XMLHttpRequest");

    private ExceptionMappingAuthenticationFailureHandler exceptionHandler;

    private ForwardAuthenticationFailureHandler forwardAuthenticationFailureHandler = new ForwardAuthenticationFailureHandler("/loginRest/fail");


    public void setExceptionHandler(ExceptionMappingAuthenticationFailureHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    public void setForwardAuthenticationFailureHandler(ForwardAuthenticationFailureHandler forwardAuthenticationFailureHandler) {
        this.forwardAuthenticationFailureHandler = forwardAuthenticationFailureHandler;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {


        if( requestMatcher.matches(request) )
        {
            forwardAuthenticationFailureHandler.onAuthenticationFailure(request,response,exception);
            return;
        }
        request.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION,exception);
        exceptionHandler.onAuthenticationFailure(request,response,exception);
    }
}
