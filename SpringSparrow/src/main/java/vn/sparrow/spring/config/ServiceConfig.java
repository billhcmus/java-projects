package vn.sparrow.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by thuyenpt Date: 2020-05-20
 */

@Configuration
public class ServiceConfig {
  @Bean
  DatabaseConnector mysqlConnector() {
    DatabaseConnector connector = new MysqlConnector();
    connector.setUrl("jdbc:mysql://localhost:3306/db");
    return connector;
  }

  @Bean
  DatabaseConnector postgreSqlConnector() {
    DatabaseConnector connector = new PostgreSqlConnector();
    connector.setUrl("postgresql://localhost/db");
    return connector;
  }
}
