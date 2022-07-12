package io.github.hlg212.cas.handler;


import io.github.hlg212.cas.util.ExceptionMap;
import io.github.hlg212.cas.util.PasswordErrorLimitHelper;
import io.github.hlg212.fcf.web.util.HttpServletHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.WebAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

public class LoginErrorRedirectStrategy extends DefaultRedirectStrategy {

    @Override
    public void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url) throws IOException {

        String str = PasswordErrorLimitHelper.getErrorMessage();
        Throwable t = (Throwable)HttpServletHelper.getRequest().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        String error_info = null;
        if(StringUtils.isNotEmpty(str))
        {
            error_info = str;
        }
        else if( t != null )
        {
            error_info = ExceptionMap.getExceptionMsg(t);
        }
        if( error_info != null )
        {
            url = url + "&error_info=" + URLEncoder.encode(error_info,"UTF-8");
        }

        super.sendRedirect(request, response, url);
    }
}
