package vn.sparrow.vertx.service;

import examples.GreeterGrpc;
import examples.HelloReply;
import examples.HelloRequest;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandles;

/** Created by thuyenpt Date: 2020-06-07 */
@AllArgsConstructor
public class GreeterService extends GreeterGrpc.GreeterVertxImplBase {
  private static final Logger LOGGER =
      LogManager.getLogger(MethodHandles.lookup().lookupClass().getCanonicalName());

  @Override
  public void sayHello(HelloRequest request, Promise<HelloReply> response) {
    handleHelloRequest(request)
        .onComplete(
            res -> {
              if (res.succeeded()) {
                response.complete(res.result());
              } else {
                response.complete();
              }
            });
  }

  private Future<HelloReply> handleHelloRequest(HelloRequest request) {
    LOGGER.info("Hello {}", request.getName());
    return Future.succeededFuture(
        HelloReply.newBuilder().setMessage("Reply from Vertx Grpc server").build());
  }
}
