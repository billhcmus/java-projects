package vn.sparrow.vertx;

import io.grpc.BindableService;
import io.grpc.ServerInterceptor;
import io.vertx.core.Vertx;
import io.vertx.grpc.BlockingServerInterceptor;
import vn.sparrow.vertx.common.exception.InvalidParameterException;
import vn.sparrow.vertx.common.server.GrpcServerScaling;
import vn.sparrow.vertx.common.server.SparrowServerInterceptor;
import vn.sparrow.vertx.common.utils.ConfigLoaderUtils;
import vn.sparrow.vertx.common.utils.ExceptionUtils;
import vn.sparrow.vertx.common.utils.VertxUtils;
import vn.sparrow.vertx.config.ServerConfig;
import vn.sparrow.vertx.service.GreeterService;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/** Created by thuyenpt Date: 5/29/20 */
public class Runner {
  public static void main(String[] args) throws FileNotFoundException, InvalidParameterException {
    ServerConfig serverConfig = ConfigLoaderUtils.load(ServerConfig.class, "server.conf");
    ConfigLoaderUtils.printValue(serverConfig);
    Vertx vertx = VertxUtils.getVertxInstance(serverConfig.getVertxConfig());

    List<BindableService> bindableServices = new ArrayList<>();

    // Add service
    GreeterService greeterService = new GreeterService();
    bindableServices.add(greeterService);

    // Server interceptor
    ServerInterceptor serverInterceptor =
        BlockingServerInterceptor.wrap(vertx, new SparrowServerInterceptor());

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
