package vn.sparrow.vertx.common.utils;

import com.google.common.collect.ImmutableMap;
import lombok.Builder;
import vn.sparrow.vertx.common.exception.InvalidParameterException;

import java.util.Map;

/** Created by thuyenpt Date: 6/15/20 */
public class ErrorParserUtils {
  private ErrorParserUtils() {}

  private static final Map<Class<?>, Code> EXCEPTION_TO_CODE =
      ImmutableMap.<Class<?>, Code>builder()
          .put(InvalidParameterException.class, Code.INVALID_ARGUMENT)
          .build();

  public static Error getError(Throwable throwable) {
    Error.ErrorBuilder errorBuilder =
        Error.builder().code(Code.INTERNAL).message(ExceptionUtils.getSafeMessage(throwable));
    for (Map.Entry<Class<?>, Code> entry : EXCEPTION_TO_CODE.entrySet()) {
      if (throwable.getClass().isAssignableFrom(entry.getKey())) {
        return errorBuilder.code(entry.getValue()).build();
      }
    }
    return errorBuilder.build();
  }

  // Replace by your define code
  private enum Code {
    INTERNAL,
    INVALID_ARGUMENT
  }

  // Replace by your error object
  @Builder
  public static class Error {
    Code code;
    String message;
  }
}
