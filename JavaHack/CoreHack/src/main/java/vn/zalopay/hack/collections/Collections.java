package vn.zalopay.hack.collections;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by thuyenpt
 * Date: 2020-03-27
 */

@Getter
@Setter
@Builder
class Change {
  private long id;
  private long balanceChange;
}

public class Collections {
  public static void main(String[] args) {
    List<Change> changeList = new ArrayList<>();
    changeList.add(Change.builder().id(1).build());
    changeList.add(Change.builder().id(1).build());
    changeList.add(Change.builder().id(1).build());
    changeList.add(Change.builder().id(1).build());
    changeList.add(Change.builder().id(1).build());

    Set<Long> uniqueIds = changeList.stream().map(Change::getId).collect(Collectors.toSet());

    uniqueIds.forEach(System.out::println);
  }
}
