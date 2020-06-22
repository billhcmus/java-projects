package vn.sparrow.spring.repository;

import org.springframework.stereotype.Repository;
import vn.sparrow.spring.pet.Dog;
import vn.sparrow.spring.pet.Pet;

/**
 * Created by thuyenpt Date: 2020-05-18
 */
@Repository
public class PetRepositoryImpl implements PetRepository {
  @Override
  public Pet getPetByName(String name) {
    // Get pet from database
    return new Dog("Kio");
  }
}
