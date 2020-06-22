package vn.zalopay.hack.exception;


import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by thuyenpt Date: 2020-03-31
 */
public class Runner {
  private static void validateRequest() throws InvalidParamenterException {
    throw new InvalidParamenterException("Request is invalid");
  }

  private static void handleRequest() throws OutBoundException {
    try {
      validateRequest();
    } catch (InvalidParamenterException e) {
      OutBoundException outBoundException = new OutBoundException("OutBoundException please try again");
      outBoundException.initCause(e);
      throw outBoundException;
    }
  }
  public static void main(String[] args) {
    try {
      handleRequest();
    } catch (OutBoundException e) {
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw);
      e.printStackTrace(pw);
      System.out.println(sw.toString());
    }
  }
}
