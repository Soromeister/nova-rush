package com.gabannicusor.team;

import io.micronaut.http.HttpStatus;
import java.util.Collection;

public interface TeamRepository {

  Collection<TeamEntity> list();

  TeamEntity find(Long id);

  TeamEntity create(TeamRecord record);

  TeamEntity update(Long id, TeamRecord record);

  HttpStatus delete(Long id);
}
