package vn.zalopay.hack.lua.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.ScriptOutputType;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.codec.StringCodec;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DefaultClientResources;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thuyenpt Date: 23/04/2021
 */
public class LettuceCache {
  public static void main(String[] args) {
    int cores = Runtime.getRuntime().availableProcessors();
    ClientResources clientResources =
        DefaultClientResources.builder()
            .ioThreadPoolSize(cores)
            .computationThreadPoolSize(cores)
            .build();
    RedisURI redisURI =
        RedisURI.builder()
            .withHost("localhost")
            .withPort(6379)
            .withSsl(false)
            .withStartTls(false)
            .withVerifyPeer(false)
            .build();

    RedisClient redisClient = RedisClient.create(clientResources, redisURI);

    StatefulRedisConnection<String, String> connection = redisClient.connect(new
        StringCodec());

    RedisCommands<String, String> redisCommands = connection.sync();


    List<String> keys = new ArrayList<>();
    String masterKey = "zas:master-account:{18437381944377478}";
    String subKey = "zas:sub-account:{18437381944377478}:";
    String transLogKey = "zas:trans-logs:{18437381944377478}";
    keys.add(masterKey);
    keys.add(subKey);
    keys.add(transLogKey);

    List<String> arguments = new ArrayList<>();
    arguments.add("23342343243213456");
    arguments.add("-123");
    arguments.add("123");

    Object obj =
        redisCommands.evalsha(
            "f356c39aa9b393dda8d14b3ab03bf9d87a74cafd",
            ScriptOutputType.MULTI,
            keys.toArray(new String[0]),
            arguments.toArray(new String[0]));

    System.out.println(obj);
  }
}
