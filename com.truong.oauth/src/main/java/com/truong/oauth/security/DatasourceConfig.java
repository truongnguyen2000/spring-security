package com.truong.oauth.security;

import java.sql.Connection;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.truong.configuration.ApplicationPropertieConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan("com.truong")
@EnableTransactionManagement
@EnableAutoConfiguration(exclude=HibernateJpaAutoConfiguration.class)
public class DatasourceConfig {

	@Autowired
	private ApplicationPropertieConfig config;

	@Autowired
	private Environment environment;

	@Bean
	void initSetting() {
		System.out.println("=====Loading config=====");
		System.out.println(String.format("Datasource url: %s", config.getDatasourceUrl()));
		System.out.println("=====End=====");
	}

	@Bean
	public DataSource dataSource() {
		HikariConfig hikariConfig = this.initHikariPoolingConfig("hikari-pool");
		hikariConfig.setJdbcUrl(config.getDatasourceUrl());
		hikariConfig.setUsername(config.getDatasourceUsername());
		hikariConfig.setPassword(config.getDatasourcePassword());

		HikariDataSource hikariPoolingDataSource = new HikariDataSource(hikariConfig);

		return hikariPoolingDataSource;
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan(new String[] { "com.truong.entity" });

							// SpringSessionContext
		sessionFactory.setHibernateProperties(hibernateProperties());

		return sessionFactory;
	}

	private Properties hibernateProperties() {
		Properties properties = new Properties();
		properties.put(org.hibernate.cfg.Environment.DIALECT, environment.getRequiredProperty("hibernate.dialect"));
		properties.put(org.hibernate.cfg.Environment.SHOW_SQL, environment.getRequiredProperty("hibernate.show_sql"));
		properties.put(org.hibernate.cfg.Environment.FORMAT_SQL,
				environment.getRequiredProperty("hibernate.format_sql"));
		properties.put(org.hibernate.cfg.Environment.CURRENT_SESSION_CONTEXT_CLASS,
				environment.getRequiredProperty("hibernate.current_session_context_class"));
		return properties;
	}

	@Bean
	public HibernateTransactionManager transactionManager(LocalSessionFactoryBean sessionFactory) {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory.getObject());
		return transactionManager;
	}

//	/*
//	 * Scan entity để sử dụng JPA Nếu không sẽ gặp lỗi not an entity
//	 */
////	@Bean
////	@Primary
////	LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("dataSource") DataSource ds)
////			throws PropertyVetoException {
////		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
////		entityManagerFactory.setDataSource(ds);
////		entityManagerFactory.setPackagesToScan(new String[] { "com.truong.entity" });
////		JpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
////		entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter);
////		return entityManagerFactory;
////	}

	private HikariConfig initHikariPoolingConfig(String poolName) {
		HikariConfig hikariConfig = new HikariConfig();

		hikariConfig.setPoolName(poolName);
		hikariConfig.setDriverClassName(config.getDriverClassname());
		hikariConfig.setConnectionTimeout(config.getHikariCP_ConnectionTimeout());
		hikariConfig.setIdleTimeout(config.getHikariCP_IdleTimeout());
		hikariConfig.setMaximumPoolSize(config.getHikariCP_MaximumPoolSize());
		hikariConfig.setMinimumIdle(config.getHikariCP_MinimumIdle());
		hikariConfig.setMaxLifetime(config.getHikariCP_MaxLifetime());
		hikariConfig.setTransactionIsolation(String.valueOf(Connection.TRANSACTION_READ_COMMITTED));

		hikariConfig.addDataSourceProperty("cachePrepStmts", config.getHikariCP_CachePrepStmts());
		hikariConfig.addDataSourceProperty("prepStmtCacheSize", config.getHikariCP_PrepStmtCacheSize());
		hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", config.getHikariCP_PrepStmtCacheSqlLimit());

		return hikariConfig;
	}
}
