package vn.zalopay.hack.selection;

import com.google.common.collect.ImmutableList;
import lombok.Getter;
import vn.zalopay.hack.selection.entity.Relation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/** Created by thuyenpt Date: 2020-04-20 */
public class RelationQueue extends Selection implements RelationSelection {
  private ImmutableList<Relation> relations;
  private ArrayBlockingQueue<Relation> availableRelations;
  private Map<Long, Integer> relationsIndexing = new HashMap<>();
  @Getter
  private int capacity;

  public RelationQueue(ImmutableList<Relation> relations) {
    this.relations = relations;
    this.capacity = relations.size();
    this.availableRelations = new ArrayBlockingQueue<>(this.capacity);
    this.initRelationBlockingQueue();
    this.indexingRelation();
  }

  private void initRelationBlockingQueue() {
    relations.forEach(relation -> availableRelations.offer(relation));
  }

  private void indexingRelation() {
    for (int i = 0; i < relations.size(); i++) {
      relationsIndexing.put(relations.get(i).getSubAccountId(), i);
    }
  }

  @Override
  public synchronized List<Long> getAvailableKeys(int numberAccountNeedLoad) {
//    List<Relation> resultRelations = new ArrayList<>();
//    if (this.availableRelations.size() > numberAccountNeedLoad) {
//      System.out.println("don't need poll");
//      this.availableRelations.drainTo(resultRelations, numberAccountNeedLoad);
//      return resultRelations.stream()
//          .map(
//              relation -> {
//                relation.setLocked(true);
//                relation.setLockBy("trans_id");
//                return relation.getSubAccountId();
//              })
//          .collect(Collectors.toList());
//    }

    List<Long> loadedKeys = new ArrayList<>();
    int numberKeyLoaded = 0;
    while (numberKeyLoaded < numberAccountNeedLoad) {
      try {
        Relation relation = this.availableRelations.poll(200, TimeUnit.MILLISECONDS);
        if (relation == null) { // timeout
          System.out.println("Timeout");
          break;
        }
        relation.setLocked(true);
        relation.setLockBy("trans_id");
        loadedKeys.add(relation.getSubAccountId());
        numberKeyLoaded++;
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }

    if (numberKeyLoaded < numberAccountNeedLoad) {
      releaseRelations(loadedKeys);
      loadedKeys = null;
    }

    return loadedKeys;
  }

  public boolean releaseRelation(long subAccountId) {
    int index = relationsIndexing.get(subAccountId);
    Relation relation = relations.get(index);
    relation.setLocked(false);
    relation.setLockBy("");
    return availableRelations.offer(relation);
  }


  @Override
  public void releaseRelations(List<Long> lockedKeys) {
    lockedKeys.forEach(this::releaseRelation);
  }
}
