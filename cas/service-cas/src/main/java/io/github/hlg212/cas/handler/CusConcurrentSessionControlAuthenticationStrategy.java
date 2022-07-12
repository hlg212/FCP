package io.github.hlg212.cas.handler;

import io.github.hlg212.fcf.web.util.HttpServletHelper;
import io.github.hlg212.fcf.web.util.TokenHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.session.SessionRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.List;

/**
 *  自定义 session 控制
 *
 *
 */
@Slf4j
public class CusConcurrentSessionControlAuthenticationStrategy extends ConcurrentSessionControlAuthenticationStrategy {


    private SessionRepository sessionRepository;

    public CusConcurrentSessionControlAuthenticationStrategy(SessionRegistry sessionRegistry,SessionRepository sessionRepository) {
        super(sessionRegistry);
        this.sessionRepository = sessionRepository;
    }

    @Override
    public void onAuthentication(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("C_AUTHENTICATION",authentication);

        super.onAuthentication(authentication, request, response);

    }

    @Override
    protected void allowableSessionsExceeded(List<SessionInformation> sessions, int allowableSessions, SessionRegistry registry) throws SessionAuthenticationException {
        try {
            super.allowableSessionsExceeded(sessions,allowableSessions,registry);
        }
        catch (SessionAuthenticationException ex)
        {
            boolean flag = false;
            if( isMandatoryLogin() )
            {
                if( isAllowableMandatoryLogin() )
                {
                    sessionsExceeded(sessions);
                    flag = true;
                }
            }

            if( !flag )
            {
                throw ex;
            }
        }
    }

    private void sessionsExceeded(List<SessionInformation> sessions) throws SessionAuthenticationException {

//        SessionInformation leastRecentlyUsed = null;
//        Iterator var5 = sessions.iterator();
//
//        while(true) {
//            SessionInformation session;
//            do {
//                if (!var5.hasNext()) {
//                    leastRecentlyUsed.expireNow();
//                    return;
//                }
//
//                session = (SessionInformation)var5.next();
//            } while(leastRecentlyUsed != null && !session.getLastRequest().before(leastRecentlyUsed.getLastRequest()));
//
//            leastRecentlyUsed = session;
//        }
        Iterator<SessionInformation> iterator = sessions.iterator();
        while( iterator.hasNext() )
        {
            SessionInformation si =  iterator.next();
            sessionRepository.deleteById(si.getSessionId());
        }

    }

    /**
     *  是否强制登录
     * @return
     */
    private boolean isMandatoryLogin()
    {
        String mandatoryLogin =  HttpServletHelper.getRequest().getParameter("mandatoryLogin");
        String token = TokenHelper.getBearerToken();
        if(StringUtils.isNotEmpty(mandatoryLogin) || StringUtils.isNotEmpty(token))
        {
            return true;
        }
        return false;
    }

    /**
     *  是否允许强制登录
     * @return
     */
    private boolean isAllowableMandatoryLogin()
    {
        Authentication authentication = (Authentication) HttpServletHelper.getRequest().getAttribute("C_AUTHENTICATION");
        String name = authentication.getName();
        log.info( "是否允许 {} 进行强制登录！",name );

        return true;
    }
}