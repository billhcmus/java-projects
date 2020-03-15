package vn.zalopay.hack.dagger.command;

import java.util.List;

/** Created by thuyenpt Date: 2020-02-22 */
public interface Command {
  String key();

  Status handleInput(List<String> input);

  enum Status {
    INVALID,
    HANDLED
  }
}
