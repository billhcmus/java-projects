package vn.zalopay.hack.vertx.vertical;

import io.vertx.core.Vertx;

/**
 * Created by thuyenpt Date: 2020-03-23
 */
public class Runner {
  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new Deployer());
  }
}
