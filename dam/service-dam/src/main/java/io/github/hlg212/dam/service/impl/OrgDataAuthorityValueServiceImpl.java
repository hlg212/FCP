package io.github.hlg212.dam.service.impl;

import io.github.hlg212.dam.api.OrgDataAuthorityApiController;
import io.github.hlg212.dam.model.bo.DamScopeConditionBo;
import io.github.hlg212.dam.service.DataAuthorityValueService;
import io.github.hlg212.dam.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(Constants.Sjqxfwtj.FWLX_YHJG + Constants.DATA_AUTHORITY_VALUE_SERVICE_SUFFIX)
public class OrgDataAuthorityValueServiceImpl implements DataAuthorityValueService {

    @Autowired
    private OrgDataAuthorityApiController orgDataAuthorityApiController;

    @Override
    public Object getAuthoritys(String userId, DamScopeConditionBo bo) {
        List orgs = orgDataAuthorityApiController.getAuthoritys(userId,bo.getParamCode());
        return orgs;
    }
}
