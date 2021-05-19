package vn.zalopay.hack.lua.redis;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

/** Created by thuyenpt Date: 2020-04-05 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Command {
  private String phase;
  @Builder.Default private String sha1 = "";
  @Builder.Default private List<Object> keys = new ArrayList<>();
  @Builder.Default private Object[] args = {};
}
