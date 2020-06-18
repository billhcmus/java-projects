package vn.sparrow.vertx.config;

import io.vertx.core.impl.cpu.CpuCoreSensor;
import lombok.Getter;
import lombok.Setter;

/** Created by thuyenpt Date: 2020-06-14 */
@Getter
@Setter
public class VertxConfig {
  private int prometheusPort = 8089;
  private int workerPoolSize = CpuCoreSensor.availableProcessors() * 2;
  private int eventLoopPoolSize = CpuCoreSensor.availableProcessors() * 2;
  private long maxWorkerExecutionTimeMillis = 100;
  private long maxEventLoopExecutionTimeMillis = 10;
}
