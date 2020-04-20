package vn.zalopay.hack.synchronize;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by thuyenpt Date: 2020-04-02
 */
public class Threading {
  static AtomicInteger num = new AtomicInteger(0);
  static class MyThread extends java.lang.Thread {
    @Override
    public void run() {
      for (int i = 0; i < 100; i++) {
        System.out.println(getName() + ":" + num.incrementAndGet());
        yield();
      }
    }
  }
  public static void main(String[] args) throws InterruptedException {
    MyThread thread = new MyThread();
    thread.start();
    System.out.println("Wait thread complete execute");
    for (int i = 0; i < 100; i++) {
      System.out.println(Thread.currentThread().getName() + ":" + num.incrementAndGet());
    }
  }
}
