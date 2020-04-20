package vn.zalopay.hack;

/** Hello world! */
public class CoreHack {
  static long numAccountNeedLoadByAccountChange = 72;

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
    if (finalNumAccountWillLoad > numberOfAvailableSubAccounts) {
      long anchor = numberOfSubAccounts / 4;
      finalNumAccountWillLoad = Math.max(anchor, numberOfAvailableSubAccounts);
    }

    return Math.toIntExact(finalNumAccountWillLoad);
  }

  public static void main(String[] args) {
    int n = calculateNumberAccountNeedLoad(128, 120);
    System.out.println(n);
    n = calculateNumberAccountNeedLoadV2(128, 120);
    System.out.println("v2: " + n);
  }
}
