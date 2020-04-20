package vn.zalopay.hack.future;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/** Created by thuyenpt Date: 2020-04-20 */
public class JavaFuture {
  private static List<Long> doSomething() {
    List<Long> longs = new ArrayList<>();
    try {
      TimeUnit.SECONDS.sleep(2);
      longs.add(1L);
      longs.add(2L);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new RuntimeException("Interrupted", e);
    }
    return longs;
  }

  private static List<Long> getAvailableKeys() {
    CompletableFuture<List<Long>> future = CompletableFuture.supplyAsync(JavaFuture::doSomething);
    List<Long> longs = null;
    try {
      longs = future.get(1, TimeUnit.SECONDS);
    } catch (ExecutionException e) {
      System.out.println("Operation failed");
    } catch (TimeoutException e) {
      future.cancel(true);
      System.out.println("Timeout");
    } catch (InterruptedException e) {
      future.cancel(true);
      Thread.currentThread().interrupt();
      System.out.println("Interrupt");
    }
    return longs;
  }

  public static void main(String[] args) {
    List<Long> longList = getAvailableKeys();
    if (longList == null) {
      System.out.println("list is null");
    } else {
      System.out.println(longList.size());
    }
  }
}
