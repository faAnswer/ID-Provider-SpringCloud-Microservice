package org.tecky.uaaservice;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;

@SpringBootApplication
public class UaaServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(UaaServiceApplication.class, args);

    }

}
