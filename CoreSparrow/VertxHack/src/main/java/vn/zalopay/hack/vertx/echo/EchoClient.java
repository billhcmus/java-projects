package vn.zalopay.hack.vertx.echo;

import io.vertx.core.Vertx;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.atomic.AtomicInteger;

/** Created by thuyenpt Date: 2020-03-15 */
public class EchoClient {
  private static final Logger LOGGER =
      LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getCanonicalName());

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    NetClient netClient = vertx.createNetClient();
    netClient.connect(3000, "localhost", res -> {
      if (res.succeeded()) {
        NetSocket netSocket = res.result();
        AtomicInteger i = new AtomicInteger(0);
        netSocket.write("Client: " + i);
        netSocket.handler(event -> {
          LOGGER.info("Event: {}", event);
          i.incrementAndGet();
          netSocket.write("Client: " + i);
          if (i.incrementAndGet() == 30) {
            netSocket.write("/quit\n");
            netSocket.close();
            netClient.close();
          }
        });
        netSocket.closeHandler(event -> {
          LOGGER.info("netSocket closed");
          vertx.close();
        });
      } else {
        LOGGER.error(res.cause().getCause().getMessage());
        netClient.close();
      }
    });
  }
}
