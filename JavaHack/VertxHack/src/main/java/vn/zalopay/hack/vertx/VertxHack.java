package vn.zalopay.hack.vertx;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandles;

/** Created by thuyenpt Date: 2020-03-15 */
public class VertxHack {
  private static final Logger LOGGER =
      LogManager.getLogger(MethodHandles.lookup().lookupClass().getCanonicalName());

  public static void main(String[] args) {
    LOGGER.info("Hello world");
  }
}
