package vn.zalopay.hack.lua.redis;

/** Created by thuyenpt Date: 2020-04-05 */
public interface RedisCache {
  Object executeSyncCommand(Command syncCommand);
}
