package io.github.hlg212.cas.service.impl;

import io.github.hlg212.cas.util.Constants;
import io.github.hlg212.fcf.api.ClientApi;
import io.github.hlg212.fcf.model.basic.IClient;
import io.github.hlg212.fcf.model.basic.IClientAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @description: 客户端信息服务
 *
 * @author: huangligui
 * @create: 2018-10-26 10:30
 **/
@Service
@CacheConfig(cacheNames =  io.github.hlg212.cas.cache.Constants.ClientDetail,cacheManager = io.github.hlg212.fcf.cache.Constants.CacheManager.ThreadLocalCacheManager)
public class MyClientDetailsService implements ClientDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ClientApi clientApi;

    @Override
    @Cacheable(key = "#p0")
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
       return findClientDetails(clientId);
    }

    private ClientDetails findClientDetails(String clientId)
    {
        IClient c = clientApi.getById(clientId);
        if( c == null ) {
            throw new NoSuchClientException("No client with requested id: " + clientId);
        }

        String as = getAuthoritys(clientId);

        BaseClientDetails cd = new BaseClientDetails(c.getId(),"",c.getScopes(),String.join(",",Constants.DefGrantTypes),as);
        cd.setAutoApproveScopes(  StringUtils.commaDelimitedListToSet(c.getAutoApproveScopes()));
        cd.setClientSecret(passwordEncoder.encode(c.getSecret()));
        //cd.setRegisteredRedirectUri(StringUtils.commaDelimitedListToSet("http://192.168.0.77:8084/sso/login"));
        //cd.setRegisteredRedirectUri("http://192.168.0.77:8084/sso/login");

        //cd.setRegisteredRedirectUri(StringUtils.commaDelimitedListToSet("http://localhost:8084/sso/login,http://192.168.0.77:8084/sso/login"));
        cd.setRegisteredRedirectUri(StringUtils.commaDelimitedListToSet(c.getRegisteredRedirectUri()));
        return cd;
    }

    private  String getAuthoritys(String clientId)
    {
        List result = new ArrayList();
        Collection<IClientAuthority> clientApiAuthoritys = clientApi.getAuthoritys(clientId);
        Iterator iter = clientApiAuthoritys.iterator();
        while( iter.hasNext() )
        {
            IClientAuthority auths = (IClientAuthority) iter.next();
            result.addAll(auths.getAuthoritys());
        }
        return String.join(",",result);
    }
}
