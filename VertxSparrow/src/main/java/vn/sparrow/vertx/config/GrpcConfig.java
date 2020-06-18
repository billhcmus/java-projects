package vn.sparrow.vertx.config;

import lombok.Getter;
import lombok.Setter;

/** Created by thuyenpt Date: 2019-11-13 */
@Getter
@Setter
public class GrpcConfig {
  private int port = 53001;
  private int numInstances = Runtime.getRuntime().availableProcessors();
  private boolean nativeTransportEnable = true;
  private int keepAliveTime = 10000;
  private int keepAliveTimeout = 5000;
  private long maxConnectionAge = Long.MAX_VALUE;
  private boolean keepAlivePermitWithoutCalls = true;
}
