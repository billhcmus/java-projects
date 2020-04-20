package vn.zalopay.hack.guava;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/** Created by thuyenpt Date: 2020-04-03 */
public class Runner {
  public static void main(String[] args) throws InterruptedException, ExecutionException {
    Random random = new Random();
    random.setSeed(System.currentTimeMillis());
    LoadingCache<Integer, Integer> accountCache =
        CacheBuilder.newBuilder()
            .build(
                new CacheLoader<Integer, Integer>() {
                  @Override
                  public Integer load(Integer id) throws Exception {
                    return random.nextInt(64);
                  }
                });

    System.out.println(accountCache.get(1));
    TimeUnit.MILLISECONDS.sleep(100);
    System.out.println(accountCache.get(1));
    new Thread(
            () -> {
              System.out.println("start refresh");
              accountCache.refresh(1);
              try {
                TimeUnit.SECONDS.sleep(2);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
              System.out.println("refresh complete");

            })
        .start();
    new Thread(() -> {
      try {
        System.out.println(accountCache.get(1));
      } catch (ExecutionException e) {
        e.printStackTrace();
      }
    }).start();
    TimeUnit.MILLISECONDS.sleep(100);
    System.out.println(accountCache.get(1));

  }
}
