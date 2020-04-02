package vn.zalopay.hack.reflection;

import vn.zalopay.hack.reflection.anotation.Excel;
import vn.zalopay.hack.reflection.anotation.ExcelColumn;

/** Created by thuyenpt Date: 2020-03-30 */
@Excel(name = "Cat")
public class Cat extends Animal implements Say {
  public static final int DEFAULT_LEGS = 4;
  public static final String SAY = "Meo meo";

  @ExcelColumn(index = 0, title = "Name")
  private String name;

  @ExcelColumn(index = 0, title = "Age")
  public int age;

  Cat() {}

  Cat(String name, int age) {
    this.name = name;
    this.age = age;
  }

  @Override
  public int getNumberOfLegs() {
    return DEFAULT_LEGS;
  }

  @Override
  public String say() {
    return SAY;
  }

  public String getName() {
    return name;
  }

  private void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }
}
