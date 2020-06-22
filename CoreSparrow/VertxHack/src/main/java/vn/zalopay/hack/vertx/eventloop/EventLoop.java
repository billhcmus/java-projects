package vn.zalopay.hack.vertx.eventloop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.function.Consumer;

/** Created by thuyenpt Date: 2020-03-18 */
public class EventLoop {
  private static final Logger LOGGER =
      LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getCanonicalName());
  private static final ConcurrentLinkedDeque<Event> events = new ConcurrentLinkedDeque<>();
  private final ConcurrentHashMap<String, Consumer<Event>> handlers = new ConcurrentHashMap<>();

  public EventLoop on(String key, Consumer<Event> handler) {
    handlers.put(key, handler);
    return this;
  }

  public void dispatch(Event event) {
    events.add(event);
  }

  public void stop() {
    Thread.currentThread().interrupt();
  }

  public void run() {
    while (!(events.isEmpty() && Thread.interrupted())) {
      if (events.isEmpty()) {
        continue;
      }
      Event event = events.pop();
      if (handlers.containsKey(event.getKey())) {
        handlers.get(event.getKey()).accept(event);
      } else {
        LOGGER.info("Not handle event: {}", event);
      }
    }
  }
}
