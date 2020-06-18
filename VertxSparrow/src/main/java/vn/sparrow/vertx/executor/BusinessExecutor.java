package vn.sparrow.vertx.executor;


import io.vertx.core.Future;

/**
 * Created by thuyenpt Date: 2020-06-18
 */
public interface BusinessExecutor {
  Future<Void> handleFoo();
}
