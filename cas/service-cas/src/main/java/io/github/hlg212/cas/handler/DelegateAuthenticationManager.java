package io.github.hlg212.cas.handler;

import io.github.hlg212.cas.bo.UserLoginLockBo;
import io.github.hlg212.cas.service.UserLoginLockService;
import io.github.hlg212.cas.util.Constants;
import io.github.hlg212.fcf.util.MessageSourceAccessorHelper;
import io.github.hlg212.fcf.util.SpringHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@Slf4j
public class  DelegateAuthenticationManager implements AuthenticationManager {

    private AuthenticationManager authenticationManager;

    private static UserLoginLockService userLoginLockService;

    public DelegateAuthenticationManager(AuthenticationManager authenticationManager)
    {
        this.authenticationManager = authenticationManager;
    }

    private UserLoginLockService getUserLoginLockService()
    {
        if( userLoginLockService == null )
        {
            userLoginLockService = SpringHelper.getBean(UserLoginLockService.class);
        }
        return userLoginLockService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        checkLock(authentication.getName());
        return authenticationManager.authenticate(authentication);
    }

    private void checkLock(String username)
    {
        //根据缓存判断  如果规定时间内未达到密码错误次数 清除缓存
        if( getUserLoginLockService().isLock(username) )
        {
            UserLoginLockBo lock =  getUserLoginLockService().getById(username);
            if( lock != null )
            {
                String mess = MessageSourceAccessorHelper.getMessage(Constants.ErrorMessage.userLoginLock,new Object[]{username, DateFormatUtils.format(lock.getLockToTime(),"yyyy-MM-dd HH:mm"),lock.getErrorMsg()});
                log.debug(mess);
                throw  new LockedException(mess);
            }

        }
    }

}
