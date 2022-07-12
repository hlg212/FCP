package io.github.hlg212.cas.util;

import io.github.hlg212.cas.bo.LoginPasswordErrorInfoBo;
import io.github.hlg212.fcf.util.MessageSourceAccessorHelper;
import io.github.hlg212.fcf.web.util.HttpServletHelper;
import org.springframework.stereotype.Component;

@Component
public class PasswordErrorLimitHelper {


    public static String getErrorMessage()
    {
        String msg = null;
        LoginPasswordErrorInfoBo infoBo = (LoginPasswordErrorInfoBo)HttpServletHelper.getRequest().getAttribute(Constants.AUTHENTICATION_LOGINPASSWORDERRORINFO);
        if( infoBo != null )
        {
            if( infoBo.getCurrNum() > 1 )
            {
                msg = MessageSourceAccessorHelper.getMessage(Constants.ErrorMessage.loginPasswordErrorMessage,new Object[]{infoBo.getCurrNum(),infoBo.getMaxErrorNum()});
               // msg = String.format( Constants.LOGIN_PASSWORD_ERROR_MESSAGE,infoBo.getCurrNum(),infoBo.getMaxErrorNum());
            }
        }
        return msg;
    }

}
