package vn.zalopay.hack;

import java.util.concurrent.atomic.AtomicInteger;

/** Hello world! */
public class CoreHack {
  static long numAccountNeedLoadByAccountChange = 32;

  private static int calculateNumberAccountNeedLoad(
      int numberOfSubAccounts, int numberOfAvailableSubAccounts) {
    long finalNumAccountWillLoad;
    finalNumAccountWillLoad = Math.min(numAccountNeedLoadByAccountChange, numberOfSubAccounts);
    if (finalNumAccountWillLoad > numberOfAvailableSubAccounts) {
      long anchor = numberOfSubAccounts / 4;
      if (numberOfAvailableSubAccounts > anchor) {
        finalNumAccountWillLoad = numberOfAvailableSubAccounts;
      } else {
        finalNumAccountWillLoad = anchor;
      }
    }

    return Math.toIntExact(finalNumAccountWillLoad);
  }

  private static int calculateNumberAccountNeedLoadV2(
      int numberOfSubAccounts, int numberOfAvailableSubAccounts) {

    long finalNumAccountWillLoad = Math.min(numAccountNeedLoadByAccountChange, numberOfSubAccounts);
    if (finalNumAccountWillLoad >= numberOfAvailableSubAccounts) {
      if (numberOfAvailableSubAccounts == numberOfSubAccounts) {
        finalNumAccountWillLoad = numberOfSubAccounts / 2;
      } else {
        long anchor = numberOfSubAccounts / 4;
        finalNumAccountWillLoad = Math.max(anchor, numberOfAvailableSubAccounts);
      }
    }

    return Math.toIntExact(finalNumAccountWillLoad);
  }

  public static void main(String[] args) {
//    int n = calculateNumberAccountNeedLoad(128, 128);
//    System.out.println(n);
//    int n = calculateNumberAccountNeedLoadV2(128, 96);
//    System.out.println("v2: " + n);

    int capacity = 128;
    AtomicInteger cursor = new AtomicInteger(126);
    int nextPos = cursor.updateAndGet((pos) -> pos == capacity ? pos = 0 : pos++);
    System.out.println(nextPos);
    System.out.println(cursor.get());
  }
}
