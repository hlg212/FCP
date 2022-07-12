package io.github.hlg212.basic.service;

import io.github.hlg212.basic.model.bo.UserBo;
import io.github.hlg212.fcf.service.impl.CurdieServiceImpl;

/** 
 * 用户Service
 *
 * @author huanglg
 * @date 2022-03-28
 */
public interface UserService extends CurdieServiceImpl<UserBo> {

    /**
     * 修改用户密码
     * @param userId 用户id
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    public void changePassword(String userId,String oldPassword,String newPassword);

    /**
     * 用户是否 超级管理员
     *
     * @param userId 用户id
     * @return 是返回ture
     */
    public boolean isAdmin(String userId);
}
