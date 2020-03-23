package vn.zalopay.hack.vertx.eventloop;

import lombok.Getter;
import lombok.ToString;

/**
 * Created by thuyenpt Date: 2020-03-18
 */

@Getter
@ToString
public class Event {
  private final String key;
  private final Object data;

  public Event(String key, Object data) {
    this.key = key;
    this.data = data;
  }
}
