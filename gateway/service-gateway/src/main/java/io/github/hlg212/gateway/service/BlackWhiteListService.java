package io.github.hlg212.gateway.service;

import java.util.List;

public interface BlackWhiteListService {

    public List getBlackWhiteList();
    public List getBlackWhiteListByAppCode(String appCode);
    public List getBlackWhiteListByAccount(String account);
}
