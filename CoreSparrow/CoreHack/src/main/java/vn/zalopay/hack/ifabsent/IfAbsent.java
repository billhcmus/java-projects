package vn.zalopay.hack.ifabsent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Created by thuyenpt Date: 2020-02-24 */
public class IfAbsent {
  public static void main(String[] args) {
    Map<Integer, List<Integer>> integerListMap = new HashMap<>();

    integerListMap.putIfAbsent(1, new ArrayList<>());
    integerListMap.computeIfAbsent(2, k -> new ArrayList<>());

    integerListMap.forEach(
        (k, v) -> {
          System.out.println("key: " + k);
          v.forEach(System.out::println);
        });
  }
}
