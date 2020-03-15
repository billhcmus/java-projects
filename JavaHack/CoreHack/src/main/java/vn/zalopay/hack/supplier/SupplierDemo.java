package vn.zalopay.hack.supplier;

import java.util.function.Supplier;

/**
 * Created by thuyenpt
 * Date: 2020-02-24
 */
public class SupplierDemo {
  public static String processString(Supplier<String> supplier) {
    return "Hello " + supplier.get();
  }

  public static void handle(String name) {
    System.out.println(processString(() -> "xxx " + name));
  }

  public static void main(String[] args) {
    handle("Bill");
  }
}
