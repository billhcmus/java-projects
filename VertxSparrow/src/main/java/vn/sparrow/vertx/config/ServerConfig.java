package vn.sparrow.vertx.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Created by thuyenpt Date: 2019-11-13 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServerConfig {
  private VertxConfig vertxConfig;
  private GrpcConfig grpcConfig;
  private MySQLConfig mySQLConfig;
}
