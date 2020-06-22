package vn.zalopay.hack.queue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by thuyenpt Date: 2020-04-20
 */
public class BlockingQueue {
  public static void main(String[] args) {
    ArrayBlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(10);

    blockingQueue.add("a");
    blockingQueue.add("b");
    blockingQueue.add("c");
    blockingQueue.add("d");

    System.out.println("Before drain: " + blockingQueue);
    System.out.println("Size: " + blockingQueue.size());

//    List<String> stringList = new ArrayList<>();
//    int num = blockingQueue.drainTo(stringList, 6);
//    System.out.println("Number element drained: " + num);
//
//    System.out.println("After drained: " + blockingQueue);
//
//    System.out.println("Result collection: " + stringList);

    String value = null;
    try {
      value = blockingQueue.poll(2, TimeUnit.SECONDS);
      if (value == null) {
        System.out.println("Timeout");
        System.out.println("Current queue: " + blockingQueue);
      }
    } catch (InterruptedException e) {
      System.out.println("Interrupt");
    }
    System.out.println("Value: " + value);
    System.out.println("Queue: " + blockingQueue);
  }
}
