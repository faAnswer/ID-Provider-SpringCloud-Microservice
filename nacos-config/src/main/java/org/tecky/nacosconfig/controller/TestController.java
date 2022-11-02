package org.tecky.nacosconfig.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    // 从 nacos 中读取配置项 minio.info
    @Value("${minio.info}")
    private String configInfo;

    @GetMapping("/getconfig")
    public String getConfigInfo(){
        return configInfo;
    }
}