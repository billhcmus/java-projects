package vn.sparrow.vertx.common.da;

import io.vertx.core.Future;

/** Created by thuyenpt Date: 2019-11-22 */
public interface Transaction {
  Future<Void> begin();

  Future<Void> commit();

  Future<Void> rollback();

  Future<Void> close();

  <R> Future<R> execute(Executable<R> executable);
}
