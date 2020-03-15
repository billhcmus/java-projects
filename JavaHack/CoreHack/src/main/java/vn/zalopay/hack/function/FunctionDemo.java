package vn.zalopay.hack.function;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by thuyenpt
 * Date: 2020-02-24
 */
public class FunctionDemo {
  public static String processString(Function<Integer, String> function, Integer num) {
    Function<Integer, Integer> pow = n -> n * n;
    return function.compose(pow).apply(num);
  }

  public static String processString(BiFunction<Integer, Integer, String> biFunction, Integer num1, Integer num2) {
    return biFunction.apply(num1, num2);
  }

  public static void handle(Integer num) {
    System.out.println(processString(n -> "Num: " + n.toString(), num));
    System.out.println(processString((n1, n2) -> "Num: " + n1 * n2, num, num));
  }

  public static void main(String[] args) {
    handle(5);
  }
}
