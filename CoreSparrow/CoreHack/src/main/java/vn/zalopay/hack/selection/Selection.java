package vn.zalopay.hack.selection;

import vn.zalopay.hack.selection.entity.Event;
import vn.zalopay.hack.selection.entity.Flow;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/** Created by thuyenpt Date: 2020-03-26 */
public abstract class Selection {
  private final ConcurrentLinkedQueue<Event> resultQueue = new ConcurrentLinkedQueue<>();
  private List<Thread> acquireThread = new ArrayList<>();
  private List<Thread> releaseThread = new ArrayList<>();
  private AtomicInteger errorCounter = new AtomicInteger(0);

  public abstract List<Long> getAvailableKeys(int n, Flow flow);

  public abstract void releaseRelations(List<Long> lockedKey);

  public void startAcquire(int n) {
    Thread thread =
        new Thread(
            () -> {
              for (int i = 0; i < 12800; i++) {
                List<Long> result = getAvailableKeys(n, Flow.IN);
                if (result == null) {
                  i--;
                } else {
                  resultQueue.add(Event.builder().listKey(result).build());
                }
              }
            });

    acquireThread.add(thread);
    thread.start();
  }

  public void startRelease() {
    Thread thread =
        new Thread(
            () -> {
              while (!(resultQueue.isEmpty() && Thread.interrupted())) {
                Event event = resultQueue.poll();
                if (event == null) {
                  continue;
                }
                if (event.getEventName().equals("stop")) {
                  break;
                }
                //                System.out.println("Release");
                releaseRelations(event.getListKey());
                //                System.out.println("----end-release----size: " +
                // event.getListKey().size());
              }
            });
    thread.start();
    releaseThread.add(thread);
  }

  public void startWorker() {
    for (Thread thread : acquireThread) {
      try {
        thread.join();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public void pushEvent(Event event) {
    resultQueue.add(event);
  }
}
