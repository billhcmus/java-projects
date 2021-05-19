package vn.zalopay.hack.lua;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import vn.zalopay.hack.lua.redis.Command;
import vn.zalopay.hack.lua.redis.RedisCache;
import vn.zalopay.hack.lua.redis.RedissonCacheImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by thuyenpt Date: 22/04/2021
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Warmup(iterations = 15)
@Fork(
    value = 4,
    jvmArgs = {"-Xms2G", "-Xmx2G"})
@Measurement(iterations = 60)
public class Runner {
  private static final RedisCache redisCache;
  private static final List<Command> commands = new ArrayList<>();

  static {
    Config config = new Config();
    SingleServerConfig singleServerConfig = config.useSingleServer();
    singleServerConfig.setAddress("redis://127.0.0.1:6379");
    config.setThreads(4);
    config.setNettyThreads(4);
    config.setCodec(new StringCodec());
    RedissonClient redissonClient = Redisson.create(config);

    redisCache = new RedissonCacheImpl(redissonClient);

    List<Object> keys = new ArrayList<>();
    String masterKey = "zas:master-account:{18437381944378630}";
    String subKey = "zas:sub-account:{18437381944378630}:";
    String transLogKey = "zas:trans-logs:{18437381944378630}";
    keys.add(masterKey);
    keys.add(subKey);
    keys.add(transLogKey);

    List<Object> arguments = new ArrayList<>();
    arguments.add("thuyenpt-test-1");
    arguments.add("-1");
    arguments.add("5555");

    Command command =
        Command.builder()
            .sha1("576be3f86220bd5f79f1e9d3d806367b2b311f3d")
            .keys(keys)
            .args(arguments.toArray())
            .build();

    commands.add(command);
  }


  @Benchmark
  public void benchmark() {
    redisCache.executeSyncCommand(commands.get(0));
  }

  public static void main(String[] args) throws RunnerException {
    Options opt = new OptionsBuilder().include(vn.zalopay.hack.lua.Runner.class.getSimpleName()).forks(2).build();
    new org.openjdk.jmh.runner.Runner(opt).run();
  }
}
