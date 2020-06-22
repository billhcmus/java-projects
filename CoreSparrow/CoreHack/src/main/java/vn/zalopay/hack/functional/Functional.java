package vn.zalopay.hack.functional;

/** Created by thuyenpt Date: 2020-02-23 */
@FunctionalInterface
interface StringProcessor {
  String process(String str);
}

public class Functional {
  public String processString(String input, StringProcessor processor) {
    return processor.process(input);
  }

  public void handleText(String input, StringProcessor processor) {
    String output = this.processString(input, processor);
    System.out.println(output);
  }

  public static StringProcessor sayHi() {
    return str -> "hi " + str;
  }

  public static StringProcessor sayHello() {
    return str -> "hello " + str;
  }

  public static void main(String[] args) {
    Functional functional = new Functional();
    functional.handleText("bill", sayHi());
  }
}
