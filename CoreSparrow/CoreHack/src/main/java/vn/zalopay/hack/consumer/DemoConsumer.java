package vn.zalopay.hack.consumer;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/** Created by thuyenpt Date: 2020-02-24 */
public class DemoConsumer {
  private static <T> void processString(T input, Consumer<T> consumer) {
    consumer.accept(input);
  }

  private static <T, U> void processString(T input, U postfix, BiConsumer<T, U> biConsumer) {
    biConsumer.accept(input, postfix);
  }

  public static void main(String[] args) {
    processString("Bill", input -> System.out.println("Hello " + input));
    processString(
        "Bill",
        ", nice to meet you.",
        (name, postfix) -> System.out.println("Hello " + name + postfix));
  }
}
