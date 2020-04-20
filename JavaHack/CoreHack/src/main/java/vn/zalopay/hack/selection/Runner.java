package vn.zalopay.hack.selection;

import com.google.common.collect.ImmutableList;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import vn.zalopay.hack.selection.entity.Event;
import vn.zalopay.hack.selection.entity.Relation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/** Created by thuyenpt Date: 2020-03-26 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Warmup(iterations = 15)
@Fork(
        value = 4,
        jvmArgs = {"-Xms2G", "-Xmx2G"})
@Measurement(iterations = 60)
public class Runner {
  private static RelationSelection relationSelection;
  static {
    // Init
    List<Relation> relationList = new ArrayList<>();
    for (int i = 0; i < 128; i++) {
      relationList.add(
          Relation.builder().subAccountId(i).isLocked(false).lock(new ReentrantLock()).build());
    }
    ImmutableList<Relation> relations = ImmutableList.copyOf(relationList);
    relationSelection = new RelationRing(relations);
  }

  @Benchmark
  public void benchmark() {
    relationSelection.startAcquire(10);
    relationSelection.startAcquire(3);
    relationSelection.startAcquire(4);
    relationSelection.startAcquire(5);
    relationSelection.startRelease();

    relationSelection.startWorker();
    relationSelection.pushEvent(Event.builder().eventName("stop").build());
  }

  public static void main(String[] args) throws RunnerException {
    Options opt = new OptionsBuilder().include(Runner.class.getSimpleName()).forks(1).build();

    new org.openjdk.jmh.runner.Runner(opt).run();
  }
}
