package vn.zalopay.hack.guava;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/** Created by thuyenpt Date: 2020-04-03 */
public class Runner {
  @Getter
  @Setter
  @AllArgsConstructor
  static class Student {
    long id;
  }
  public static void main(String[] args) throws InterruptedException, ExecutionException {
    Random random = new Random();
    random.setSeed(System.currentTimeMillis());
    LoadingCache<Integer, Student> accountCache =
        CacheBuilder.newBuilder().build(new CacheLoader<Integer, Student>() {
          @Override
          public Student load(Integer key) throws Exception {
            return new Student(1);
          }
        });


    accountCache.put(1, new Student(1));
    Student st = accountCache.get(1);
    st.setId(2);

    System.out.println(st);
    System.out.println(accountCache.get(1));

    //    System.out.println(accountCache.get(1));
//    TimeUnit.MILLISECONDS.sleep(100);
//    System.out.println(accountCache.get(1));
//    new Thread(
//            () -> {
//              System.out.println("start refresh");
//              accountCache.refresh(1);
//              try {
//                TimeUnit.SECONDS.sleep(2);
//              } catch (InterruptedException e) {
//                e.printStackTrace();
//              }
//              System.out.println("refresh complete");
//
//            })
//        .start();
//    new Thread(() -> {
//      try {
//        System.out.println(accountCache.get(1));
//      } catch (ExecutionException e) {
//        e.printStackTrace();
//      }
//    }).start();
//    TimeUnit.MILLISECONDS.sleep(100);
//    System.out.println(accountCache.get(1));

  }
}