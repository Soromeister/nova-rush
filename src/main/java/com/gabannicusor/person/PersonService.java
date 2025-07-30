package com.gabannicusor.person;

import com.gabannicusor.common.DataRoot;
import io.hypersistence.tsid.TSID;
import io.micronaut.eclipsestore.RootProvider;
import io.micronaut.eclipsestore.annotations.StoreParams;
import io.micronaut.eclipsestore.annotations.StoreReturn;
import io.micronaut.http.HttpStatus;
import jakarta.inject.Singleton;
import java.time.Instant;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Singleton
public class PersonService implements PersonRepository {

  private final RootProvider<DataRoot> rootProvider;

  public PersonService(RootProvider<DataRoot> rootProvider) {
    this.rootProvider = rootProvider;
  }

  protected Map<TSID, PersonEntity> getEntityMap() {
    return rootProvider.root().getPersons();
  }

  @Override
  public Collection<PersonEntity> list() {
    return getEntityMap().values()
        .parallelStream()
        .filter(e -> e.getDeletedAt() == null)
        .collect(Collectors.toSet());
  }

  @Override
  public PersonEntity find(Long id) {
    return getEntityMap().get(TSID.from(id));
  }

  @Override
  public PersonEntity create(PersonRecord record) {
    log.info("Received record {}", record);
    var map = getEntityMap();
    TSID id = TSID.Factory.getTsid();
    return performCreate(id.toLong(), map, record);
  }

  @Override
  public PersonEntity update(Long id, PersonRecord record) {
    log.trace("Received record {}", record);
    var map = getEntityMap();
    return performUpdate(id, map, record);
  }

  @Override
  public HttpStatus delete(Long id) {
    var map = getEntityMap();
    performDelete(map, id);
    return HttpStatus.OK;
  }

  @StoreParams("persons")
  protected PersonEntity performCreate(Long id, Map<TSID, PersonEntity> persons, PersonRecord record) {
    log.info("New ID is {}", id);
    var newPerson = PersonEntity.builder()
        .id(id)
        .firstName(record.firstName())
        .lastName(record.lastName())
        .telephoneNumber(record.telephoneNumber())
        .title(record.title())
        .createdAt(Instant.now())
        .build();
    log.info("New entity {}", newPerson);
    persons.put(TSID.from(id), newPerson);
    return newPerson;
  }

  @StoreReturn
  protected PersonEntity performUpdate(Long id, Map<TSID, PersonEntity> persons, PersonRecord record) {
    TSID tsid = TSID.from(id);
    var person = persons.get(tsid);
    person.setFirstName(record.firstName());
    person.setLastName(record.lastName());
    person.setTelephoneNumber(record.telephoneNumber());
    person.setTitle(record.title());
    person.setUpdatedAt(Instant.now());
    return person;
  }

  @StoreReturn
  protected Map<TSID, PersonEntity> performDelete(Map<TSID, PersonEntity> persons, Long id) {
    TSID tsid = TSID.from(id);
    if (persons.remove(tsid) != null) {
      log.trace("Person with id '{}' has been completely removed", tsid);
      return persons;
    }
    return null;
  }
}
