package io.github.hlg212.gateway.exception;

import org.springframework.security.core.AuthenticationException;

public class UserIpRangeNotLlowedException extends AuthenticationException
{
    public UserIpRangeNotLlowedException(String msg) {
        super(msg);
    }
}
