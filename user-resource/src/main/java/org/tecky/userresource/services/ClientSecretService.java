package org.tecky.userresource.services;

import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.Executor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ClientSecretService implements ApplicationRunner {

    public Map<String, String> secretMap;

    @Autowired
    ConfigService configService;

    @Value("${spring.cloud.nacos.config.name}")
    private String dataID;

    @Value("${spring.cloud.nacos.config.group}")
    private String group;


    @Override
    public void run(ApplicationArguments args) throws Exception {

        String json = configService.getConfigAndSignListener(this.dataID, this.group, 2000, new Listener() {

            @Override
            public void receiveConfigInfo(String configInfo) {

                try {
                    readJSON(configInfo);

                } catch (JsonProcessingException e) {

                    throw new RuntimeException(e);
                }
            }
            @Override
            public Executor getExecutor() {
                return null;
            }
        });

        readJSON(json);
    }

    public void readJSON(String json) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        this.secretMap = objectMapper.readValue(json, new TypeReference<Map<String, String>>(){});

    }
}
