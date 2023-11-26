package com.truong.restapi.security;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.truong.configuration.ApplicationPropertieConfig;
import com.truong.restapi.config.ReplicationRoutingDataSource;
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
		System.out.println(String.format("Datasource master url: %s", config.getDatasourceMasterUrl()));
		System.out.println(String.format("Datasource slave url: %s", config.getDatasourceSlaveUrl()));
		System.out.println("=====End=====");
	}

//	@Bean
//	public DataSource dataSource() {
//		HikariConfig hikariConfig = this.initHikariPoolingConfig("hikari-pool");
//		hikariConfig.setJdbcUrl(config.getDatasourceUrl());
//		hikariConfig.setUsername(config.getDatasourceUsername());
//		hikariConfig.setPassword(config.getDatasourcePassword());
//
//		HikariDataSource hikariPoolingDataSource = new HikariDataSource(hikariConfig);
//
//		return hikariPoolingDataSource;
//	}
	
	
	@Bean
	DataSource writeOnlyDataSource() {
		HikariConfig hikariConfig = this.initHikariPoolingConfig("hikari-master-pool");
		hikariConfig.setJdbcUrl(config.getDatasourceMasterUrl());
		hikariConfig.setUsername(config.getDatasourceMasterUsername());
		hikariConfig.setPassword(config.getDatasourceMasterPassword());
		hikariConfig.setReadOnly(false);
		
		return new HikariDataSource(hikariConfig);
	}
	
	@Bean
	DataSource readOnlyDataSource() {
		HikariConfig hikariConfig = this.initHikariPoolingConfig("hikari-slave-pool");
		hikariConfig.setJdbcUrl(config.getDatasourceSlaveUrl());
		hikariConfig.setUsername(config.getDatasourceSlaveUsername());
		hikariConfig.setPassword(config.getDatasourceSlavePassword());
		hikariConfig.setReadOnly(true);
		
		return new HikariDataSource(hikariConfig);
	}
	
	@Bean
    DataSource routingDataSource() {
        ReplicationRoutingDataSource routingDataSource = new ReplicationRoutingDataSource();

        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put("write", writeOnlyDataSource());
        dataSourceMap.put("read", readOnlyDataSource());
        routingDataSource.setTargetDataSources(dataSourceMap);
        routingDataSource.setDefaultTargetDataSource(writeOnlyDataSource());

        return routingDataSource;
    }
	
	/*
	 * @Primary, điều này có nghĩa là nó sẽ được chọn làm DataSource mặc định nếu có nhiều DataSource bean trong ứng dụng
	 * Trong Spring Framework, @Qualifier là một annotation được sử dụng để xác định rõ ràng bean nào nên được sử dụng trong trường hợp có nhiều bean cùng loại. Annotation này giúp Spring hiểu được bạn muốn inject bean nào khi có nhiều bean cùng loại được khai báo.
	 */
	@Primary
    @Bean
    @DependsOn({"writeOnlyDataSource", "readOnlyDataSource", "routingDataSource"})
    DataSource dataSource() {
        return new LazyConnectionDataSourceProxy(routingDataSource());
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
//	@Bean
//	@Primary
//	LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("dataSource") DataSource ds)
//			throws PropertyVetoException {
//		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
//		entityManagerFactory.setDataSource(ds);
//		entityManagerFactory.setPackagesToScan(new String[] { "com.truong.entity" });
//		JpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
//		entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter);
//		return entityManagerFactory;
//	}

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
