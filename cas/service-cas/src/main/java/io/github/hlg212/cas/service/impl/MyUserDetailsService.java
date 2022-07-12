package io.github.hlg212.cas.service.impl;

import io.github.hlg212.fcf.api.AuthApi;
import io.github.hlg212.fcf.api.UserApi;
import io.github.hlg212.fcf.model.basic.IUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * @description: 用户信息服务
 *
 * @author: huangligui
 * @create: 2018-10-26 10:30
 **/
@Service
@Slf4j
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserApi userApi;

    @Autowired
    private AuthApi authApi;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUser(username);
        return user;
    }

    private User getUser(String username) {

        IUser u = userApi.getUserByAccount(username);
        /*io.github.hlg212.fcf.model.basic.User u  = new io.github.hlg212.fcf.model.basic.User();
       u.setAccount(username);*/
        //u.setPassword(passwordEncoder.encode("123456") );
        if( u == null ){
            throw  new UsernameNotFoundException("用户不存在【"+username+"】");
        }

        Collection<GrantedAuthority> authorities = new HashSet<>();
       // authorities.add(new SimpleGrantedAuthority(Constants.UserType.USER.getValue()));//用户所拥有的角色信息

        //authApi.getUserAuthoritys(u.getAccount());
        try
        {
            List<String> as =  authApi.getAllPermissions(u.getId());
            authorities.addAll( AuthorityUtils.createAuthorityList(as.toArray(new String[0])) );
        }catch (Exception e)
        {
            log.warn("获取用户【{}】权限错误!",username,e);
        }


        User user = new User(u.getAccount(),u.getPassword(),authorities);
        return user;
    }
}
