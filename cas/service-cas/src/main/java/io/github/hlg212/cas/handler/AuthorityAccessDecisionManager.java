package io.github.hlg212.cas.handler;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.AbstractAccessDecisionManager;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @description: <功能描述>
 * @author: huangligui
 * @create: 2019-03-15 15:11
 **/
@Component
@ConditionalOnProperty(matchIfMissing = true,value = "cas.filterSecurity",prefix = "htcf")
public class AuthorityAccessDecisionManager extends AffirmativeBased {

    private AccessDecisionManager accessDecisionManager;

    protected AuthorityAccessDecisionManager(List<AccessDecisionVoter<?>> decisionVoters) {
        super(decisionVoters);
    }

    public AuthorityAccessDecisionManager()
    {
        super(Arrays.asList( new AuthorityVoter()  ));
    }

    public void setAccessDecisionManager(AccessDecisionManager accessDecisionManager) {
        this.accessDecisionManager = accessDecisionManager;
    }

    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        accessDecisionManager.decide(authentication,o,collection);
        super.decide(authentication,o,collection);
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        boolean b = accessDecisionManager.supports(attribute);
        if( !b )
        {
            return super.supports(attribute);
        }
        return b;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        boolean b = accessDecisionManager.supports(clazz);
        if( !b )
        {
            return super.supports(clazz);
        }
        return b;
    }

    private static class  AuthorityVoter extends RoleVoter
    {
        public AuthorityVoter()
        {
            setRolePrefix("");
        }
    }
}
