package vn.zalopay.hack.synchronize;

/** Created by thuyenpt Date: 2020-03-25 */
class Change {
  public volatile int num = 0;

  public synchronized void increase(int i) {
    if (i == 100000) {
      return;
    }
    num++;
    increase(++i);
  }
}

public class Synchronize {

  public static void main(String[] args) throws InterruptedException {
    Change change = new Change();
    Thread thread1 = new Thread(() -> change.increase(0));
    thread1.start();

    Thread thread2 = new Thread(() -> change.increase(0));
    thread2.start();

    thread1.join();
    thread2.join();

    System.out.println(change.num);
  }
}
