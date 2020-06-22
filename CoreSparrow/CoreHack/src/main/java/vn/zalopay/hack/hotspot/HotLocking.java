package vn.zalopay.hack.hotspot;

import lombok.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/** Created by thuyenpt Date: 2020-03-12 */
public class HotLocking {
  private static final Map<Long, HotAccountLock> HOT_ACCOUNT_LOCK_MAP = new ConcurrentHashMap<>();

  public static void main(String[] args) {}

  private void releaseLockForMasterAccount(long masterAccountId) {
    HotAccountLock hotAccountLock = HOT_ACCOUNT_LOCK_MAP.get(masterAccountId);
    if (!hotAccountLock.getIsLocked().compareAndSet(true, false)) {
      System.out.println("LOSS LOCK OF AN MASTER ACCOUNT");
    }
  }

  private void acquireLockForMasterAccount(long masterAccountId) {
    HotAccountLock hotAccountLock =
        HOT_ACCOUNT_LOCK_MAP.computeIfAbsent(
            masterAccountId, hotId -> new HotAccountLock(hotId, new AtomicBoolean(false)));

    while (!hotAccountLock.getIsLocked().compareAndSet(false, true)) {
      System.out.println("WAIT LOCK: {}" + masterAccountId);
    }
  }

  @AllArgsConstructor
  @NoArgsConstructor
  @Getter
  @Setter
  @Builder
  private static class HotAccountLock {
    private long masterAccountId;
    private AtomicBoolean isLocked;
  }
}
