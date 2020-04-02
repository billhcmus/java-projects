package vn.zalopay.hack.selection;

import com.google.common.collect.ImmutableList;
import vn.zalopay.hack.selection.entity.Relation;

import java.util.*;

/** Created by thuyenpt Date: 2020-03-26 */
public class RelationSpin extends Selection implements RelationSelection {
  private ImmutableList<Relation> relations;

  public RelationSpin(ImmutableList<Relation> relations) {
    this.relations = relations;
  }
  @Override
  public List<Long> getAvailableKeys(int n) {
    Map<Long, Long> mapAvailableKeys = new HashMap<>();
    getAvailableKeys(mapAvailableKeys, relations, n, 0);
    return new ArrayList<>(mapAvailableKeys.values());
  }

  @Override
  public void releaseRelations(List<Long> lockedKey) {
    lockedKey.forEach(this::releaseLock);
  }

  public synchronized void getAvailableKeys(
      Map<Long, Long> mapAvailableKeys,
      List<Relation> relations,
      long numberKeyNeedLoad,
      int counter) {
    for (int i = 0; i < relations.size(); ++i) {
      Relation relation = relations.get(i);
      if (mapAvailableKeys.containsKey(relation.getSubAccountId())) {
        continue;
      }

      if (counter == numberKeyNeedLoad) {
        break;
      }
      relation.getLock().lock();
      if (relation.isLocked()) {
        relation.getLock().unlock();
        continue;
      }
      relation.setLocked(true);
      mapAvailableKeys.put(relation.getSubAccountId(), relation.getSubAccountId());
      counter++;

      relation.getLock().unlock();
    }
    if (counter < numberKeyNeedLoad) {
      getAvailableKeys(mapAvailableKeys, relations, numberKeyNeedLoad, counter);
    }
  }

  public void releaseLock(long subAccountId) {
    Relation relation =
        relations.stream().filter(r -> r.getSubAccountId() == subAccountId).findAny().orElse(null);

    if (relation == null) {
      System.out.println("No sub account found, id=" + subAccountId);
      return;
    }
    relation.getLock().lock();
    if (relation.isLocked()) {

      relation.setLocked(false);
      relation.setLockBy("");
    } else {
      System.out.println("SOMEONE HAS BEEN RELEASE THE LOCK");
    }
    relation.getLock().unlock();
  }
}
