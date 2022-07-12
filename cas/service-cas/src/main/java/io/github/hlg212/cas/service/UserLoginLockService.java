package io.github.hlg212.cas.service;

import io.github.hlg212.cas.bo.LoginPasswordErrorInfoBo;
import io.github.hlg212.cas.bo.UserLoginLockBo;
import io.github.hlg212.fcf.service.impl.CurdServiceImpl;

/**
 * @description: <功能描述>
 * @author: huangligui
 * @create: 2019-02-18 10:04
 **/
public interface UserLoginLockService extends CurdServiceImpl<UserLoginLockBo> {


    public boolean isLock(String username);

    public LoginPasswordErrorInfoBo addLoginErrorNum(String username);

}
