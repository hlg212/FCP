package io.github.hlg212.gateway.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
/**
 * @description: 获取认证 token 的帮助类
 * @author: huangligui
 * @create: 2018-10-26 10:30
 **/
public class TokenHelper {

    public static final String TOKEN_KEY = "Authorization";
    public static final String BEARER = "bearer ";

    public static final String PARAM_TOKEN_KEY = "__AuthToken";

    public static String getToken(ServerWebExchange exchange)
    {
        String token = extractToken(exchange.getRequest());
        if( StringUtils.isEmpty(token) )
        {
            HttpCookie ck =  exchange.getRequest().getCookies().getFirst(TOKEN_KEY);
           if( ck != null && StringUtils.isNotEmpty(ck.getValue()) )
            {
                token = ck.getValue();
            }
            else{
               token = exchange.getRequest().getQueryParams().getFirst(PARAM_TOKEN_KEY);
           }
        }
        return token;
    }

    private static String extractToken(ServerHttpRequest request) {
        String token = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isBlank(token) || !token.toLowerCase().startsWith(BEARER)) {
            return null;
        }
        return token.substring(BEARER.length());
    }
}
