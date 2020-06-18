package vn.sparrow.vertx.common.utils;

import org.apache.commons.lang3.StringUtils;
import vn.sparrow.vertx.common.exception.InvalidParameterException;

/** Created by thuyenpt Date: 2019-11-15 */
public class ValidateUtils {
  public static void notNull(Object object, String name) throws InvalidParameterException {
    if (object == null) {
      throw new InvalidParameterException(name + " must NOT null");
    }
  }

  public static void notEmpty(String uid, String name) throws InvalidParameterException {
    if (StringUtils.isEmpty(uid)) {
      throw new InvalidParameterException(name + " must NOT empty");
    }
  }

  public static void notZero(long value, String name) throws InvalidParameterException {
    if (value == 0) {
      throw new InvalidParameterException(name + " must NOT a zero");
    }
  }
}
