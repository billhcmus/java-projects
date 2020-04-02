package vn.zalopay.hack.selection;

import vn.zalopay.hack.selection.entity.Event;

/** Created by thuyenpt Date: 2020-03-26 */
public interface RelationSelection {
  void startAcquire(int n);

  void startRelease();

  void startWorker();

  void pushEvent(Event event);
}
