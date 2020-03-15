package vn.zalopay.hack.hotspot;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.RunnerException;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/** Created by thuyenpt Date: 2020-03-05 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(
    value = 4,
    jvmArgs = {"-Xms2G", "-Xmx2G"})
@Warmup(iterations = 15)
@Measurement(iterations = 60)
public class InOut {
  private static final Map<Long, List<Long>> ACCOUNT_RELATION_MAP = new HashMap<>();
  private static final ArrayList<AccountCache> ACCOUNT_CACHES = new ArrayList<>();
  private static final int numberOfSubAccount = 128;
  private static final int batchLoad = 16;

  static {
    List<Long> subKeys = new ArrayList<>();
    for (int i = 1; i <= numberOfSubAccount; i++) {
      subKeys.add((long) i);
    }

    ACCOUNT_RELATION_MAP.put(69L, subKeys);
    for (int i = 1; i <= numberOfSubAccount; ++i) {
      if (i == 1) {
        ACCOUNT_CACHES.add(AccountCache.builder().id(i).balance(10).build());
      } else {
        ACCOUNT_CACHES.add(AccountCache.builder().id(i).balance(10).build());
      }
    }
  }

  private static List<AccountCache> getSubAccountsForInOutFlow(
      long masterAccId, List<AccountChange> accountChanges) {
    List<Long> listKey = ACCOUNT_RELATION_MAP.get(masterAccId);
    if (listKey.isEmpty()) {
      return new ArrayList<>();
    }
    Collections.shuffle(listKey);

    int currentStartIndex = 0;
    int currentEndIndex = batchLoad;
    List<AccountCache> tempAccountCaches = new ArrayList<>();
    List<AccountCache> resultAccountCaches = new ArrayList<>();

    loadRangeSubAccounts(
        listKey, currentStartIndex, currentEndIndex, tempAccountCaches, resultAccountCaches);

    for (AccountChange accountChange : accountChanges) {
      if (accountChange.isIncr()) {
        handleIncreaseChange(tempAccountCaches, accountChange);
      } else {
        currentEndIndex =
            handleDecreaseChange(
                listKey, currentEndIndex, tempAccountCaches, resultAccountCaches, accountChange);
      }
    }
//    System.out.println("Temp account caches complete");
//    display(tempAccountCaches);
    return resultAccountCaches;
  }

  private static int handleDecreaseChange(
      List<Long> listKey,
      int currentEndIndex,
      List<AccountCache> tempAccountCaches,
      List<AccountCache> resultAccountCaches,
      AccountChange accountChange) {
    long currentBalanceChange = accountChange.getBalanceChange();
    long remainBalanceChange = currentBalanceChange;

    while (remainBalanceChange < 0) {
      tempAccountCaches.sort((a1, a2) -> Long.compare(a2.getBalance(), a1.getBalance()));
//      System.out.println("Collected for OUT");
//      display(tempAccountCaches);
      for (AccountCache accountCache : tempAccountCaches) {
        if (accountCache.getBalance() == 0) {
          break;
        }
        remainBalanceChange = remainBalanceChange + accountCache.getBalance();
        if (remainBalanceChange >= 0) {
          accountCache.setBalance(accountCache.getBalance() + currentBalanceChange);
          break;
        } else {
          currentBalanceChange = remainBalanceChange;
          accountCache.setBalance(0);
        }
      }
      System.out.println("Remain balance change when out of accounts: " + remainBalanceChange);
      if (remainBalanceChange < 0) {
        currentEndIndex =
            getMoreSubAccounts(listKey, currentEndIndex, tempAccountCaches, resultAccountCaches);
      }
    }
    return currentEndIndex;
  }

  private static int getMoreSubAccounts(
      List<Long> listKey,
      int currentEndIndex,
      List<AccountCache> tempAccountCaches,
      List<AccountCache> resultAccountCaches) {
    int currentStartIndex;
    currentStartIndex = currentEndIndex;
    System.out.println("Current end index: " + currentEndIndex);
    if (currentEndIndex >= numberOfSubAccount) {
      display(tempAccountCaches);
      throw new IllegalArgumentException("Not enough money");
    }
    currentEndIndex =
        currentEndIndex > numberOfSubAccount / 2
            ? numberOfSubAccount
            : currentEndIndex + batchLoad; // numberOfSubAccount % batchLoad == 0 must satisfied
    loadRangeSubAccounts(
        listKey, currentStartIndex, currentEndIndex, tempAccountCaches, resultAccountCaches);
    return currentEndIndex;
  }

  private static void handleIncreaseChange(
      List<AccountCache> tempAccountCaches, AccountChange accountChange) {
    AccountCache selectedAccount =
        tempAccountCaches.get(new Random().nextInt(tempAccountCaches.size()));
    selectedAccount.setBalance(selectedAccount.getBalance() + accountChange.getBalanceChange());
  }

  private static void loadRangeSubAccounts(
      List<Long> listKey,
      int currentStartIndex,
      int currentEndIndex,
      List<AccountCache> tempAccountCaches,
      List<AccountCache> resultAccountCaches) {
    List<AccountCache> batchAccountCaches =
        listKey
            .subList(currentStartIndex, currentEndIndex)
            .parallelStream()
            .map(InOut::getAccount)
            .collect(Collectors.toList());
    appendAccountCacheList(resultAccountCaches, batchAccountCaches);
    tempAccountCaches.addAll(batchAccountCaches);
  }

  private static void appendAccountCacheList(
      List<AccountCache> currentList, List<AccountCache> chunk) {
    currentList.addAll(chunk.stream().map(AccountCache::new).collect(Collectors.toList()));
  }

  private static AccountCache getAccount(long id) {
    try {
      TimeUnit.MILLISECONDS.sleep(1);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return ACCOUNT_CACHES.stream()
        .filter(accountCache -> accountCache.getId() == id)
        .findAny()
        .orElse(null);
  }

  private static List<AccountChange> accountChanges = createAccountChanges(0, 4);

  @Benchmark
  public void benchmark() {
    getSubAccountsForInOutFlow(69L, accountChanges);
  }

  public static void main(String[] args) throws RunnerException {
    List<AccountCache> selectedAccountCaches = getSubAccountsForInOutFlow(69L, accountChanges);
    display(selectedAccountCaches);
    //    Options opt = new OptionsBuilder().include(InOut.class.getSimpleName()).forks(1).build();
    //
    //    new Runner(opt).run();
  }

  private static void display(List<AccountCache> accountCaches) {
    accountCaches.forEach(AccountCache::display);
    long remainBalance = accountCaches.stream().map(AccountCache::getBalance).reduce(0L, Long::sum);
    System.out.println("Balance: " + remainBalance);
  }

  @AllArgsConstructor
  @Setter
  @Getter
  @Builder
  static class AccountCache {
    long id;
    long balance;

    public AccountCache(AccountCache other) {
      this.id = other.id;
      this.balance = other.balance;
    }

    void display() {
      System.out.println("ID: " + id + " Balance: " + balance);
    }
  }

  @AllArgsConstructor
  @Setter
  @Getter
  @Builder
  static class AccountChange {
    boolean isIncr;
    long balanceChange;
  }

  // type: 0: OUT, 1: IN-OUT
  private static List<AccountChange> createAccountChanges(int type, int numChange) {
    List<AccountChange> accountChanges = new ArrayList<>();
    switch (type) {
      case 0:
        for (int i = 0; i < numChange; ++i) {
          accountChanges.add(AccountChange.builder().isIncr(false).balanceChange(-2000).build());
        }
        break;
      case 1:
        for (int i = 0; i < numChange; ++i) {
          if (i % 2 == 0) {
            accountChanges.add(AccountChange.builder().isIncr(true).balanceChange(250).build());
          } else {
            accountChanges.add(AccountChange.builder().isIncr(false).balanceChange(-600).build());
          }
        }
        break;
      default:
        AccountChange accountChange1 =
            AccountChange.builder().isIncr(false).balanceChange(-5).build();
        AccountChange accountChange2 =
            AccountChange.builder().isIncr(false).balanceChange(-5).build();
        AccountChange accountChange3 =
            AccountChange.builder().isIncr(false).balanceChange(-0).build();
        AccountChange accountChange4 =
            AccountChange.builder().isIncr(false).balanceChange(-0).build();
        accountChanges.add(accountChange1);
        accountChanges.add(accountChange2);
        accountChanges.add(accountChange3);
        accountChanges.add(accountChange4);
    }
    return accountChanges;
  }
}
