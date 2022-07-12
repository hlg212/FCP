package io.github.hlg212.cas.service;

import io.github.hlg212.fcf.model.PageInfo;
import io.github.hlg212.fcf.model.mc.IOnlineUser;


public interface OnlineUserService<T extends IOnlineUser> {

    public T save(T t);

    public void deleteById(Object... id);

    public T getById(Object id);

    public PageInfo<T> findPage(String username, int pageNum, int pageSize);

    public void remoteToken(String token);

    public void kill(String id);
    public void expiresToken(String token);
    public void expiresSession(String session);

    public boolean isInvalid(String id);


}
