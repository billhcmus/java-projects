package vn.zalopay.hack.guava;

import com.google.common.collect.Comparators;

import java.util.ArrayList;
import java.util.List;

/** Created by thuyenpt Date: 2020-04-03 */
public class Runner {
  public static void main(String[] args) {
    List<Integer> integers = new ArrayList<>();
    integers.add(10);
    integers.add(5);
    integers.add(15);
    integers.add(9);
    integers.add(27);

    List<Integer> greatestList =
        integers.stream().collect(Comparators.greatest(2, Integer::compareTo));
    greatestList.forEach(System.out::println);

  }
}
