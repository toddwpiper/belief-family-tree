package com.elementalprime.bft.jpa.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.elementalprime.bft.jpa.test.config.TestH2Config;

@Configuration
@Import({JPAConfig.class, TestH2Config.class})
public class TestJPAConfig {
    private static final Logger LOG = LoggerFactory.getLogger(TestJPAConfig.class);
    
}
