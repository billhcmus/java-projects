package vn.zalopay.hack.hotspot;

import lombok.*;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/** Created by thuyenpt Date: 2020-02-28 */
public class Hotspot {
  private static final long LIMIT = 100;
  private static List<Account> accounts = new ArrayList<>();
  private static Map<Integer, List<Relation>> relations = new HashMap<>();
  static {
    int num = 8;
    List<Relation> subs = new ArrayList<>();
    for (int i = 0; i < num; ++i) {
      accounts.add(new Account(i, 10000));
      subs.add(new Relation(i, new ReentrantLock()));
    }
    relations.put(9, subs);
  }

  private static void In(long amount) {
    List<Relation> subs = relations.get(9);
    long num = amount % LIMIT == 0 ? (amount / LIMIT) : (amount / LIMIT) + 1;
    if (num > LIMIT) {
      num = LIMIT;
    }
    //    subs.stream().forEach(relation -> );

  }

  public static void main(String[] args) throws InterruptedException {
    List<Relation> subs = relations.get(9);
    subs.forEach(relation -> System.out.println(relation.toString()));

    new Thread(
            () -> {
              System.out.println("Thread 1: try to get lock of item 0");
              if (subs.get(0).lock.isLocked()) {
                System.out.println("Thread 1: item 0 is locked");
              } else {
                AtomicInteger counter = new AtomicInteger(0);
                subs.forEach(relation -> {
                  if (!relation.lock.isLocked()) {
                    relation.lock.lock();

                  }
                });
                subs.get(0).lock.lock();
                System.out.println("Thread 1: lock success");
                try {
                  TimeUnit.SECONDS.sleep(5);
                  subs.get(0).lock.unlock();
                } catch (InterruptedException e) {
                  e.printStackTrace();
                }
              }
            })
        .start();

    TimeUnit.SECONDS.sleep(1);

    new Thread(
            () -> {
              while (true) {
                System.out.println("Thread 2: try to get lock of item 0");
                try {
                  if (subs.get(0).lock.tryLock(1, TimeUnit.SECONDS)) break;
                } catch (InterruptedException e) {
                  e.printStackTrace();
                }
              }
              System.out.println("Thread 2: lock success");
            })
        .start();

    TimeUnit.SECONDS.sleep(10);
  }

  @Getter
  @Setter
  @AllArgsConstructor
  @ToString
  static class Account {
    long id;
    long balance;
  }

  @AllArgsConstructor
  @ToString
  @Data
  static class Relation {
    long id;
    ReentrantLock lock;
  }
}
