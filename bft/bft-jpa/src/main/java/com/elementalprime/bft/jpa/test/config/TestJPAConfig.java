package com.elementalprime.bft.jpa.test.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.elementalprime.bft.jpa.config.JPAConfig;

@Configuration
@Import(com.elementalprime.bft.jpa.config.JPAConfig.class)
@EnableTransactionManagement(proxyTargetClass = true)
public class TestJPAConfig {
    private static final Logger LOG = LoggerFactory.getLogger(TestJPAConfig.class);

    
    @Bean
    public PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer x = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean aws_persistence_yaml = new YamlPropertiesFactoryBean();
        aws_persistence_yaml.setResources(new ClassPathResource("bft-jpa-dev.yml"));
        x.setProperties(aws_persistence_yaml.getObject());
        return x;
    }

    @Bean(name = JPAConfig.NAME_DATA_SOURCE)
    public DataSource dataSource() {

        // no need shutdown, EmbeddedDatabaseFactoryBean will take care of this
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        EmbeddedDatabase db =
                builder.setType(EmbeddedDatabaseType.H2).addScript("create-schema.sql").addScript("create-aws-h2-db.sql").build();
        return db;
    }
}
