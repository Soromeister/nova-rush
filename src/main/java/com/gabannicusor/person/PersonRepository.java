package com.gabannicusor.person;

import io.micronaut.http.HttpStatus;
import java.util.Collection;

public interface PersonRepository {

  Collection<PersonEntity> list();

  PersonEntity find(Long id);

  PersonEntity create(PersonRecord record);

  PersonEntity update(Long id, PersonRecord record);

  HttpStatus delete(Long id);
}
