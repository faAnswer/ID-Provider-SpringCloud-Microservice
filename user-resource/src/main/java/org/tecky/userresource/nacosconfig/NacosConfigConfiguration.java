package org.tecky.userresource.nacosconfig;


import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Properties;

/**
 * @author <a href="mailto:chenxilzx1@gmail.com">theonefx</a>
 */

@EnableAutoConfiguration
@Configuration
public class NacosConfigConfiguration {

    @Value("${spring.cloud.nacos.config.server-addr}")
    private String serverAddr ;

    @Value("${spring.cloud.nacos.config.namespace}")
    private String namespace ;

    @Value("${spring.cloud.nacos.config.username}")
    private String username ;

    @Value("${spring.cloud.nacos.config.password}")
    private String password ;

    @Bean
    @Primary
    public ConfigService nacosConfigService() throws NacosException {

        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR, this.serverAddr);
        properties.put(PropertyKeyConst.NAMESPACE, this.namespace);
        properties.put(PropertyKeyConst.USERNAME, this.username);
        properties.put(PropertyKeyConst.PASSWORD, this.password);

        return NacosFactory.createConfigService(properties);
    }
}
