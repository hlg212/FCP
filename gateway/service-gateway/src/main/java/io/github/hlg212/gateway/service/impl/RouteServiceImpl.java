package io.github.hlg212.gateway.service.impl;

import io.github.hlg212.fcf.model.ga.IRoute;
import io.github.hlg212.fcf.util.EventPublisherHelper;
import io.github.hlg212.fcf.util.SpringEventPublisherHelper;
import io.github.hlg212.gateway.service.RouteService;
import io.github.hlg212.gateway.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: <功能描述>
 * @author: huangligui
 * @create: 2019-03-13 09:39
 **/
@Service
@Slf4j
public class RouteServiceImpl implements RouteService {

    @Resource
    private RouteDefinitionWriter routeDefinitionWriter;


    @Override
    public void save(IRoute route) {
        RouteDefinition definition = new RouteDefinition();
        PredicateDefinition predicate = new PredicateDefinition();
        Map<String, String> predicateParams = new HashMap<>(8);

        definition.setId(Constants.CUS_ROUTE + route.getId());
        predicate.setName("AntPath");

        predicateParams.put("pattern", route.getMatchPath().replaceAll(",","\\|") );
        predicate.setArgs(predicateParams);
        if( StringUtils.isNotEmpty(route.getNmatchPath() )  )
        {

            PredicateDefinition predicate2 = new PredicateDefinition();
            predicate2.setName("NantPath");
            Map<String, String> predicateParams2 = new HashMap<>(8);
            predicateParams2.put("pattern",route.getNmatchPath().replaceAll(",","\\|"));
            predicate2.setArgs(predicateParams2);

            definition.setPredicates(Arrays.asList(predicate,predicate2));
        }
        else
        {
            definition.setPredicates(Arrays.asList(predicate));
        }

        URI uri = UriComponentsBuilder.fromHttpUrl(route.getUri()).build().toUri();
        definition.setUri(uri);
        int order = 10000;
        int rorder = 100;
        if( route.getOrder() != null )
        {
            rorder = Integer.valueOf(route.getOrder().toString());
        }
        order = (order - rorder) * -1;


        definition.setOrder(order);
        routeDefinitionWriter.save(Mono.just(definition)).subscribe();
        notifyChanged();
    }

    private void notifyChanged() {
        SpringEventPublisherHelper.publish(new RefreshRoutesEvent(this));
    }

        @Override
    public void deleteById(String id) {

        try {
            routeDefinitionWriter.delete(Mono.just(Constants.CUS_ROUTE + id)).subscribe();
            notifyChanged();
        }catch (Exception e)
        {
            log.warn("{} ,route 不存在 id:{}",e.getMessage(),id);
        }
    }


    /**
     * 增加路由
     *
     */
/*    public String add(RouteDefinition definition) {


      *//*  RouteDefinition definition = new RouteDefinition();
        PredicateDefinition predicate = new PredicateDefinition();
        Map<String, String> predicateParams = new HashMap<>(8);

        definition.setId("baiduRoute");
        predicate.setName("Path");
        predicateParams.put("pattern", "/baidu");
        predicateParams.put("pathPattern", "/baidu");
        predicate.setArgs(predicateParams);
        definition.setPredicates(Arrays.asList(predicate));
        URI uri = UriComponentsBuilder.fromHttpUrl("http://www.baidu.com").build().toUri();
        definition.setUri(uri);
        routeDefinitionWriter.save(Mono.just(definition)).subscribe();*//*

        routeDefinitionWriter.save(Mono.just(definition)).subscribe();
        return "success";
    }*/


    /**
     * 更新路由
     */
/*    public String update(RouteDefinition definition) {
        try {
            this.routeDefinitionWriter.delete(Mono.just(definition.getId()));
        } catch (Exception e) {
            return "update fail,not find route  routeId: " + definition.getId();
        }
        try {
            routeDefinitionWriter.save(Mono.just(definition)).subscribe();
            return "success";
        } catch (Exception e) {
            return "update route  fail";
        }


    }*/

    /**
     * 删除路由
     *
     */
 /*   public String delete(String id) {
        try {
            this.routeDefinitionWriter.delete(Mono.just(id));
            return "delete success";
        } catch (Exception e) {
            e.printStackTrace();
            return "delete fail";
        }*/

}
