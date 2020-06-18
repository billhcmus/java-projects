package vn.sparrow.vertx.common.da;

import io.vertx.ext.sql.SQLClient;
import vn.sparrow.vertx.config.MySQLConfig;

import javax.sql.DataSource;

/** Created by thuyenpt Date: 2019-11-14 */
public interface DataSourceProvider {
  DataSource getDataSource(MySQLConfig config);
  SQLClient getVertxDataSource(DataSource dataSource);
}
