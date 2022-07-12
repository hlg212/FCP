package io.github.hlg212.basic.service.impl;

import io.github.hlg212.basic.model.bo.AppBo;
import io.github.hlg212.basic.model.qco.AppQco;
import io.github.hlg212.basic.service.AppService;
import org.springframework.stereotype.Service;

/** 
 * 应用Service
 *
 * @author huanglg
 * @date 2022-03-28
 */
@Service
public class AppServiceImpl implements AppService{

    @Override
    public AppBo getAppByCode(String code) {
        AppQco qco = new AppQco();
        qco.setCode(code);
        return get(qco);
    }
}
