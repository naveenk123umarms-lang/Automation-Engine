package com.jsp.automation_engine.automationconfig;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class AutomationConfig {
    @Bean
    public DataSource dataSource(){
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(System.getenv(AppConstants.DB_URL));
        config.setUsername(System.getenv(AppConstants.DB_USERNAME));
        config.setPassword(System.getenv(AppConstants.DB_PASSWORD));
        config.setDriverClassName(AppConstants.MYSQL_DRIVER);
        HikariDataSource hikariDataSource = new HikariDataSource(config);
        return hikariDataSource;

    }
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {

        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();

        emf.setDataSource(dataSource);
        emf.setPackagesToScan(AppConstants.BASE_PACKAGE);

        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setShowSql(true);
        emf.setJpaVendorAdapter(adapter);

        Map<String, Object> props = new HashMap<>();
        props.put(AppConstants.HBM2DDL_AUTO,"update");

        emf.setJpaPropertyMap(props);

        return emf;
    }

    @Bean
    public JpaTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean emf) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(emf.getObject());
        return txManager;
    }
}
