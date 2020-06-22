package vn.zalopay.hack.bitwise;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by thuyenpt
 * Date: 5/4/20
 */
public class Runner {
  private static AtomicInteger cursor = new AtomicInteger(126);
  private static int getNextPos() {
    int currentPos;
    int nextPos;
    do {
      currentPos = cursor.get();
      nextPos = currentPos < 127 ? currentPos + 1 : 0;
    } while (!cursor.compareAndSet(currentPos, nextPos));
    return nextPos;
  }
  public static void main(String[] args) {
    System.out.println(getNextPos());
    System.out.println(getNextPos());
  }
}
