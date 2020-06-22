package vn.zalopay.hack.vertx.vertical;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/** Created by thuyenpt Date: 2020-03-22 */
public class HelloVertical extends AbstractVerticle {
  private static final Logger LOGGER =
      LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getCanonicalName());

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(() -> new AbstractVerticle() {
      @Override
      public void start(Promise<Void> startPromise) throws Exception {
        vertx.setPeriodic(1000, event -> LOGGER.info("Tick: {}", event));
        vertx.createHttpServer().requestHandler(event -> {
          LOGGER.info("Request from: {}", event.remoteAddress().host());
          event.response().end("Hello from server!");
        }).listen(8080, rs -> {
          if (rs.succeeded()) {
            startPromise.complete();
          } else {
            startPromise.fail(rs.cause());
          }
        });
        LOGGER.info("Http server listening at {}", 8080);
      }
    }, new DeploymentOptions().setInstances(2));
  }
}
