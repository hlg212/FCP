package io.github.hlg212.gateway.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;

@Slf4j
public class ServerWebExchangeUtil {
    public static String getIp(ServerWebExchange exchange){
        String ip = null;
        HttpHeaders httpHeaders =  exchange.getRequest().getHeaders();
        ip = httpHeaders.getFirst("X-Forwarded-For") ;
        if(  StringUtils.isEmpty(ip ) || "unknown".equalsIgnoreCase(ip)  ){
            ip  = httpHeaders.getFirst("Proxy-Client-IP");
        }
        if(  StringUtils.isEmpty(ip ) || "unknown".equalsIgnoreCase(ip)  ){
            ip = httpHeaders.getFirst("WL-Proxy-Client-IP");
        }
        if(  StringUtils.isEmpty(ip ) || "unknown".equalsIgnoreCase(ip) ){
            ip = httpHeaders.getFirst("HTTP-Client-IP");
        }
        if(  StringUtils.isEmpty(ip ) || "unknown".equalsIgnoreCase(ip)  ){
            ip = httpHeaders.getFirst("HTTP_X_FORWARDED-FOR");
        }
        if( StringUtils.isEmpty(ip ) || "unknown".equalsIgnoreCase(ip) ){
            ip = exchange.getRequest().getRemoteAddress().getAddress().toString().replace("/","");
        }
        log.debug("client ip : {}",ip);
        if(  StringUtils.isNotEmpty(ip) )
        {
            String ips[] = ip.split(",");
            ip = ips[0];
        }
        return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
    }
}
