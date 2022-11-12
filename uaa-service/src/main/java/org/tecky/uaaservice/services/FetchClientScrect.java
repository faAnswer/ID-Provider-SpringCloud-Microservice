package org.tecky.uaaservice.services;

import com.alibaba.nacos.api.config.ConfigService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.tecky.uaaservice.entities.OauthClientEntity;
import org.tecky.uaaservice.mapper.OauthClientEntityRepository;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FetchClientScrect {

    @Autowired
    ConfigService configService;

    @Autowired
    OauthClientEntityRepository oauthClientEntityRepository;

    @Value("${spring.cloud.nacos.config.name}")
    private String dataID;

    @Value("${spring.cloud.nacos.config.group}")
    private String group;

    public void fetch() throws Exception {

        List<OauthClientEntity> oauthClientEntityList = oauthClientEntityRepository.findAllByStatus("alive");

        Map<String, String> clientMap = new HashMap<>();

        for(OauthClientEntity oauthClientEntity : oauthClientEntityList){

            clientMap.put(oauthClientEntity.getClientid(), oauthClientEntity.getClientsecret());
        }

        ObjectMapper objectMapper = new ObjectMapper();

        String json = objectMapper.writeValueAsString(clientMap);

        configService.publishConfig(this.dataID, this.group, json);
    }
}