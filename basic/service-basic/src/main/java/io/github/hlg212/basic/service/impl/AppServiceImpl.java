package io.github.hlg212.basic.service.impl;

import io.github.hlg212.basic.model.bo.AppBo;
import io.github.hlg212.basic.model.bo.AppRoleBo;
import io.github.hlg212.basic.model.bo.DictBo;
import io.github.hlg212.basic.service.AppRoleService;
import io.github.hlg212.basic.service.AppService;
import io.github.hlg212.basic.service.DictService;
import io.github.hlg212.fcf.service.impl.CurdServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** 
 * 应用Service
 *
 * @author huanglg
 * @date 2022-03-28
 */
@Service
public class AppServiceImpl implements AppService{

    @Autowired
    AppRoleService appRoleService;

    @Autowired
    DictService dictService;

    @Override
    public AppBo save(AppBo appBo) {
        DictBo db = new DictBo();
        db.setCode("12");
        dictService.save(db);


        AppRoleBo appRoleBo = new AppRoleBo();
        appRoleBo.setAppId("12");
        try {
            appRoleService.save1(appRoleBo);
        }catch (Exception e)
        {

        }
        return AppService.super.save(appBo);
    }
}
