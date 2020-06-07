package vn.sparrow.vertx.common;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

/** Created by thuyenpt Date: 2020-06-07 */
public class VertxUtils {
  private VertxUtils() {}

  public static Vertx getVertxInstance() {
    return Vertx.vertx(
        new VertxOptions()
            .setPreferNativeTransport(true)
            .setWorkerPoolSize(4)
            .setEventLoopPoolSize(4));
  }
}
