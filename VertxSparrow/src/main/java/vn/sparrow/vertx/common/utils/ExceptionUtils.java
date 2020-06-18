package vn.sparrow.vertx.common.utils;

import java.sql.SQLException;

public class ExceptionUtils {

  private ExceptionUtils() {}

  public static String getDetail(Throwable ex) {
    if (ex == null) {
      return "";
    }

    String details = "";
    if (ex instanceof SQLException) {
      SQLException sqlException = (SQLException) ex;
      details =
          "Code="
              + sqlException.getErrorCode()
              + ", \nsqlState="
              + sqlException.getSQLState()
              + "\n, ";
    }

    details += getMessage(ex);

    return details;
  }

  private static String getMessage(Throwable ex) {
    return " message: "
        + ex.getMessage()
        + "\n, stackTrace: "
        + org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace(ex);
  }

  public static String getSafeMessage(Throwable ex) {
    if (ex == null || ex.getMessage() == null) {
      return "";
    }
    return ex.getMessage();
  }

  public static boolean isEqual(Object current, Class<?> expect) {
    return current != null && current.getClass().isAssignableFrom(expect);
  }
}
