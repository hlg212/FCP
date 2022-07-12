package io.github.hlg212.cas.handler.oauth;

import org.springframework.security.oauth2.provider.endpoint.DefaultRedirectResolver;
import org.springframework.util.AntPathMatcher;

public class AntRedirectResolver extends DefaultRedirectResolver {

    private AntPathMatcher matcher = new AntPathMatcher();

    @Override
    protected boolean redirectMatches(String requestedRedirect, String redirectUri) {
        return super.redirectMatches(requestedRedirect, redirectUri) || antRedirectMatches(requestedRedirect,redirectUri);
    }

    private boolean antRedirectMatches(String requestedRedirect, String redirectUri)
    {
        return matcher.match(redirectUri,requestedRedirect);
    }


}
