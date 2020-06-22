package vn.sparrow.spring.config;

import lombok.extern.log4j.Log4j2;

/**
 * Created by thuyenpt Date: 2020-05-25
 */
@Log4j2
public class PostgreSqlConnector extends DatabaseConnector {
  @Override
  public void connect() {
    log.info("Connect to postgreSQL server");
  }
}
