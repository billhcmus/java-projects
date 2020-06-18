package vn.sparrow.vertx.common.da;

import io.vertx.core.Future;
import io.vertx.ext.sql.SQLConnection;


/** Created by thuyenpt Date: 2020-06-14 */
@FunctionalInterface
public interface Executable<R> {
  Future<R> execute(SQLConnection connection);
}
