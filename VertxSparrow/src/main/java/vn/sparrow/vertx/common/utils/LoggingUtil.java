package vn.sparrow.vertx.common.utils;

import com.google.protobuf.MessageOrBuilder;
import org.slf4j.Logger;

/** Created by thuyenpt Date: 2019-11-24 */
public class LoggingUtil {
  public static void LogReceive(String method, Logger logger, MessageOrBuilder request) {
    String requestJson = JsonProtoUtils.print(request);
    logger.info("{} HANDLE request={}", method, requestJson);
  }

  public static void LogResponse(
      String method,
      Logger logger,
      MessageOrBuilder request,
      MessageOrBuilder response,
      long duration) {
    String requestJson = JsonProtoUtils.print(request);
    String responseJson = JsonProtoUtils.print(response);
    logger.info(
        "{} COMPLETE request={}, response={}, duration={}",
        method,
        requestJson,
        responseJson,
        duration);
  }
}
