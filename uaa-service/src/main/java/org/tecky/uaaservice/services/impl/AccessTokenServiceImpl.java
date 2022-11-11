package org.tecky.uaaservice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.tecky.uaaservice.entities.OauthClientEntity;
import org.tecky.uaaservice.entities.OauthScopeEntity;
import org.tecky.uaaservice.mapper.OauthClientEntityRepository;
import org.tecky.uaaservice.mapper.OauthScopeEntityRepository;
import org.tecky.uaaservice.services.intf.IAccessTokenService;
import org.faAnswer.jwt.JwtToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AccessTokenServiceImpl implements IAccessTokenService {

    @Value("${jwt.secret}")
    private String coreSecret;

    @Autowired
    OauthClientEntityRepository oauthClientEntityRepository;

    @Autowired
    OauthScopeEntityRepository oauthScopeEntityRepository;

    @Override
    public String getAccessToken(Authentication authentication, Map<String, String> oAuthInfo) {

        String clientID = oAuthInfo.get("client_id");

        OauthClientEntity oauthClientEntity = oauthClientEntityRepository.findByClientid(clientID);
        List<OauthScopeEntity> oauthScopeEntity = oauthScopeEntityRepository.findAllByAppid(oauthClientEntity.getAppid());

        List<Integer> scopeList = new ArrayList<Integer>();

        for(OauthScopeEntity i : oauthScopeEntity){

            scopeList.add(i.getScopeid());
        }

        JwtToken jwtToken = new JwtToken(oauthClientEntity.getClientsecret());

        String accessToken = jwtToken
                .setPayload("username", authentication.getName())
                .setPayload("clientid", clientID)
                .setPayload("scope", scopeList)
                .generateToken();

        return accessToken;
    }

    @Override
    public String getAccessToken(Authentication authentication, Map<String, String> oAuthInfo, String accessToken) {

        String clientID = oAuthInfo.get("client_id");

        JwtToken coreAccessToken = new JwtToken(this.coreSecret);

        if(accessToken.equals("")){

            String newAccessToken = getAccessToken(authentication, oAuthInfo);


            return coreAccessToken.setPayload(clientID, newAccessToken).generateToken();
        }

        Map<String, Object> corePayLoad = coreAccessToken.getAllPayload(accessToken);

        if(corePayLoad.get(clientID) == null){

            String newAccessToken = getAccessToken(authentication, oAuthInfo);
            coreAccessToken.reFreshPayload(clientID, newAccessToken);

            return coreAccessToken.generateToken();

        } else {

            return accessToken;
        }
    }
}
