package org.tecky.viewserver.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.tecky.viewserver.security.config.impl.RedirectRequestCache;

@Configuration
public class RequestCacheConfig {

    @Bean
    RedirectRequestCache requestCache() {

        RedirectRequestCache cache = new RedirectRequestCache();

        RequestMatcher getRequests = new AntPathRequestMatcher("/**");

        cache.setRequestMatcher(getRequests);

        return cache;
    }
}
