package vn.zalopay.hack.vertx.vertical;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/** Created by thuyenpt Date: 2020-03-23 */
public class EmptyVertical extends AbstractVerticle {
  private static final Logger LOGGER =
      LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getCanonicalName());

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    LOGGER.info("Start vertical.");
    startPromise.complete();
  }

  @Override
  public void stop(Promise<Void> stopPromise) throws Exception {
    LOGGER.info("Stop vertical.");
    stopPromise.complete();
  }
}
