package org.tecky.servicenacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
public class ServiceNacosApplication {

    public static void main(String[] args) {

        SpringApplication.run(ServiceNacosApplication.class, args);
    }

    @RequestMapping("/sayhi/{name}")
    public String sayHi(@PathVariable String name) {
        return "Hi Nacos Discovery " + name;
    }
}
