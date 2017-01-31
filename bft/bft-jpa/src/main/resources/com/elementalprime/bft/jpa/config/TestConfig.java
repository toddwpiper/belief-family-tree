package au.gov.ipaustralia.rio.sdsm.aws.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import au.gov.ipaustralia.rio.sdsm.aws.jpa.config.AWSPersistenceConfig;

@ComponentScan(basePackages = { "au.gov.ipaustralia.rio.sdsm.aws" })
@Import(value = { JPAConfig.class })
@Configuration
public class TestConfig {

    @Bean
    public PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer x = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean aws_persistence_yaml = new YamlPropertiesFactoryBean();
        aws_persistence_yaml.setResources(new ClassPathResource("aws-persistence-dev.yml"));
        x.setProperties(aws_persistence_yaml.getObject());
        return x;
    }

    @Bean(name = AWSPersistenceConfig.NAME_DATA_SOURCE)
    public DataSource dataSource() {

        // no need shutdown, EmbeddedDatabaseFactoryBean will take care of this
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        EmbeddedDatabase db =
                builder.setType(EmbeddedDatabaseType.H2).addScript("create-schema.sql").addScript("create-aws-h2-db.sql").build();
        return db;
    }

}
