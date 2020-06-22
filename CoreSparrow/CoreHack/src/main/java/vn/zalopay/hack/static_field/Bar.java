package vn.zalopay.hack.static_field;

/**
 * Created by thuyenpt
 * Date: 2020-04-01
 */
public class Bar extends Base implements Showable {
  public void show() {
    System.out.println("Bar: " + id);
  }

  public void changeId(int i) {
    id = i;
  }
}
