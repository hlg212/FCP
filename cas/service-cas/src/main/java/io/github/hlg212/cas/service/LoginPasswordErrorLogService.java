package io.github.hlg212.cas.service;

import io.github.hlg212.cas.bo.LoginPasswordErrorLogBo;
import io.github.hlg212.fcf.service.impl.CurdServiceImpl;

import java.util.List;

/**
 * @description: <功能描述>
 * @author: huangligui
 * @create: 2019-02-18 10:04
 **/
public interface LoginPasswordErrorLogService extends CurdServiceImpl<LoginPasswordErrorLogBo> {

    public List<LoginPasswordErrorLogBo> getLoginPasswordErrorLogs(String username);


    public List<LoginPasswordErrorLogBo> saveLoginPasswordErrorLogs(String username,List list);
}
