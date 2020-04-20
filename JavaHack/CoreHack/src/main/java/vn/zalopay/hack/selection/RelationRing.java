package vn.zalopay.hack.selection;

import com.google.common.collect.ImmutableList;
import lombok.ToString;
import vn.zalopay.hack.selection.entity.Relation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/** Created by thuyenpt Date: 2020-03-26 */
@ToString
public class RelationRing extends Selection implements RelationSelection {
  private ImmutableList<Relation> relations;
  private Map<Long, Integer> relationsIndexing = new HashMap<>();
  private int maxRoundTimes = 3;
  private int writePos = -1;
  private int capacity;
  private int mask;

  public RelationRing(ImmutableList<Relation> relations) {
    this.relations = relations;
    this.capacity = relations.size();
    this.mask = this.capacity - 1;
    indexingRelation();
  }

  private void indexingRelation() {
    for (int i = 0; i < relations.size(); i++) {
      relationsIndexing.put(relations.get(i).getSubAccountId(), i);
    }
  }

  private boolean isDeadlineExceed(long startTime, long timeout) {
    return (System.currentTimeMillis() - startTime) > timeout;
  }

  public synchronized List<Long> getAvailableKeys(int numberAccountNeedLoad) {
    List<Long> availableKeys = new ArrayList<>();
    int loadedKey = 0;
    int roundTimes = 0;
    long startTime = System.currentTimeMillis();
    long timeout = 200;
    while (!isDeadlineExceed(startTime, timeout) && loadedKey < numberAccountNeedLoad) {
      int roundIndex = 0;
      roundTimes++;
      while (roundIndex < this.capacity) {
        roundIndex++;
        writePos = (writePos + 1) & mask;
        Relation relation = relations.get(writePos);
        if (!relation.isLocked()) {
          relation.setLocked(true);
          availableKeys.add(relation.getSubAccountId());
          ++loadedKey;
        }
        if (loadedKey == numberAccountNeedLoad) {
          break;
        }
      }
    }

    if (loadedKey < numberAccountNeedLoad) {
      availableKeys.forEach(this::releaseRelation);
      availableKeys = null;
    }
    return availableKeys;
  }

//    public synchronized List<Long> getAvailableKeys(int needLoadKey) {
//      List<Long> availableKeys = new ArrayList<>();
//      int roundTimes = 0;
//      int loadedKey = 0;
//      while (roundTimes < maxRoundTimes && loadedKey < needLoadKey) {
//        roundTimes++;
//        int roundIndex = 0;
//        while (roundIndex < this.capacity) {
//          roundIndex++;
//          writePos = (writePos + 1) & mask;
//          Relation relation = relations.get(writePos);
//          if (!relation.isLocked()) {
//            relation.setLocked(true);
//            availableKeys.add(relation.getSubAccountId());
//            loadedKey++;
//          }
//          if (loadedKey == needLoadKey) {
//            break;
//          }
//        }
//      }
//
//      if (roundTimes == maxRoundTimes) {
//        availableKeys.forEach(this::releaseRelation);
//        availableKeys = null;
//      }
//
//      return availableKeys;
//    }

  public void releaseRelation(long subAccountId) {
    int index = relationsIndexing.get(subAccountId);
    Relation relation = relations.get(index);
    relation.setLocked(false);
    relation.setLockBy("");
  }

  public void releaseRelations(List<Long> lockedKey) {
    lockedKey.forEach(this::releaseRelation);
  }
}
