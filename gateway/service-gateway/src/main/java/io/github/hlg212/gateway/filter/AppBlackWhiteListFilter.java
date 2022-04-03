package io.github.hlg212.gateway.filter;

import io.github.hlg212.fcf.model.basic.BlackWhiteList;
import io.github.hlg212.gateway.annotation.AppBlackWhiteListConditional;
import io.github.hlg212.gateway.service.BlackWhiteListService;
import io.github.hlg212.gateway.util.BlackWhiteListUtil;
import io.github.hlg212.gateway.util.CheckBlackWhiteListUtil;
import io.github.hlg212.gateway.util.Constants;
import io.github.hlg212.gateway.util.ServerWebExchangeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.PathContainer;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @description: 黑白名单验证
 * @author: wuwei
 * @create: 2019-6-14
 **/
@Component
@Order(Constants.WEB_FILTER_APPBLACKWHITELIST_ORDER)
@AppBlackWhiteListConditional
public class AppBlackWhiteListFilter implements WebFilter {

    @Autowired
    private BlackWhiteListService blackWhiteListService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String ip = ServerWebExchangeUtil.getIp(exchange);
        List<PathContainer.Element> elements = exchange.getRequest().getPath().elements();
        if( elements.size() > 1 ) {
            String appCode = elements.get(1).value();
            List<BlackWhiteList> hbmds = getBlackWhiteListByAppCode(appCode);

            if (CheckBlackWhiteListUtil.check(ip, hbmds)) {
                return BlackWhiteListUtil.thrLimitExc(exchange);
            }
        }
        return chain.filter(exchange);
    }

    private List<BlackWhiteList> getBlackWhiteListByAppCode(String appCode){
        try{
            return blackWhiteListService.getBlackWhiteListByAppCode(appCode);
        }catch (Exception e){
            return null;
        }
    }
}