package vn.zalopay.hack;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Hello world!
 */
public class CoreHack {
  public static void main(String[] args) {
//    System.out.println("Hello World!");
//    Bar bar = new BarImpl();
    String str = "x";

    String other = Optional.ofNullable(str).orElse("");
    System.out.println(other);
  }
}
