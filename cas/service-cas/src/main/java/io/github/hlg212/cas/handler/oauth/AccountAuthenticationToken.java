package io.github.hlg212.cas.handler.oauth;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class AccountAuthenticationToken extends AbstractAuthenticationToken
{
    private String account;

    public AccountAuthenticationToken(String account) {
       this(account,null);
    }

    public AccountAuthenticationToken(String account,Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.account = account;
    }

    @Override
    public Object getCredentials() {
        return account;
    }

    @Override
    public Object getPrincipal() {
        return account;
    }
}