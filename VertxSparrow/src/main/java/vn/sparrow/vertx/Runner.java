package vn.sparrow.vertx;

import io.grpc.BindableService;
import io.grpc.ServerInterceptor;
import io.vertx.core.Vertx;
import io.vertx.grpc.BlockingServerInterceptor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vn.sparrow.vertx.common.ExceptionUtils;
import vn.sparrow.vertx.common.VertxUtils;
import vn.sparrow.vertx.interceptor.SparrowInterceptor;
import vn.sparrow.vertx.server.GrpcServerScaling;
import vn.sparrow.vertx.service.GreeterService;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

/** Created by thuyenpt Date: 5/29/20 */
public class Runner {
  private static final Logger LOGGER =
      LogManager.getLogger(MethodHandles.lookup().lookupClass().getCanonicalName());

  public static void main(String[] args) {
    Vertx vertx = VertxUtils.getVertxInstance();
    List<BindableService> bindableServices = new ArrayList<>();

    // Add service
    GreeterService greeterService = new GreeterService();
    bindableServices.add(greeterService);

    // Server interceptor
    ServerInterceptor serverInterceptor =
        BlockingServerInterceptor.wrap(vertx, new SparrowInterceptor());

    GrpcServerScaling grpcServerScaling =
        new GrpcServerScaling(vertx, bindableServices, serverInterceptor);

    grpcServerScaling.start();
    addShutdownHook(vertx, grpcServerScaling);
  }

  private static void addShutdownHook(Vertx vertx, GrpcServerScaling serverScaling) {
    Runtime.getRuntime()
        .addShutdownHook(
            new Thread(
                () -> {
                  serverScaling.stop();
                  vertx.close(
                      res -> {
                        if (res.succeeded()) {
                          System.out.println("Vertx close complete");
                        } else {
                          System.out.println(
                              "Vertx close get error=" + ExceptionUtils.getDetail(res.cause()));
                        }
                      });
                  System.out.println("Shutdown complete");
                }));
  }
}
