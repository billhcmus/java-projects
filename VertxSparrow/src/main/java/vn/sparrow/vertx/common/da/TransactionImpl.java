package vn.sparrow.vertx.common.da;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.ext.sql.SQLClient;
import io.vertx.ext.sql.SQLConnection;

import java.sql.SQLException;
import java.util.function.Consumer;

/** Created by thuyenpt Date: 2019-11-22 */
public class TransactionImpl implements Transaction {
  private SQLConnection connection;
  private SQLClient sqlClient;

  public TransactionImpl(SQLClient sqlClient) {
    this.sqlClient = sqlClient;
  }

  @Override
  public Future<Void> begin() {
    Promise<Void> promise = Promise.promise();
    sqlClient.getConnection(
        rs -> {
          if (rs.failed()) {
            promise.fail(rs.cause());
          } else {
            connection = rs.result();
            disableAutoCommit(promise);
          }
        });
    return promise.future();
  }

  private void disableAutoCommit(Promise<Void> promise) {
    connection.setAutoCommit(
        false,
        rs -> {
          if (rs.succeeded()) {
            promise.complete();
          } else {
            promise.fail(rs.cause());
          }
        });
  }

  @Override
  public Future<Void> commit() {
    return handle(promise -> connection.commit(rs -> handleResult(promise, rs)));
  }

  @Override
  public Future<Void> rollback() {
    return handle(promise -> connection.rollback(rs -> handleResult(promise, rs)));
  }

  @Override
  public Future<Void> close() {
    return handle(promise -> connection.close(rs -> handleResult(promise, rs)));
  }

  private Future<Void> handle(Consumer<Promise<Void>> consumer) {
    if (connection == null) {
      return Future.failedFuture(new SQLException("Transaction NOT start yet"));
    }

    Promise<Void> promise = Promise.promise();
    consumer.accept(promise);
    return promise.future();
  }

  private void handleResult(Promise<Void> promise, AsyncResult<Void> asyncResult) {
    if (asyncResult.succeeded()) {
      promise.complete();
    } else {
      promise.fail(asyncResult.cause());
    }
  }

  @Override
  public <R> Future<R> execute(Executable<R> executable) {
    return executable.execute(connection);
  }
}
