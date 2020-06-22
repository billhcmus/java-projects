package vn.sparrow.spring.service;

import org.springframework.stereotype.Service;
import vn.sparrow.spring.pet.Pet;
import vn.sparrow.spring.repository.PetRepository;

/**
 * Created by thuyenpt Date: 2020-05-18
 */
@Service
public class PetStoreService {
  private final PetRepository petRepository;

  public PetStoreService(PetRepository petRepository) {
    this.petRepository = petRepository;
  }

  public Pet getRandomPet() {
    return petRepository.getPetByName("abc");
  }
}
