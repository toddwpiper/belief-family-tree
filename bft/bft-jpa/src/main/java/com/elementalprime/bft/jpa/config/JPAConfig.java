package com.elementalprime.bft.jpa.config;

import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableJpaRepositories(basePackages = "com.elementalprime",
                       entityManagerFactoryRef = "entityManagerFactory",
                       transactionManagerRef = "transactionManager")
@EnableTransactionManagement(proxyTargetClass = true)
public class JPAConfig {
    private static final Logger LOG = LoggerFactory.getLogger(JPAConfig.class);

    public static final String NAME_PERSISTENCE_UNIT = "bftPU";

    public static final String NAME_TRANSACTION_MANAGER = "transactionManager";

    public static final String NAME_DATA_SOURCE = "dataSource";

    public static final String NAME_ENTITY_MANAGER = "entityManager";

    public static final String NAME_ENTITY_MANAGER_FACTORY = "entityManagerFactory";

    @Value("${datasource.url}")
    private String dataSourceUrl;
    
    @Value("${datasource.dbMaxPoolSize}")
    private int maxPoolSize;
    
    private Long connectionTimeout = new Long("3000");
    
    private Long maxLifetime = new Long("1800000");
    
    private String dataSourceClassName = "org.postgresql.Driver";

    private String connectionTestQuery = "SELECT 1";

    private String schema = "bft";

    @Primary
    @Bean(name = NAME_DATA_SOURCE)
    public DataSource awsDataSource() {

        LOG.info("Configuring HikariCP datasource for AWS");
        HikariConfig hc = new HikariConfig();
        hc.setConnectionTestQuery(connectionTestQuery);
        hc.setConnectionTimeout(connectionTimeout);
        hc.setDriverClassName(dataSourceClassName);
        hc.setJdbcUrl(dataSourceUrl);
        hc.setMaximumPoolSize(maxPoolSize);
        hc.setMaxLifetime(maxLifetime);

        // to pass to the underlying datasource
        Properties dsProperties = new Properties();
        dsProperties.setProperty("url", dataSourceUrl);
        dsProperties.setProperty("hibernate.default_schema", schema);

        hc.setDataSourceProperties(dsProperties);

        // Security.insertProviderAt(new OraclePKIProvider(), 3);

        return new HikariDataSource(hc);
    }

    @Primary
    @Bean(name = JPAConfig.NAME_ENTITY_MANAGER_FACTORY)
    public LocalContainerEntityManagerFactoryBean awsEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean b = new LocalContainerEntityManagerFactoryBean();
        b.setDataSource(awsDataSource());
        b.setPackagesToScan("au.gov.ipaustralia.rio.sdsm.aws.jpa.entity");
        b.setPersistenceUnitName(NAME_PERSISTENCE_UNIT);

        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setShowSql(false);
        adapter.setDatabasePlatform("org.hibernate.dialect.PostgreSQL92Dialect");
        adapter.setGenerateDdl(false);
        adapter.setDatabase(Database.POSTGRESQL);
        b.setJpaVendorAdapter(adapter);

        return b;
    }

    @Primary
    @Bean(name = { JPAConfig.NAME_TRANSACTION_MANAGER })
    public PlatformTransactionManager awsTransactionManager(@Qualifier(JPAConfig.NAME_ENTITY_MANAGER_FACTORY) EntityManagerFactory emf) {

        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(emf);

        return txManager;
    }

    @Primary
    @Bean(name = { JPAConfig.NAME_ENTITY_MANAGER })
    public EntityManager entityManager(@Qualifier(JPAConfig.NAME_ENTITY_MANAGER_FACTORY) EntityManagerFactory emf) {
        return emf.createEntityManager();
    }

}
