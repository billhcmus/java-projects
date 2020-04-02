package vn.zalopay.hack.reflection.anotation;

import java.lang.annotation.*;

/**
 * Created by thuyenpt Date: 2020-03-30
 */
@Target(ElementType.FIELD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelColumn {
  int index();
  String title();
  String description() default "Default value";
}
