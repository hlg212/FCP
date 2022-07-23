package io.github.hlg212.dam.service;

import io.github.hlg212.fcf.model.dam.IDataAuthorityPropertyConditionValue;

public interface DataAuthorityPropertyConditionService {

    public IDataAuthorityPropertyConditionValue getValue(String userId,String id, String optype);

}
