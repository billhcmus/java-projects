package vn.zalopay.hack.vertx.echo;

import io.vertx.core.Vertx;
import io.vertx.core.net.NetSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/** Created by thuyenpt Date: 2020-03-15 */
public class VertxEcho {
  //-Dlog4j.configurationFile=./conf/log4j2.xml
  private static final Logger LOGGER =
      LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getCanonicalName());
  private static int numberOfConnection = 0;

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();

    vertx.createNetServer().connectHandler(VertxEcho::handleNewClient).listen(3000);
    vertx.setPeriodic(3000, h -> LOGGER.info(howMany()));
    vertx
        .createHttpServer()
        .requestHandler(httpServerRequest -> httpServerRequest.response().end(howMany()))
        .listen(8080);
  }

  private static void handleNewClient(NetSocket netSocket) {
    numberOfConnection++;
    LOGGER.info("Connection id: {}", numberOfConnection);
    netSocket.handler(
        buffer -> {
          LOGGER.info("Get buffer from client: {}", buffer);
          netSocket.write("Server: ACK");
          if (buffer.toString().endsWith("/quit\n")) {
            netSocket.close();
          }
        });

    netSocket.closeHandler(v -> numberOfConnection--);
  }

  private static String howMany() {
    return "We have " + numberOfConnection + " connection";
  }
}
