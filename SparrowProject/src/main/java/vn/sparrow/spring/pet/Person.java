package vn.sparrow.spring.pet;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/** Created by thuyenpt Date: 5/10/20 */
@Component
@Log4j2
@Getter
public class Person {
  @Getter Pet pet;

  public Person(@Qualifier("dog") Pet pet) {
    this.pet = pet;
  }
}
