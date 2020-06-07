package vn.sparrow.vertx.server;

import io.grpc.BindableService;
import io.grpc.ServerInterceptor;
import io.grpc.ServerInterceptors;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.grpc.VertxServer;
import io.vertx.grpc.VertxServerBuilder;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vn.sparrow.vertx.common.ExceptionUtils;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.concurrent.TimeUnit;

/** Created by thuyenpt Date: 2020-06-07 */
@AllArgsConstructor
public class GrpcServerScaling {
  private static final Logger LOGGER =
      LogManager.getLogger(MethodHandles.lookup().lookupClass().getCanonicalName());
  private Vertx vertx;
  private List<BindableService> bindableServices;
  private ServerInterceptor serverInterceptor;

  public void start() {
    createServerScaling();
  }

  public void stop() {
  }

  private void createServerScaling() {
    VertxServerBuilder vertxServerBuilder = VertxServerBuilder.forPort(vertx, 53001);
    bindableServices.forEach(
        bindableService ->
            vertxServerBuilder.addService(
                ServerInterceptors.intercept(bindableService, serverInterceptor)));

    vertx.deployVerticle(
        () -> {
          VertxServer vertxServer = vertxServerBuilder.build();
          return new AbstractVerticle() {
            @Override
            public void start() {
              vertxServer.start(
                  res -> {
                    if (res.succeeded()) {
                      LOGGER.info("gRPC service start listening at port {}", 53001);
                    } else {
                      LOGGER.info(
                          "gRPC service start error, error={}",
                          ExceptionUtils.getDetail(res.cause()));
                    }
                  });
            }

            @Override
            public void stop() throws Exception {
              vertxServer.shutdown().awaitTermination(5, TimeUnit.SECONDS);
            }
          };
        },
        new DeploymentOptions().setInstances(4));
  }
}
