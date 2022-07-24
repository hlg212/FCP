package io.github.hlg212.dam.service.impl;

import io.github.hlg212.dam.model.bo.DamConfigBo;
import io.github.hlg212.dam.model.bo.DamScopeConditionBo;
import io.github.hlg212.dam.model.enums.ConditionTypeEnum;
import io.github.hlg212.dam.model.qco.DamScopeConditionQco;
import io.github.hlg212.dam.service.DamConfigService;
import io.github.hlg212.dam.service.DamScopeConditionService;
import io.github.hlg212.dam.service.DataAuthorityPropertyConditionService;
import io.github.hlg212.dam.service.DataAuthorityValueService;
import io.github.hlg212.dam.util.Constants;
import io.github.hlg212.fcf.model.dam.DataAuthorityPropertyConditionValue;
import io.github.hlg212.fcf.model.dam.IDataAuthorityPropertyConditionValue;
import io.github.hlg212.fcf.util.FworkHelper;
import io.github.hlg212.fcf.util.PermissionHelper;
import io.github.hlg212.fcf.util.SpringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class DataAuthorityPropertyConditionServiceImpl implements DataAuthorityPropertyConditionService {

    @Autowired
    private DamScopeConditionService damScopeConditionService;

    @Autowired
    private DamConfigService damConfigService;

    @Override
    public IDataAuthorityPropertyConditionValue getValue(String userId,String id, String optype) {
        List<DamScopeConditionBo> list = getUserOwned(userId,id,optype);
        List valList = new ArrayList();

        DataAuthorityPropertyConditionValue value = null;

        for( DamScopeConditionBo b : list ) {
            DataAuthorityValueService service = (DataAuthorityValueService) SpringHelper.getBean(b.getScopeType() + Constants.DATA_AUTHORITY_VALUE_SERVICE_SUFFIX);
            Object val = service.getAuthoritys(userId,b);
            valList.add(val);
        }

        if( !valList.isEmpty() )
        {
            value = new DataAuthorityPropertyConditionValue();
            value.setValue(mergeValue(valList));
        }
        return value;
    }
    private Object mergeValue(List valList)
    {
        Object val = valList.get(0);
        if( val instanceof Collection )
        {
            return mergeColl(valList);
        }
        String strVal = val.toString();
        if( strVal.startsWith("[") )
        {
            return mergeStringColl(valList);
        }
        return valList.get(valList.size()-1);
    }
    private List mergeColl(List valList){
        List result = new ArrayList();
        for( Object e: valList)
        {
            result.addAll( (Collection) e );
        }
        return result;
    }
    private String mergeStringColl(List valList){
        return null;
    }


    private List<DamScopeConditionBo> getUserOwned(String userId,String id, String optype)
    {
        DamScopeConditionBo bo = damScopeConditionService.getById(id);
        DamConfigBo configBo = damConfigService.getById(bo.getConfigId());
        DamScopeConditionQco qco = new DamScopeConditionQco();
        qco.setConfigId(bo.getConfigId());
        qco.setPropertyName(bo.getPropertyName());
        qco.setOperation(bo.getOperation());
        //qco.setTjlx("");
       // qco.setSybz(io.github.hlg212.fcf.model.Constants.BOOLEAN_Y);
        List<DamScopeConditionBo> list =  damScopeConditionService.find(qco);
        List<DamScopeConditionBo> result = new ArrayList<>();
        boolean flag = true;
        List<DamScopeConditionBo> relist = getUserOwned(configBo.getAppId(),userId,optype,list);
        List<DamScopeConditionBo> jcresult  = new ArrayList<>();
        for( DamScopeConditionBo b : relist )
        {
            if(ConditionTypeEnum.EXTEND.getValue().equals(b.getConditionType()) )
            {
                result.add(b);
            }
            else if( ConditionTypeEnum.ONLY.getValue().equals(b.getConditionType()) )
            {
                result.add(b);
                flag = false;
            }
            else
            {
                jcresult.add(b);
            }
        }
        if( flag )
        {
            result.addAll(jcresult);
        }
        return result;
    }
    private List<DamScopeConditionBo> getUserOwned(String appCode,String userId,String optype, List <DamScopeConditionBo> list)
    {
        List <DamScopeConditionBo> relist = new ArrayList<>();
        for( DamScopeConditionBo b : list )
        {
            if( optype.equals(io.github.hlg212.fcf.Constants.DataOperation.QUERY) && b.getIsQuery()
                    || (optype.equals(io.github.hlg212.fcf.Constants.DataOperation.ADD) && b.getIsAdd())
                    || (optype.equals(io.github.hlg212.fcf.Constants.DataOperation.UPDATE) && b.getIsUpdate())
                    || (optype.equals(io.github.hlg212.fcf.Constants.DataOperation.DELETE) && b.getIsDelete()))
            {

                if( ConditionTypeEnum.SYS.getValue().equals(b.getConditionType())
                        || checkAuthoriity(appCode,userId,b.getCode()) )
                {
                    relist.add(b);
                }
            }
            else
            {
                continue;
            }

        }
        return relist;
    }
    private boolean checkAuthoriity(String appCode,String userId,String authority) {
        return PermissionHelper.checkPermission(appCode, userId,authority);
    }


}
