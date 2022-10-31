package org.tecky.springcloudnacosconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
public class SpringcloudNacosConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringcloudNacosConsumerApplication.class, args);
    }
    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        // 用于进行 HTTP 请求的对象
        return new RestTemplate();
    }
}