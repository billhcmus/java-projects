package vn.zalopay.zas.dubbo.DubboConsumer.filter;

/**
 * Created by thuyenpt
 * Date: 2020-02-19
 */
public class App {
  private static int i = 0;
  private static void one() {
    System.out.println(i++);
  }

  private static void two() {
    one(); one();
  }

  private static void four() {
    two(); two();
  }

  private static void eight() {
    four();four();
  }
  
  private static void sixteen() {
    eight(); eight();
  }

  public static void main(String[] args) {
//    one();
//    two();
//    four();
//    thirtythree();
//    sixtyfour();
    sixteen();

//    thirtythree();
//    four();
//    two();
//    one();
  }

  private static boolean foo(int i) {
    System.out.println(i);
    return i == 100 || foo(i + 1);
  }
}
