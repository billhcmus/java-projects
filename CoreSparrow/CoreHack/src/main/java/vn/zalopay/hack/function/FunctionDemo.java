package vn.zalopay.hack.function;

import java.sql.SQLOutput;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

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

  public static <T> String handleWithFunc(T input, Function<T, String> function) {
    return function.apply(input);
  }

  public static <T> void handleWithConsumer(T input, Consumer<T> consumer) {
    consumer.accept(input);
  }

  public static <T> T handleWithSupplier(Supplier<T> supplier) {
    return supplier.get();
  }

  public static void main(String[] args) {
    System.out.println("Function");
    String rs = handleWithFunc(5, t -> "a" + t);
    System.out.println(rs);
    System.out.println("Consumer");
    handleWithConsumer("xxx", System.out::println);
    System.out.println("Supplier");
    System.out.println(handleWithSupplier(() -> "xxxyyy"));
  }
}
