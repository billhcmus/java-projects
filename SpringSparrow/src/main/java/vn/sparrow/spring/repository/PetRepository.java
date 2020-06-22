package vn.sparrow.spring.repository;

import vn.sparrow.spring.pet.Pet;

/** Created by thuyenpt Date: 2020-05-18 */
public interface PetRepository {
  Pet getPetByName(String name);
}
