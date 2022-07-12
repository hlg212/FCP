package io.github.hlg212.cas.filter;

import io.github.hlg212.cas.handler.oauth.AccountAuthenticationToken;
import io.github.hlg212.fcf.api.UserInfoApi;
import io.github.hlg212.fcf.model.basic.IUser;
import io.github.hlg212.fcf.web.util.HttpServletHelper;
import io.github.hlg212.fcf.web.util.TokenHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;

@Slf4j
public class CookieSecurityContextPersistenceFilter extends OncePerRequestFilter {

    @Autowired
    private UserInfoApi userInfoApi;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        String token = TokenHelper.getBearerToken();

        if(a == null && StringUtils.isNotEmpty(token))
        {
            try {
                IUser iUser = userInfoApi.userinfo(token);
                //return getAuthenticationManager().authenticate(new AccountAuthenticationToken(iUser.getAccount()));
                UserDetails ud = userDetailsService.loadUserByUsername(iUser.getAccount());
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(iUser.getAccount(), null, ud.getAuthorities());
                //auth.setAuthenticated(true);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }catch (Exception e)
            {
                log.error("token 认证出错!",e);
            }
        }
        filterChain.doFilter(request,response);
    }

}
