package vn.zalopay.hack.dagger;

import vn.zalopay.hack.dagger.router.CommandRouter;

import java.util.Scanner;

/** Created by thuyenpt Date: 2020-02-22 */
public class Runner {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    CommandRouter commandRouter = new CommandRouter();

    while (scanner.hasNextLine()) {
      commandRouter.route(scanner.nextLine());
    }
  }
}
