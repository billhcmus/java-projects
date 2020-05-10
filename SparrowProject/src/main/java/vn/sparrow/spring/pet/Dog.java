package vn.sparrow.spring.pet;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/** Created by thuyenpt Date: 5/10/20 */
@Component
@Log4j2
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Dog implements Pet {
  @Override
  public void eat() {
    log.info("Dog eating");
  }
}
