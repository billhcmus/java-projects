package vn.sparrow.vertx.common.exception;

/** Created by thuyenpt Date: 2019-11-17 */
public class InvalidParameterException extends Exception {
  public InvalidParameterException(String message) {
    super(message);
  }

  public InvalidParameterException(Exception e) {
    super(e);
  }
}
