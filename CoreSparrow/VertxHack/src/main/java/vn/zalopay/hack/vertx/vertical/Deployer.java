package vn.zalopay.hack.vertx.vertical;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * Created by thuyenpt Date: 2020-03-23
 */
public class Deployer extends AbstractVerticle {
  private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getCanonicalName());

  @Override
  public void start() throws Exception {
    long delay = 1000;
    for (int i = 0; i < 50; i++) {
      vertx.setTimer(delay, id -> deploy());
      delay = delay + 1000;
    }
  }

  private void deploy() {
    vertx.deployVerticle(new EmptyVertical(), res -> {
      if (res.succeeded()) {
        String id = res.result();
        LOGGER.info("Successfully deploy: {}", id);
        vertx.setTimer(5000, tid -> undeploy(id));
      } else {
        LOGGER.error("Error when deploying:", res.cause());
      }
    });
  }

  private void undeploy(String id) {
    vertx.undeploy(id, res -> {
      if (res.succeeded()) {
        LOGGER.info("Undeploy successfully: {}", id);
      } else {
        LOGGER.info("Undeploy error: {}", id);
      }
    });
  }
}
