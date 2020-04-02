package vn.zalopay.hack.reflection.anotation;

import java.lang.annotation.*;

/**
 * Created by thuyenpt Date: 2020-03-30
 */
@Target(ElementType.TYPE)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface Excel {
  int index() default 0;
  String name() default "Sheet 1";
}
