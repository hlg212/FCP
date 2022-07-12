package io.github.hlg212.cas.service.impl;

import io.github.hlg212.cas.bo.LoginPasswordErrorInfoBo;
import io.github.hlg212.cas.bo.LoginPasswordErrorLogBo;
import io.github.hlg212.cas.bo.UserLoginLockBo;
import io.github.hlg212.cas.cache.Constants;
import io.github.hlg212.cas.properties.LoginPasswordErrorProperties;
import io.github.hlg212.cas.service.LoginPasswordErrorLogService;
import io.github.hlg212.cas.service.UserLoginLockService;
import io.github.hlg212.fcf.annotation.CacheRemove;
import io.github.hlg212.fcf.annotation.EnableTransactionLog;
import io.github.hlg212.fcf.model.basic.IUser;
import io.github.hlg212.fcf.util.FworkHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@CacheConfig(cacheNames = Constants.UserLoginLock)
// 集成模式防止死循环
@EnableTransactionLog(false)
public class UserLoginLockServiceImpl implements UserLoginLockService {


    @Autowired
    private UserLoginLockService self;

    @Autowired
    private LoginPasswordErrorLogService loginPasswordErrorLogService;

    @Autowired
    private LoginPasswordErrorProperties loginPasswordErrorProperties;

    @Override
    public boolean isLock(String username) {

        UserLoginLockBo loginLockBo = self.getById(username);
        if( loginLockBo != null )
        {
            if( loginLockBo.getLockToTime() != null )
            {
                if( new Date().before(loginLockBo.getLockToTime() ) )
                {
                    return true;
                }
            }
        }

        self.deleteById(username);

        return false;
    }

    @Override
    public LoginPasswordErrorInfoBo addLoginErrorNum(String username) {
        LoginPasswordErrorInfoBo loginPasswordErrorInfoBo = new LoginPasswordErrorInfoBo();
        loginPasswordErrorInfoBo.setUsername(username);
        loginPasswordErrorInfoBo.setMaxErrorNum(loginPasswordErrorProperties.getMaxErrorNum());
        List<LoginPasswordErrorLogBo> list = loginPasswordErrorLogService.getLoginPasswordErrorLogs(username);
        List result = new ArrayList(10);
        if( list != null ) {
            Date invalidTime = DateUtils.addMinutes(new Date(),-1 * loginPasswordErrorProperties.getTimecell());

            result = list.stream().filter(o -> {
                return !o.getErrorTime().before(invalidTime);
            }).collect(Collectors.toList());
        }

        LoginPasswordErrorLogBo loginPasswordErrorLogBo = new LoginPasswordErrorLogBo();
        loginPasswordErrorLogBo.setUsername(username);
        loginPasswordErrorLogBo.setErrorTime(new Date());
        result.add(loginPasswordErrorLogBo);

        if( result.size() >= loginPasswordErrorProperties.getMaxErrorNum() )
        {
            UserLoginLockBo loginLock = new UserLoginLockBo();
            loginLock.setUsername(username);
            loginLock.setErrorMsg(String.format("登录密码错误次数超过%s次",loginPasswordErrorProperties.getMaxErrorNum()));
            loginLock.setLockToTime(DateUtils.addMinutes(new Date(),loginPasswordErrorProperties.getLockMinutes()));
            self.save(loginLock);
        }
        loginPasswordErrorInfoBo.setCurrNum(result.size());
        loginPasswordErrorLogService.saveLoginPasswordErrorLogs(username,result);
        return loginPasswordErrorInfoBo;
    }

    @Override
    @Cacheable(key="#p0")
    public <E extends UserLoginLockBo> E getById(Object id) {
        return null;
    }

    @Override
    @CacheRemove(key="#p0")
    public void deleteById(Object... ids) {

        log.debug("deleteById :{}" ,ids);
    }


    @Override
    @CachePut(key="#p0.username")
    public UserLoginLockBo save(UserLoginLockBo userLoginLockBo) {
        userLoginLockBo.setCreateTime(new Date());
        IUser u = FworkHelper.getUser();
        if( u != null )
        {
            userLoginLockBo.setCreateUser( u.getName() );

        }
        return userLoginLockBo;
    }
}
