package io.github.hlg212.gateway.service;

import io.github.hlg212.fcf.model.ga.IRoute;

import java.util.List;

/**
 * @description: <功能描述>
 * @author: huangligui
 * @create: 2019-03-12 13:30
 **/
public interface RouteService {


    public void save(IRoute route);

    public void deleteById(String id);
}
