package vn.zalopay.hack.selection.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by thuyenpt
 * Date: 2020-03-26
 */
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Event {
  private List<Long> listKey;
  @Builder.Default private String eventName = "";
}
