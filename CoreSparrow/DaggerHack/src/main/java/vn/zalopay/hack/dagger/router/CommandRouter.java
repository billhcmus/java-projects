package vn.zalopay.hack.dagger.router;

import org.apache.commons.lang3.StringUtils;
import vn.zalopay.hack.dagger.command.Command;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/** Created by thuyenpt Date: 2020-02-22 */
public class CommandRouter {
  private final Map<String, Command> commands = Collections.emptyMap();

  public Command.Status route(String input) {
    List<String> inputList = Arrays.asList(StringUtils.split(input, " "));

    if (inputList.isEmpty()) {
      return invalidCommand(input);
    }

    Command command = commands.get(inputList.get(0));
    if (command == null) {
      return invalidCommand(input);
    }

    Command.Status status = command.handleInput(inputList.subList(1, inputList.size()));
    if (status == Command.Status.INVALID) {
      System.out.println(inputList.get(0) + ": invalid arguments");
    }
    return status;
  }

  private Command.Status invalidCommand(String input) {
    System.out.println(String.format("couldn't understand \"%s\". please try again.", input));
    return Command.Status.INVALID;
  }
}
