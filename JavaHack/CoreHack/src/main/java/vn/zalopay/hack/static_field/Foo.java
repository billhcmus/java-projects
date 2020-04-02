package vn.zalopay.hack.static_field;

/**
 * Created by thuyenpt
 * Date: 2020-04-01
 */
public class Foo extends Base implements Showable {
  public void show() {
    System.out.println("Foo: " + id);
  }

  public void changeId(int i) {
    id = i;
  }
}
