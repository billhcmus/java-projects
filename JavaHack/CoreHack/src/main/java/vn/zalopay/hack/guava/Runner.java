package vn.zalopay.hack.guava;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/** Created by thuyenpt Date: 2020-04-03 */
public class Runner {
  @Getter
  @Setter
  @AllArgsConstructor
  @ToString
  static class Student {
    long id;
  }

  public static void main(String[] args) throws InterruptedException, ExecutionException {
    Random random = new Random();
    random.setSeed(System.currentTimeMillis());
    LoadingCache<Integer, Student> accountCache =
        CacheBuilder.newBuilder()
            .build(
                new CacheLoader<Integer, Student>() {
                  private ListeningExecutorService executorService =
                      MoreExecutors.listeningDecorator(Executors.newSingleThreadExecutor());

                  @Override
                  public Student load(Integer key) {
                    return new Student(1);
                  }

                  @Override
                  public ListenableFuture<Student> reload(Integer key, Student oldValue) {
                    return executorService.submit(
                        () -> {
                          TimeUnit.SECONDS.sleep(5);
                          return load(key);
                        });
                  }
                });

    System.out.println("1: " + accountCache.get(1).toString());
    TimeUnit.MILLISECONDS.sleep(100);
    System.out.println("2: " + accountCache.get(1).toString());
    new Thread(
            () -> {
              System.out.println("start refresh");
              accountCache.refresh(1);
              System.out.println("refresh complete");
            })
        .start();
    new Thread(
            () -> {
              try {
                System.out.println("3: " + accountCache.get(1).toString());
              } catch (ExecutionException e) {
                e.printStackTrace();
              }
            })
        .start();
    for (int i = 0; i < 100; ++i) {
      System.out.println("L" + i + ":" + accountCache.get(1).toString());
      System.out.println("L" + i + ":" + accountCache.get(1).toString());
    }

    TimeUnit.MILLISECONDS.sleep(2000);
    System.out.println("finalize: " + accountCache.get(1).toString());
  }
}
