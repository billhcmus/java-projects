package vn.zalopay.hack.collections;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;

/** Created by thuyenpt Date: 2020-03-27 */
@Getter
@Setter
@Builder
class Change {
  private long id;
  private long balanceChange;
  private long version;
}

public class Collections {
  public static void main(String[] args) {
    List<Change> changeList = new ArrayList<>();
    changeList.add(Change.builder().id(1).version(1).build());
    changeList.add(Change.builder().id(2).version(2).build());
    changeList.add(Change.builder().id(3).version(3).build());
    changeList.add(Change.builder().id(4).version(4).build());
    changeList.add(Change.builder().id(4).version(5).build());

    //    Set<Long> uniqueIds = changeList.stream().map(Change::getId).collect(Collectors.toSet());
    //    uniqueIds.forEach(System.out::println);

//    Map<Long, Long> mapVersions =
//        changeList.stream()
//            .collect(Collectors.toMap(Change::getId, change -> change.getVersion() - 1));
//
//    Map<Long, List<Change>> changeMap =
//        changeList.stream().collect(Collectors.groupingBy(Change::getId));

    LinkedHashMap<Long, String> changeLinkedHashMap =
        new LinkedHashMap<Long, String>(0, 1F, false) {
          @Override
          protected boolean removeEldestEntry(Map.Entry<Long, String> eldest) {
            return size() > 5;
          }
        };

    changeLinkedHashMap.put(1L, "a");
    changeLinkedHashMap.put(2L, "b");
    changeLinkedHashMap.put(3L, "c");
    changeLinkedHashMap.put(4L, "d");
    changeLinkedHashMap.put(5L, "e");

    changeLinkedHashMap.put(6L, "e");
    changeLinkedHashMap.put(7L, "e");
    changeLinkedHashMap.put(8L, "e");


    System.out.println(changeLinkedHashMap);
  }
}
