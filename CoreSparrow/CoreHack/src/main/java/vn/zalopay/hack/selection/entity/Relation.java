package vn.zalopay.hack.selection.entity;

import lombok.*;

import java.util.concurrent.locks.ReentrantLock;

/** Created by thuyenpt Date: 2020-03-26 */
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Relation {
  private long subAccountId;
  private ReentrantLock lock;
  private boolean isLocked;
  private String lockBy;
}
