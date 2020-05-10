package vn.zalopay.hack.selection;

import com.google.common.collect.ImmutableList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import vn.zalopay.hack.selection.entity.Flow;
import vn.zalopay.hack.selection.entity.Relation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Created by thuyenpt Date: 4/25/20 */
public class DoublyRelationRing extends Selection implements RelationSelection {
  private Map<Long, Account> fakeAccounts = new HashMap<>();
  private ImmutableList<Relation> relationsForAllFlow;
  private Map<Long, Integer> relationsIndexing = new HashMap<>();

  private static final int MAX_ROUND_TIMES = 3;
  private int writePos = -1;
  private int capacity;
  private int mask;

  public DoublyRelationRing(ImmutableList<Relation> relations) {
    this.relationsForAllFlow = relations;
    this.capacity = relations.size();
    this.mask = this.capacity - 1;
    initDoublyRelationRing();
  }

  private void initDoublyRelationRing() {
    // Indexing & init data
    for (int i = 0; i < relationsForAllFlow.size(); i++) {
      Relation relation = relationsForAllFlow.get(i);
      long accountId = relation.getSubAccountId();
      relationsIndexing.put(accountId, i);

      Account fakeAccount = Account.builder().id(accountId).balance(i % 2 == 0 ? 0 : 0).build();
      fakeAccounts.put(accountId, fakeAccount);
    }
  }

  public synchronized List<Long> getAvailableKeys(int numberKeyNeedLoad, Flow flow) {
    List<Long> availableKeys = new ArrayList<>();
    int roundTimes = 0;
    int loadedKey = 0;
    boolean needCheckBalance = true;
    while (roundTimes < MAX_ROUND_TIMES && loadedKey < numberKeyNeedLoad) {
      roundTimes++;
      loadedKey =
          tryGetAvailableKeys(flow, availableKeys, numberKeyNeedLoad, loadedKey, needCheckBalance);
      if (needCheckBalance && (roundTimes > MAX_ROUND_TIMES / 2)) {
        needCheckBalance = false;
      }
    } // end while

    if (roundTimes == MAX_ROUND_TIMES && loadedKey < numberKeyNeedLoad) {
      availableKeys.forEach(this::releaseRelation);
      availableKeys = null;
    }

    return availableKeys;
  }

  private int tryGetAvailableKeys(
      Flow flow,
      List<Long> availableKeys,
      int numberKeyNeedLoad,
      int loadedKey,
      boolean needCheckBalance) {
    int startIndex = 0;
    while (startIndex < this.capacity) {
      startIndex++;
      writePos = (writePos + 1) & mask;
      Relation relation = relationsForAllFlow.get(writePos);
      if (!relation.isLocked()) {
        long accountId = relation.getSubAccountId();
        if (skippable(needCheckBalance, flow, accountId)) {
          continue;
        }
        relation.setLocked(true);
        availableKeys.add(accountId);
        loadedKey++;
      }

      if (loadedKey == numberKeyNeedLoad) {
        break;
      }
    } // end while
    return loadedKey;
  }

  private boolean skippable(boolean needCheckBalance, Flow flow, long accountId) {
    if (needCheckBalance) {
      switch (flow) {
        case IN:
          // With IN flow, if account already have balance, we can skip this
          return fakeAccounts.get(accountId).getBalance() != 0;
        case OUT:
          // With OUT flow, if account not have balance, we can skip this
          return fakeAccounts.get(accountId).getBalance() == 0;
        default:
          return false;
      }
    }
    return false;
  }

  public void releaseRelation(long subAccountId) {
    int index = relationsIndexing.get(subAccountId);
    Relation relation = relationsForAllFlow.get(index);
    relation.setLocked(false);
    relation.setLockBy("");
  }

  public void releaseRelations(List<Long> lockedKey) {
    lockedKey.forEach(this::releaseRelation);
  }

  @AllArgsConstructor
  @Getter
  @Setter
  @Builder
  private static class Account {
    private long id;
    private long balance;
  }
}
