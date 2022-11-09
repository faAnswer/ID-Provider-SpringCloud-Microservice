package org.tecky.uaaservice.security.config.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.log.LogMessage;
import org.springframework.security.web.PortResolver;
import org.springframework.security.web.PortResolverImpl;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class RedirectRequestCache extends HttpSessionRequestCache {
    public static final String SAVED_REQUEST = "SPRING_SECURITY_SAVED_ALL_REQUEST";

    protected final Log logger = LogFactory.getLog(this.getClass());

    private PortResolver portResolver = new PortResolverImpl();

    private boolean createSessionAllowed = true;

    private RequestMatcher requestMatcher = AnyRequestMatcher.INSTANCE;

    private String sessionAttrName = SAVED_REQUEST;

    private List<SavedRequest> savedRequestList = new ArrayList<>();

    @Override
    public void saveRequest(HttpServletRequest request, HttpServletResponse response) {
        if (!this.requestMatcher.matches(request)) {
            if (this.logger.isTraceEnabled()) {
                this.logger.trace(
                        LogMessage.format("Did not save request since it did not match [%s]", this.requestMatcher));
            }
            return;
        }
        DefaultSavedRequest savedRequest = new DefaultSavedRequest(request, this.portResolver);
        this.savedRequestList.add(savedRequest);

        if (this.createSessionAllowed || request.getSession(false) != null) {
            // Store the HTTP request itself. Used by
            // AbstractAuthenticationProcessingFilter
            // for redirection after successful authentication (SEC-29)
            request.getSession().setAttribute(this.sessionAttrName, this.savedRequestList);
            if (this.logger.isDebugEnabled()) {
                this.logger.debug(LogMessage.format("Saved request %s to session", savedRequest.getRedirectUrl()));
            }
        }
        else {
            this.logger.trace("Did not save request since there's no session and createSessionAllowed is false");
        }
    }

    private boolean matchesSavedRequest(HttpServletRequest request, SavedRequest savedRequest) {
        if (savedRequest instanceof DefaultSavedRequest) {
            DefaultSavedRequest defaultSavedRequest = (DefaultSavedRequest) savedRequest;
            return defaultSavedRequest.doesRequestMatch(request, this.portResolver);
        }
        String currentUrl = UrlUtils.buildFullRequestUrl(request);
        return savedRequest.getRedirectUrl().equals(currentUrl);
    }

    public void removeSavedRequest() {

        this.savedRequestList.clear();
    }
}
