package io.github.hlg212.cas.service.impl;

import io.github.hlg212.cas.bo.LoginPasswordErrorLogBo;
import io.github.hlg212.cas.cache.Constants;
import io.github.hlg212.cas.service.LoginPasswordErrorLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@CacheConfig(cacheNames = Constants.LoginPasswordErrorLog)
public class LoginPasswordErrorLogImpl implements LoginPasswordErrorLogService {


    @Override
    @Cacheable(key="#p0")
    public List<LoginPasswordErrorLogBo> getLoginPasswordErrorLogs(String username) {
        log.debug("获得[{}]登录错误日志",username);
        return null;
    }


    @Override
    @CachePut(key="#p0")
    public List<LoginPasswordErrorLogBo> saveLoginPasswordErrorLogs(String username, List list) {
        log.debug("保存[{}]登录错误日志",username);
        return list;
    }


}
