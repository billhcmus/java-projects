package vn.sparrow.vertx;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.WorkerExecutor;

/**
 * Created by thuyenpt
 * Date: 5/29/20
 */
public class Runner {
  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx(new VertxOptions().setWorkerPoolSize(64));

    WorkerExecutor executor = vertx.createSharedWorkerExecutor("my-worker-pool");

  }
}
