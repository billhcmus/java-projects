package vn.zalopay.hack.synchronize;

/** Created by thuyenpt Date: 2020-03-25 */

class Data {
  public int num;
}

class Change {
  public final Data dataChange = new Data();

  public void increase() {
    for (int i = 0; i < 100000; i++) {
      synchronized (dataChange) {
        dataChange.num++;
      }
    }
  }
}

public class Synchronize {

  public static void main(String[] args) throws InterruptedException {
    Change change = new Change();
    Thread thread1 = new Thread(change::increase);
    thread1.start();

    Thread thread2 = new Thread(change::increase);
    thread2.start();

    thread1.join();
    thread2.join();
    System.out.println(change.dataChange.num);
  }
}
