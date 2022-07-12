package io.github.hlg212.cas.handler.oauth;

import io.github.hlg212.cas.util.ExceptionMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;

public class CusWebResponseExceptionTranslator  extends DefaultWebResponseExceptionTranslator {

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {

        String msg = ExceptionMap.getExceptionMsg(e);
        if(StringUtils.isNotEmpty(msg))
        {
            if( e instanceof  OAuth2Exception) {
                e = OAuth2Exception.create(((OAuth2Exception) e).getOAuth2ErrorCode(), msg);
            }
            else
            {
                e = OAuth2Exception.create(((OAuth2Exception) e).getOAuth2ErrorCode(), msg);
            }
        }

        return   super.translate(e);
    }
}
