package org.tecky.nacosconfig.controller;


import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.client.config.NacosConfigService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/nacos")
@RefreshScope
public class NacosConfigController {

    private static String dataId = "nacos-config";
    private static String group = "DEFAULT_GROUP";

    @NacosInjected
    @Autowired
    private ConfigService configService;

    @GetMapping(value = "/publishConfig")
    public boolean publishConfig() {
        boolean res = false;

        log.info(dataId);

        try {
            // 核心代码
            String json = configService.getConfig(dataId, group,2000);

            ObjectMapper objectMapper = new ObjectMapper();

            Map<String, Object> result = objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {});

            System.out.println("res");


            //            res = configService.publishConfig(dataId, group, "发布配置");


        } catch (NacosException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(res);
        return res;
    }


}
