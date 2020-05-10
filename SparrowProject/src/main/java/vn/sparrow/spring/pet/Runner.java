package vn.sparrow.spring.pet;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/** Created by thuyenpt Date: 5/10/20 */
@SpringBootApplication
@Log4j2
public class Runner {
  public static void main(String[] args) {
    ApplicationContext context = SpringApplication.run(Runner.class, args);

    Pet pet = context.getBean(Dog.class);
    log.info("pet address: {}", pet);

    Person person = context.getBean(Person.class);
    log.info("pet of person address: {}", person.getPet());
  }
}
