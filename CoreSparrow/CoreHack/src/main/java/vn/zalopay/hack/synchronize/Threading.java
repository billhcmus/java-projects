package vn.zalopay.hack.synchronize;

import lombok.Builder;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/** Created by thuyenpt Date: 2020-04-02 */
public class Threading {
  @Builder
  static class HotAccount {
    ReentrantLock lock;
    long balance;
    public void show(String threadName) {
      System.out.println("Thread: " + threadName);
    }
  }

  private static HotAccount hotAccount = HotAccount.builder().lock(new ReentrantLock()).balance(0).build();

  public static void main(String[] args) throws InterruptedException {
    Thread thread1 = new Thread(() -> {
      for (int i = 0; i < 100000; i++) {
        boolean acquireLockSuccess = false;
        try {
          acquireLockSuccess = hotAccount.lock.tryLock(200, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
        if (acquireLockSuccess) {
          hotAccount.balance++;
          hotAccount.lock.unlock();
        }
      }
    });

    Thread thread2 = new Thread(() -> {
      for (int i = 0; i < 100000; i++) {
        hotAccount.balance++;
      }
    });

    thread1.start(); thread2.start();

    thread1.join();
    thread2.join();

    System.out.println("main: " + hotAccount.balance);
  }
}
