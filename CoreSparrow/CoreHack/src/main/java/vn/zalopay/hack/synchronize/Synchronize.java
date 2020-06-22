package vn.zalopay.hack.synchronize;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/** Created by thuyenpt Date: 2020-03-25 */

class Data {
  public int num;
}

class Change {
  public List<Long> longs = new ArrayList<>();
  public final Data dataChange = new Data();

  public synchronized void hold() {
    try {
      System.out.println("Start sleeping");
      TimeUnit.SECONDS.sleep(5);
      System.out.println("Complete sleeping");
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void modify() {
    System.out.println("Size: " + longs.size());
    longs.add(0, 100L);
  }
}

public class Synchronize {

  public static void main(String[] args) throws InterruptedException {
    Change change = new Change();
    change.longs.add(1L);
    change.longs.add(2L);
    change.longs.add(3L);
    Thread thread1 = new Thread(change::hold);
    thread1.start();

    TimeUnit.SECONDS.sleep(1);

    Thread thread2 = new Thread(change::hold);
    thread2.start();

    System.out.println("Main wait");
  }
}
