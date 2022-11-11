package org.tecky.uaaservice.services.intf;

import org.springframework.security.core.Authentication;

import java.util.Map;

public interface IAccessTokenService {

    public String getAccessToken(Authentication authentication, Map<String, String> oAuthInfo);

    public String getAccessToken(Authentication authentication, Map<String, String> oAuthInfo, String accessToken);

}
