package vn.zalopay.hack.vertx.vertical;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.invoke.MethodHandles;
import java.util.concurrent.TimeUnit;

/** Created by thuyenpt Date: 2020-03-24 */
public class ExecuteBlocking extends AbstractVerticle {
  private static final Logger LOGGER =
      LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getCanonicalName());

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new ExecuteBlocking());
  }

  @Override
  public void start() throws Exception {
    vertx.setPeriodic(
        1000,
        id -> {
          LOGGER.info("Tick");
          vertx.executeBlocking(
              promise -> {
                LOGGER.info("Start sleep");
                try {
                  TimeUnit.MICROSECONDS.sleep(500);
                  LOGGER.info("Wake up");
                  promise.complete("OK!");
                } catch (Exception e) {
                  promise.fail(e);
                }
              },false,
              asyncResult -> {
                if (asyncResult.succeeded()) {
                  LOGGER.info("blocking code result: {}", asyncResult.result());
                } else {
                  LOGGER.error("execute blocking meet error: {}", asyncResult.cause().getMessage());
                }
              });
        });
  }
}
