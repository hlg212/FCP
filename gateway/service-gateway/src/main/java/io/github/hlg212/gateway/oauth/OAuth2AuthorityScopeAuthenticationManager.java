package io.github.hlg212.gateway.oauth;

import io.github.hlg212.fcf.api.ClientApi;
import io.github.hlg212.fcf.model.basic.IClientAuthority;
import io.github.hlg212.fcf.util.CollectionHelper;
import io.github.hlg212.gateway.cache.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;

import java.util.*;

/**
 *  对客户端的scope做出控制
 *  用户的权限不能大于客户端
 */
@CacheConfig(cacheManager = io.github.hlg212.fcf.cache.Constants.CacheManager.SimpleCacheManager,cacheNames = Constants.OA)
public class OAuth2AuthorityScopeAuthenticationManager  extends OAuth2AuthenticationManager {
    @Autowired
    private ClientApi clientApi;

    @Override
    @Cacheable(key = "'wap_'+#p0.principal")
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        OAuth2Authentication a = null;

        Authentication au =  super.authenticate(authentication);
        au = wap(au);
        return au;
    }

    private Authentication wap(Authentication au){
        if( au instanceof OAuth2Authentication)
        {
            OAuth2Authentication oAuth2Authentication = (OAuth2Authentication)au;
            if( "client_credentials".equals( oAuth2Authentication.getOAuth2Request().getGrantType() ) )
            {
                return au;
            }
            Set scope = oAuth2Authentication.getOAuth2Request().getScope();
            if( scope.contains("ALL") ) {
                return au;
            }
            List as = retainAuthorities((OAuth2Authentication)au);
            au = new AuthorityScopeAuthentication(au,as);
        }
        return au;
    }

    private List getAuthoritiesByScopes( Set<String> scope )
    {
        List authorities = new ArrayList();
        Iterator iter = scope.iterator();
        while(iter.hasNext())
        {
            authorities.add(iter.next());
        }
        return authorities;
    }

    /**
     *  取用户的权限与客户端的作用域的交集
     *
     * @param au
     * @return
     */
    private List retainAuthorities( OAuth2Authentication au)
    {
        Set<String> scopes = au.getOAuth2Request().getScope();
        Set<String> authoritys = new HashSet<String>();
        Collection<IClientAuthority> clientApiAuthoritys = clientApi.getAuthoritys(au.getOAuth2Request().getClientId());

        Map roleAuthMap = CollectionHelper.getPropertyMap(clientApiAuthoritys,"roleId");

        for(String roleId : scopes){
            if(roleAuthMap.containsKey(roleId)){
                authoritys.addAll((Collection<String>)roleAuthMap.get(roleId));
            }
        }
        Collection as = au.getAuthorities();
        List result = new ArrayList();
        Iterator iter = as.iterator();
        while( iter.hasNext() )
        {
            GrantedAuthority ga = (GrantedAuthority) iter.next();
            if(  authoritys.contains(ga.getAuthority()) ) {
                result.add(ga);
            }
        }
        return result;
    }

    private class AuthorityScopeAuthentication implements Authentication
    {
        private Authentication authentication;
        private Collection<GrantedAuthority> authorities;
        private AuthorityScopeAuthentication(Authentication authentication,Collection<GrantedAuthority> authorities)
        {
            this.authentication = authentication;
            this.authorities = authorities;
        }
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            if( authorities == null ) {
                return authentication.getAuthorities();
            }
            return authorities;
        }

        @Override
        public Object getCredentials() {
            return authentication.getCredentials();
        }

        @Override
        public Object getDetails() {
            return authentication.getDetails();
        }

        @Override
        public Object getPrincipal() {
            return authentication.getPrincipal();
        }

        @Override
        public boolean isAuthenticated() {
            return authentication.isAuthenticated();
        }

        @Override
        public void setAuthenticated(boolean b) throws IllegalArgumentException {
            authentication.setAuthenticated(b);
        }

        @Override
        public String getName() {
            return authentication.getName();
        }
    }
}
