package io.github.hlg212.basic.service;

import io.github.hlg212.fcf.service.impl.CurdServiceImpl;

import io.github.hlg212.basic.model.bo.AppBo;
import io.github.hlg212.fcf.service.impl.CurdieServiceImpl;

/** 
 * 应用Service
 *
 * @author huanglg
 * @date 2022-03-28
 */
public interface AppService extends CurdieServiceImpl<AppBo> {


    public AppBo getAppByCode(String code);
}
