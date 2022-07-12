package io.github.hlg212.basic.service.impl;

import io.github.hlg212.basic.model.bo.UserBo;
import io.github.hlg212.basic.model.po.User;
import io.github.hlg212.basic.service.UserService;
import io.github.hlg212.fcf.model.Constants;
import io.github.hlg212.fcf.util.BooleanHelper;
import io.github.hlg212.fcf.util.ExceptionHelper;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/** 
 * 用户Service
 *
 * @author huanglg
 * @date 2022-03-28
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserBo save(UserBo userBo) {
        checkAdd(userBo);
        fillSave(userBo);
        return UserService.super.save(userBo);
    }

    private void fillSave(UserBo userBo)
    {
        userBo.setIsAdmin(Constants.BOOLEAN_N);

        userBo.setPasswd(encodePassword(getDefPassword()));
    }
    private String getDefPassword()
    {
        return "12345";
    }

    private void checkAdd(UserBo userBo)
    {

    }

    private String encodePassword(String password)
    {
        return passwordEncoder.encode(password);
    }

    @Override
    public UserBo update(UserBo userBo) {
        fillUpdate(userBo);
        return UserService.super.update(userBo);
    }

    private void fillUpdate(UserBo userBo)
    {
        userBo.setPasswd(null);
    }

    private void checkUpdate(UserBo userBo)
    {

    }

    @Override
    public void changePassword(String id, String oldPassword, String newPassword) {
        UserBo userBo = getById(id);
        if( !checkPassword(oldPassword,userBo.getPassword()))
        {
            ExceptionHelper.throwBusinessException("原密码不正确!");
        }
        userBo.setPasswd(encodePassword(newPassword));
        update(userBo);
    }


    private boolean checkPassword(String password,String encodePassword)
    {
        return passwordEncoder.matches(password,encodePassword);
    }


    @Override
    public boolean isAdmin(String userId) {
        UserBo bo = getById(userId);
        if(StringUtils.isEmpty(bo.getIsAdmin() ))
        {
            return false;
        }
        return BooleanHelper.to( bo.getIsAdmin() );
    }

}
