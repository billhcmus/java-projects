package vn.sparrow.spring.pet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

/** Created by thuyenpt Date: 5/10/20 */
@Component("cat")
@Log4j2
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cat implements Pet {
  String name;

  @Override
  public void eat() {
    log.info("Cat eating");
  }
}
