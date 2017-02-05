package com.elementalprime.bft.web.config;


import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.elementalprime.bft.jpa.config.JPAConfig;
import com.elementalprime.bft.jpa.test.config.TestH2Config;

@ComponentScan(basePackages = {"com.elementalprime.bft"})
@Import(value = {WebConfig.class, ViewConfig.class, JPAConfig.class, TestH2Config.class})
@Configuration
@EnableAsync
@EnableScheduling
public class AppConfig {
    private static final Logger LOG = LoggerFactory.getLogger(AppConfig.class);

    /*@Bean
    public PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer x = new PropertySourcesPlaceholderConfigurer();
        
        String envFile = System.getProperty("/opt/env/application.yml");
        
        LOG.info("Configuring from [{}]", envFile);
        
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new FileSystemResource(envFile));
        x.setProperties(yaml.getObject());
        return x;
    }*/
    
    @PreDestroy
    public void adsf() {
        LOG.info("PRE DESTROY");
    }

}
