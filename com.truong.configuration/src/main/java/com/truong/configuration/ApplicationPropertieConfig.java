package com.truong.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationPropertieConfig {

	@Value("${spring.datasource.driver-class-name}")
	private String driverClassname;
	
	@Value("${spring.datasource.url}")
	private String datasourceUrl;

	@Value("${spring.datasource.username}")
	private String datasourceUsername;

	@Value("${spring.datasource.password}")
	private String datasourcePassword;
	
	@Value("${project.oauth.port}")
	private int oauthPort;
	
	@Value("${project.restapi.port}")
	private int restapiPort;
	
	@Value("${oauth2.endpoint}")
	private String oauth2Endpoint;
	
	@Value("${hikaricp.connectionTimeout}")
	private long hikariCP_ConnectionTimeout;

	@Value("${hikaricp.idleTimeout}")
	private long hikariCP_IdleTimeout;

	@Value("${hikaricp.maxLifetime}")
	private long hikariCP_MaxLifetime;

	@Value("${hikaricp.maximumPoolSize}")
	private int hikariCP_MaximumPoolSize;

	@Value("${hikaricp.minimumIdle}")
	private int hikariCP_MinimumIdle;

	@Value("${hikaricp.cachePrepStmts}")
	private String hikariCP_CachePrepStmts;

	@Value("${hikaricp.cacheResultSetMetadata}")
	private String hikariCP_CacheResultSetMetadata;

	@Value("${hikaricp.cacheServerConfiguration}")
	private String hikariCP_CacheServerConfiguration;

	@Value("${hikaricp.prepStmtCacheSize}")
	private int hikariCP_PrepStmtCacheSize;

	@Value("${hikaricp.prepStmtCacheSqlLimit}")
	private int hikariCP_PrepStmtCacheSqlLimit;

	@Value("${hikaricp.useServerPrepStmts}")
	private String hikariCP_UseServerPrepStmts;

	@Value("${hikaricp.useLocalSessionState}")
	private String hikariCP_UseLocalSessionState;

	@Value("${hikaricp.rewriteBatchedStatements}")
	private String hikariCP_RewriteBatchedStatements;

	@Value("${hikaricp.elideSetAutoCommit}")
	private String hikariCP_ElideSetAutoCommit;

	@Value("${hikaricp.maintainTimeStats}")
	private String hikariCP_MaintainTimeStats;

	public int getOauthPort() {
		return oauthPort;
	}

	public void setOauthPort(int oauthPort) {
		this.oauthPort = oauthPort;
	}

	public String getOauth2Endpoint() {
		return oauth2Endpoint;
	}

	public void setOauth2Endpoint(String oauth2Endpoint) {
		this.oauth2Endpoint = oauth2Endpoint;
	}

	public String getDatasourceUrl() {
		return datasourceUrl;
	}

	public void setDatasourceUrl(String datasourceUrl) {
		this.datasourceUrl = datasourceUrl;
	}

	public String getDatasourceUsername() {
		return datasourceUsername;
	}

	public void setDatasourceUsername(String datasourceUsername) {
		this.datasourceUsername = datasourceUsername;
	}

	public String getDatasourcePassword() {
		return datasourcePassword;
	}

	public void setDatasourcePassword(String datasourcePassword) {
		this.datasourcePassword = datasourcePassword;
	}

	public int getRestapiPort() {
		return restapiPort;
	}

	public void setRestapiPort(int restapiPort) {
		this.restapiPort = restapiPort;
	}

	public long getHikariCP_ConnectionTimeout() {
		return hikariCP_ConnectionTimeout;
	}

	public void setHikariCP_ConnectionTimeout(long hikariCP_ConnectionTimeout) {
		this.hikariCP_ConnectionTimeout = hikariCP_ConnectionTimeout;
	}

	public long getHikariCP_IdleTimeout() {
		return hikariCP_IdleTimeout;
	}

	public void setHikariCP_IdleTimeout(long hikariCP_IdleTimeout) {
		this.hikariCP_IdleTimeout = hikariCP_IdleTimeout;
	}

	public long getHikariCP_MaxLifetime() {
		return hikariCP_MaxLifetime;
	}

	public void setHikariCP_MaxLifetime(long hikariCP_MaxLifetime) {
		this.hikariCP_MaxLifetime = hikariCP_MaxLifetime;
	}

	public int getHikariCP_MaximumPoolSize() {
		return hikariCP_MaximumPoolSize;
	}

	public void setHikariCP_MaximumPoolSize(int hikariCP_MaximumPoolSize) {
		this.hikariCP_MaximumPoolSize = hikariCP_MaximumPoolSize;
	}

	public int getHikariCP_MinimumIdle() {
		return hikariCP_MinimumIdle;
	}

	public void setHikariCP_MinimumIdle(int hikariCP_MinimumIdle) {
		this.hikariCP_MinimumIdle = hikariCP_MinimumIdle;
	}

	public String getHikariCP_CachePrepStmts() {
		return hikariCP_CachePrepStmts;
	}

	public void setHikariCP_CachePrepStmts(String hikariCP_CachePrepStmts) {
		this.hikariCP_CachePrepStmts = hikariCP_CachePrepStmts;
	}

	public String getHikariCP_CacheResultSetMetadata() {
		return hikariCP_CacheResultSetMetadata;
	}

	public void setHikariCP_CacheResultSetMetadata(String hikariCP_CacheResultSetMetadata) {
		this.hikariCP_CacheResultSetMetadata = hikariCP_CacheResultSetMetadata;
	}

	public String getHikariCP_CacheServerConfiguration() {
		return hikariCP_CacheServerConfiguration;
	}

	public void setHikariCP_CacheServerConfiguration(String hikariCP_CacheServerConfiguration) {
		this.hikariCP_CacheServerConfiguration = hikariCP_CacheServerConfiguration;
	}

	public int getHikariCP_PrepStmtCacheSize() {
		return hikariCP_PrepStmtCacheSize;
	}

	public void setHikariCP_PrepStmtCacheSize(int hikariCP_PrepStmtCacheSize) {
		this.hikariCP_PrepStmtCacheSize = hikariCP_PrepStmtCacheSize;
	}

	public int getHikariCP_PrepStmtCacheSqlLimit() {
		return hikariCP_PrepStmtCacheSqlLimit;
	}

	public void setHikariCP_PrepStmtCacheSqlLimit(int hikariCP_PrepStmtCacheSqlLimit) {
		this.hikariCP_PrepStmtCacheSqlLimit = hikariCP_PrepStmtCacheSqlLimit;
	}

	public String getHikariCP_UseServerPrepStmts() {
		return hikariCP_UseServerPrepStmts;
	}

	public void setHikariCP_UseServerPrepStmts(String hikariCP_UseServerPrepStmts) {
		this.hikariCP_UseServerPrepStmts = hikariCP_UseServerPrepStmts;
	}

	public String getHikariCP_UseLocalSessionState() {
		return hikariCP_UseLocalSessionState;
	}

	public void setHikariCP_UseLocalSessionState(String hikariCP_UseLocalSessionState) {
		this.hikariCP_UseLocalSessionState = hikariCP_UseLocalSessionState;
	}

	public String getHikariCP_RewriteBatchedStatements() {
		return hikariCP_RewriteBatchedStatements;
	}

	public void setHikariCP_RewriteBatchedStatements(String hikariCP_RewriteBatchedStatements) {
		this.hikariCP_RewriteBatchedStatements = hikariCP_RewriteBatchedStatements;
	}

	public String getHikariCP_ElideSetAutoCommit() {
		return hikariCP_ElideSetAutoCommit;
	}

	public void setHikariCP_ElideSetAutoCommit(String hikariCP_ElideSetAutoCommit) {
		this.hikariCP_ElideSetAutoCommit = hikariCP_ElideSetAutoCommit;
	}

	public String getHikariCP_MaintainTimeStats() {
		return hikariCP_MaintainTimeStats;
	}

	public void setHikariCP_MaintainTimeStats(String hikariCP_MaintainTimeStats) {
		this.hikariCP_MaintainTimeStats = hikariCP_MaintainTimeStats;
	}

	public String getDriverClassname() {
		return driverClassname;
	}

	public void setDriverClassname(String driverClassname) {
		this.driverClassname = driverClassname;
	}
	
	
}
