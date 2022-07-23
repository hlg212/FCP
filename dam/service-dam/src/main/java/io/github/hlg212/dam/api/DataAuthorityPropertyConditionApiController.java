package io.github.hlg212.dam.api;

import io.github.hlg212.dam.model.bo.DamScopeConditionBo;
import io.github.hlg212.dam.model.qco.DamScopeConditionQco;
import io.github.hlg212.dam.service.DamScopeConditionService;
import io.github.hlg212.fcf.api.dam.DataAuthorityPropertyConditionApi;
import io.github.hlg212.fcf.model.dam.IDataAuthorityPropertyConditionValue;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(value = "数据权限条件api控制器", tags = {"数据权限条件api控制器"})
public class DataAuthorityPropertyConditionApiController implements DataAuthorityPropertyConditionApi<DamScopeConditionBo> {

    @Autowired
    private DamScopeConditionService damScopeConditionService;



    @Override
    public List getConditions(String configSetId) {
        DamScopeConditionQco qco = new DamScopeConditionQco();
        qco.setConfigId(configSetId);
        //qco.setTjlx("");
       // qco.setSybz(io.github.hlg212.fcf.model.Constants.BOOLEAN_Y);
        List<DamScopeConditionBo> list =  damScopeConditionService.find(qco);

        return toRepeat(list);

    }

    @Override
    public <E extends IDataAuthorityPropertyConditionValue> E getValue(String id) {
        return null;
    }

    private List<DamScopeConditionBo> toRepeat(List<DamScopeConditionBo> list)
    {
        List<DamScopeConditionBo> result = new ArrayList<>();
        Map<String,DamScopeConditionBo> map = new LinkedHashMap();
        for(DamScopeConditionBo bo : list )
        {
            String key = bo.getPropertyName() + bo.getOperation();
            if( !map.containsKey(key) )
            {
                map.put(key,bo);
                result.add(bo);
            }
        }

        return result;
    }

//    @Override
//    public IDataAuthorityPropertyConditionValue getValue(String id,String optype) {
//        return dataAuthorityPropertyConditionService.getValue(FworkHelper.getUid(),id,optype);
//    }
}
