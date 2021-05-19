package vn.zalopay.hack.lua.redis;

import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;

public class RedissonCacheImpl implements RedisCache {
  private final RedissonClient redissonClient;

  public RedissonCacheImpl(RedissonClient redissonClient) {
    this.redissonClient = redissonClient;
  }

  @Override
  public Object executeSyncCommand(Command syncCommand) {
    return
        redissonClient
            .getScript()
            .evalSha(
                RScript.Mode.READ_WRITE,
                syncCommand.getSha1(),
                RScript.ReturnType.MULTI,
                syncCommand.getKeys(),
                syncCommand.getArgs());
  }

}
