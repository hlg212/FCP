package io.github.hlg212.gateway.handler;

import io.github.hlg212.fcf.model.basic.BlackWhiteList;
import io.github.hlg212.gateway.annotation.UserBlackWhiteListConditional;
import io.github.hlg212.gateway.exception.UserIpRangeNotLlowedException;
import io.github.hlg212.gateway.service.BlackWhiteListService;
import io.github.hlg212.gateway.util.CheckBlackWhiteListUtil;
import io.github.hlg212.gateway.util.ServerWebExchangeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.WebFilterChainServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@UserBlackWhiteListConditional
public class UserIpBlackWhiteListAuthenticationSuccessHandler extends WebFilterChainServerAuthenticationSuccessHandler {

    @Autowired
    private BlackWhiteListService blackWhiteListService;

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        String ip = ServerWebExchangeUtil.getIp(webFilterExchange.getExchange());
        String username = authentication.getName();
        List<BlackWhiteList> hbmds = getBlackWhiteListByAccount(username);
        if(CheckBlackWhiteListUtil.check(ip, hbmds)){
            return Mono.error(new UserIpRangeNotLlowedException(String.format("用户[%s]IP被限制!",username)));
        }
        return super.onAuthenticationSuccess(webFilterExchange, authentication);
    }


    private List<BlackWhiteList> getBlackWhiteListByAccount(String username){
        try{
            if( blackWhiteListService == null )
            {
                return null;
            }
            return blackWhiteListService.getBlackWhiteListByAccount(username);
        }catch (Exception e){
            return null;
        }
    }

}
