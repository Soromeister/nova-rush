package com.gabannicusor.person;

import static io.micronaut.scheduling.TaskExecutors.BLOCKING;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.annotation.Status;
import io.micronaut.scheduling.annotation.ExecuteOn;
import java.util.Collection;
import lombok.extern.log4j.Log4j2;

@Log4j2
@ExecuteOn(BLOCKING)
@Controller("/person")
public class PersonController {

  protected final PersonRepository repository;

  public PersonController(PersonRepository repository) {
    this.repository = repository;
  }

  @Get(value = "/all")
  @Status(HttpStatus.OK)
  public Collection<PersonEntity> list() {
    return repository.list();
  }

  @Post
  @Status(HttpStatus.CREATED)
  public PersonEntity create(@Body PersonRecord record) {
    return repository.create(record);
  }

  @Put("/{id}")
  public PersonEntity update(@PathVariable Long id, @Body PersonRecord record) {
    return repository.update(id, record);
  }

  @Get("/{id}")
  public PersonEntity find(@PathVariable Long id) {
    log.info("Received request for entity with id {}", id);
    return repository.find(id);
  }

  @Delete("/{id}")
  @Status(HttpStatus.NO_CONTENT)
  public HttpStatus delete(@PathVariable Long id) {
    return repository.delete(id);
  }
}
