package vn.zalopay.hack.collections;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

    Map<Long, Long> mapVersions =
        changeList.stream()
            .collect(Collectors.toMap(Change::getId, change -> change.getVersion() - 1));

    Map<Long, List<Change>> changeMap = changeList.stream().collect(Collectors.groupingBy(Change::getId));

    System.out.println(mapVersions);
  }
}
