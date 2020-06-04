package vn.sparrow.spring.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import vn.sparrow.spring.config.DatabaseConnector;
import vn.sparrow.spring.config.MysqlConnector;
import vn.sparrow.spring.pet.Pet;

/**
 * Created by thuyenpt Date: 2020-05-18
 */
@SpringBootApplication
@Log4j2
@ComponentScan("vn.sparrow.spring")
public class Runner {
  public static void main(String[] args) {
    ApplicationContext context = SpringApplication.run(Runner.class, args);
    PetStoreService petStoreService = context.getBean(PetStoreService.class);
    Pet pet = petStoreService.getRandomPet();
    log.info(pet);

    DatabaseConnector connector = context.getBean(MysqlConnector.class);
    connector.connect();
  }
}
