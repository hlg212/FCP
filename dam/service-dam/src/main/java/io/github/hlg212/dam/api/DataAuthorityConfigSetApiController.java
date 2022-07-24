package io.github.hlg212.dam.api;

import io.github.hlg212.dam.model.bo.DamConfigBo;
import io.github.hlg212.dam.model.qco.DamConfigQco;
import io.github.hlg212.dam.service.DamConfigService;
import io.github.hlg212.fcf.api.dam.DataAuthorityConfigSetApi;
import io.github.hlg212.fcf.model.Constants;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(value = "数据权限配置api控制器", tags = {"数据权限配置api控制器"})
public class DataAuthorityConfigSetApiController implements DataAuthorityConfigSetApi<DamConfigBo> {

    @Autowired
    private DamConfigService damConfigService;
    @Override
    public List getDataAuthorityConfigSetByCode(String code) {
        DamConfigQco qco = new DamConfigQco();
        qco.setIsEnabled(Constants.BOOLEAN_Y);
        qco.setAppId(code);
        return damConfigService.find(qco);
    }
}
