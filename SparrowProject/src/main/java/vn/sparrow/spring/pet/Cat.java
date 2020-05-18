package vn.sparrow.spring.pet;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

/**
 * Created by thuyenpt
 * Date: 5/10/20
 */
@Component("cat")
@Log4j2
public class Cat implements Pet {
  @Override
  public void eat() {
    log.info("Cat eating");
  }
}
