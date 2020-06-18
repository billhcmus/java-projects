package vn.sparrow.vertx.common.da;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.vertx.core.Vertx;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLClient;
import io.vertx.micrometer.backends.BackendRegistries;
import vn.sparrow.vertx.config.MySQLConfig;

import javax.sql.DataSource;

/** Created by thuyenpt Date: 2019-11-14 */
public class DataSourceProviderImpl implements DataSourceProvider {
  private Vertx vertx;

  public DataSourceProviderImpl(Vertx vertx) {
    this.vertx = vertx;
  }

  @Override
  public DataSource getDataSource(MySQLConfig config) {
    HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setDriverClassName(config.getDriver());
    hikariConfig.setJdbcUrl(config.getUrl());
    hikariConfig.setUsername(config.getUsername());
    hikariConfig.setPassword(config.getPassword());
    hikariConfig.setMaximumPoolSize(config.getPoolSize());
    hikariConfig.setAutoCommit(config.isAutoCommit());
    hikariConfig.addDataSourceProperty("useServerPrepStmts", "" + config.isUseServerPrepStmts());
    hikariConfig.addDataSourceProperty("cachePrepStmts", "" + config.isCachePrepStmts());
    hikariConfig.addDataSourceProperty("prepStmtCacheSize", "" + config.getPrepStmtCacheSize());
    hikariConfig.addDataSourceProperty(
        "prepStmtCacheSqlLimit", "" + config.getPrepStmtCacheSqlLimit());
    hikariConfig.addDataSourceProperty("maxLifetime", "" + config.getMaxLifetimeMillis());
    hikariConfig.setMetricRegistry(BackendRegistries.getDefaultNow());

    return new HikariDataSource(hikariConfig);
  }

  @Override
  public SQLClient getVertxDataSource(DataSource dataSource) {
    return JDBCClient.create(vertx, dataSource);
  }
}
