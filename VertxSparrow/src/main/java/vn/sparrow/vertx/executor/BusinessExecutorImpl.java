package vn.sparrow.vertx.executor;

import com.google.protobuf.MessageOrBuilder;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.sparrow.vertx.common.utils.LoggingUtil;

import java.lang.invoke.MethodHandles;
import java.util.function.Supplier;

/** Created by thuyenpt Date: 2020-06-18 */
public class BusinessExecutorImpl implements BusinessExecutor {
  // Handle your business logic here
  private static final Logger LOGGER =
      LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getCanonicalName());

  @Override
  public Future<Void> handleFoo() {
    return Future.failedFuture("Haven't implement yet");
  }

  private <R> void handle(
      String methodName,
      MessageOrBuilder request,
      Promise<R> responsePromise,
      Supplier<Future<R>> supplier,
      R defaultResponse) {
    LoggingUtil.LogReceive(methodName, LOGGER, request);
    supplier
        .get()
        .onComplete(
            res -> {
              if (res.succeeded()) {
                responsePromise.complete(res.result());
              } else {
                responsePromise.complete(defaultResponse);
              }
            });
  }
}
