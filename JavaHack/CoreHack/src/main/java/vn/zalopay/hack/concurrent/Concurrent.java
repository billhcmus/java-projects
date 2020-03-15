package vn.zalopay.hack.concurrent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/** Created by thuyenpt Date: 2020-03-03 */
public class Concurrent {
  private static Map<Long, List<Relation>> relationMap = new ConcurrentHashMap<>();

  static {
    List<Relation> myList = new ArrayList<>();
    myList.add(new Relation(69, new AtomicBoolean(false)));
    myList.add(new Relation(70, new AtomicBoolean(false)));
    myList.add(new Relation(71, new AtomicBoolean(false)));
    myList.add(new Relation(72, new AtomicBoolean(false)));
    myList.add(new Relation(73, new AtomicBoolean(false)));
    myList.add(new Relation(74, new AtomicBoolean(false)));
    relationMap.put(69L, myList);
  }

  public static void main(String[] args) {
    List<Relation> relations = relationMap.get(69L);

    Iterator<Relation> relationIterator = relations.iterator();

    while (relationIterator.hasNext()) {
      Relation relation = relationIterator.next();
      relation.setNum(relation.getNum() * -1);
    }

    relations.sort((r1, r2) -> Long.compare(r2.getNum(), r1.getNum()));
    display();
  }

  private static void lock(int num) {
    List<Relation> relations = relationMap.get(69L);
    for (int i = 0; i < num; ++i) {
      if (relations.get(i).isLocked.compareAndSet(false, true)) {
        System.out.println("Locked " + i);
      } else {
        System.out.println("Lock " + i + " failed");
      }
    }
  }

  private static void display() {
    relationMap.values().forEach(list -> list.forEach(Relation::display));
  }

  @Getter
  @Setter
  @AllArgsConstructor
  static class Relation {
    int num;
    AtomicBoolean isLocked;

    void display() {
      System.out.println("Num: " + num + ", isLocked: " + isLocked);
    }
  }
}
