package au.gov.ipaustralia.extract.config;


import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import au.gov.ipaustralia.rio.ipd.sdsm.web.metrics.MetricsConfig;

@ComponentScan(basePackages = {"au.gov.ipaustralia.extract"})
@Import(value = {MetricsConfig.class, WebConfig.class})
@Configuration
@EnableAsync
@EnableScheduling
public class AppConfig {
    private static final Logger LOG = LoggerFactory.getLogger(AppConfig.class);

    @Bean
    public PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer x = new PropertySourcesPlaceholderConfigurer();
        
        String envFile = System.getProperty("sdsm.env.file.path");
        
        if (envFile == null || envFile.isEmpty()) {
        	throw new RuntimeException("sdsm.env.file.path property not set");
        }
        
        LOG.info("Configuring from [{}]", envFile);
        
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new FileSystemResource(envFile));
        x.setProperties(yaml.getObject());
        return x;
    }
    
    @Bean(name = "threadPoolTaskExecutor")
	public ThreadPoolTaskExecutor taskExecutor(@Value("${pools.asyncThreadMaxPoolSize}") int maxPoolSize) {
		ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
		pool.setCorePoolSize(maxPoolSize);
		pool.setMaxPoolSize(maxPoolSize);
		return pool;
	}
    
    @Bean(name = "binaryExtractPoolTaskExecutor")
    public ThreadPoolTaskExecutor binaryExtractTaskExecutor(@Value("${pools.asyncBinaryExtractMaxPoolSize}") int maxPoolSize) {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(maxPoolSize);
        pool.setMaxPoolSize(maxPoolSize);
        return pool;
    }
    
    @Bean(name = "hashPoolTaskExecutor")
    public ThreadPoolTaskExecutor hashTaskExecutor(@Value("${pools.hashThreadMaxPoolSize}") int maxPoolSize) {
    	ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
		pool.setCorePoolSize(maxPoolSize);
		pool.setMaxPoolSize(maxPoolSize);
		return pool;
	}

    @PreDestroy
    public void adsf() {
        LOG.info("PRE DESTROY");
    }

}
