package vn.zalopay.hack.vertx.eventloop;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.TimeUnit;

/** Created by thuyenpt Date: 2020-03-18 */
public class EventLoopRunner {
  private static final Logger LOGGER =
      LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getCanonicalName());

  public static void main(String[] args) {
    EventLoop eventLoop = new EventLoop();

    eventLoop.on("tick", event -> LOGGER.info("Tick {}", event));
    eventLoop.on("tack", event -> LOGGER.info("tack {}", event));
    eventLoop.on(
        "stop",
        event -> {
          LOGGER.info("Event loop stopping.....");
          eventLoop.stop();
        });
    new Thread(
            () -> {
              try {
                for (int i = 0; i < 10; ++i) {
                  TimeUnit.MILLISECONDS.sleep(500);
                  eventLoop.dispatch(new Event("tick", i));
                }
                TimeUnit.MILLISECONDS.sleep(1500);
                eventLoop.dispatch(new Event("stop", null));
              } catch (InterruptedException e) {
                LOGGER.warn(e.getMessage());
                Thread.currentThread().interrupt();
              }
            })
        .start();

    new Thread(
            () -> {
              try {
                for (int i = 0; i < 10; ++i) {
                  TimeUnit.MILLISECONDS.sleep(1000);
                  Event event = new Event("tack", "world.");
                  eventLoop.dispatch(event);
                }
              } catch (InterruptedException e) {
                LOGGER.warn(e.getMessage());
                Thread.currentThread().interrupt();
              }
            })
        .start();

    eventLoop.run();
  }
}
