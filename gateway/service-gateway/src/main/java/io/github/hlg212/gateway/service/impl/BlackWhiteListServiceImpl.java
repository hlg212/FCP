package io.github.hlg212.gateway.service.impl;

import io.github.hlg212.fcf.api.BlackWhiteListApi;
import io.github.hlg212.fcf.cache.Constants;
import io.github.hlg212.gateway.service.BlackWhiteListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author hlg
 */
@Component
@CacheConfig(cacheNames = Constants.BlackWhiteList,cacheManager = Constants.CacheManager.SimpleCacheManager )
public class BlackWhiteListServiceImpl implements BlackWhiteListService {

    @Autowired
    private BlackWhiteListApi blackWhiteListApi;

    @Override
    @Cacheable(key = Constants.BlackWhiteListKey.getBlackWhiteList_spel,unless="#result == null")
    public List getBlackWhiteList() {
        return blackWhiteListApi.getBlackWhiteList();
    }

    @Override
    @Cacheable(key = Constants.BlackWhiteListKey.getBlackWhiteListByAppCode_spel,unless="#result == null")
    public List getBlackWhiteListByAppCode(String appCode) {
        return blackWhiteListApi.getBlackWhiteListByAppCode(appCode);
    }

    @Override
    @Cacheable(key = Constants.BlackWhiteListKey.getBlackWhiteListByAccount_spel,unless="#result == null")
    public List getBlackWhiteListByAccount(String account) {
        return blackWhiteListApi.getBlackWhiteListByAccount(account);
    }
}
