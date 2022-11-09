package org.tecky.uaaservice.security.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.authenticator.SavedRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.tecky.uaaservice.security.config.impl.RedirectRequestCache;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class AuthSuccessRedirectHandler implements AuthenticationSuccessHandler {

    @Autowired
    RedirectRequestCache redirectRequestCache;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                 Authentication authentication) throws IOException, ServletException{

        List<DefaultSavedRequest> savedRequestList = (List<DefaultSavedRequest>) request.getSession().getAttribute(RedirectRequestCache.SAVED_REQUEST);

        String tarRequest = savedRequestList.get(0).getRedirectUrl();

        //savedRequestList.get(0)
        redirectStrategy.sendRedirect(request, response, tarRequest);

        redirectRequestCache.removeSavedRequest();
    }
}
