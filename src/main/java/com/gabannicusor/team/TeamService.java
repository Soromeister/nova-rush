package com.gabannicusor.team;

import com.gabannicusor.common.DataRoot;
import com.gabannicusor.person.PersonEntity;
import com.gabannicusor.person.PersonRepository;
import com.gabannicusor.person.PersonService;
import io.hypersistence.tsid.TSID;
import io.micronaut.eclipsestore.RootProvider;
import io.micronaut.eclipsestore.annotations.StoreParams;
import io.micronaut.eclipsestore.annotations.StoreReturn;
import io.micronaut.http.HttpStatus;
import jakarta.inject.Singleton;
import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.eclipse.store.storage.types.StorageManager;

@Log4j2
@Singleton
public class TeamService implements TeamRepository {

  private final RootProvider<DataRoot> rootProvider;
  private final StorageManager storageManager;

  public TeamService(RootProvider<DataRoot> rootProvider, StorageManager storageManager) {
    this.rootProvider = rootProvider;
    this.storageManager = storageManager;
  }

  protected Map<TSID, TeamEntity> getEntityMap() {
    return rootProvider.root().getTeams();
  }

  @Override
  public Collection<TeamEntity> list() {
    return getEntityMap().values()
        .parallelStream()
        .filter(e -> e.getDeletedAt() == null)
        .collect(Collectors.toSet());
  }

  @Override
  public TeamEntity find(Long id) {
    return getEntityMap().get(TSID.from(id));
  }

  @Override
  public TeamEntity create(TeamRecord record) {
    log.info("Received record {}", record);
    var map = getEntityMap();
    TSID id = TSID.Factory.getTsid();
    return performCreate(id.toLong(), map, record);
  }

  @Override
  public TeamEntity update(Long id, TeamRecord record) {
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

  @StoreParams("teams")
  protected TeamEntity performCreate(Long id, Map<TSID, TeamEntity> teams, TeamRecord record) {
    log.info("New ID is {}", id);
    var persons = getPersons(record.members());
    var newTeam = TeamEntity.builder()
        .id(id)
        .teamName(record.teamName())
        .members(persons)
        .createdAt(Instant.now())
        .build();
    log.info("New entity {}", newTeam);
    updatePersons(persons, newTeam);
    log.info("Persons updated, ready to add to the map");
    teams.put(TSID.from(id), newTeam);
    log.info("Team added to the map");
    return newTeam;
  }

  @StoreReturn
  protected TeamEntity performUpdate(Long id, Map<TSID, TeamEntity> teams, TeamRecord record) {
    TSID tsid = TSID.from(id);
    var team = teams.get(tsid);
    team.setTeamName(record.teamName());
    team.setMembers(getPersons(record.members()));
    team.setUpdatedAt(Instant.now());
    return team;
  }

  @StoreReturn
  protected Map<TSID, TeamEntity> performDelete(Map<TSID, TeamEntity> teams, Long id) {
    TSID tsid = TSID.from(id);
    if (teams.remove(tsid) != null) {
      log.trace("Team with id '{}' has been completely removed", tsid);
      return teams;
    }
    return null;
  }

  private Set<PersonEntity> getPersons(Set<Long> ids) {
    PersonRepository repository = new PersonService(rootProvider);
    var persons = new HashSet<PersonEntity>();
    ids.forEach(id -> persons.add(repository.find(id)));
    return persons;
  }

  @StoreReturn
  protected Set<PersonEntity> updatePersons(Set<PersonEntity> persons, TeamEntity team) {
    log.info("Updating persons for team {}", team);
    PersonRepository repository = new PersonService(rootProvider);
    persons.forEach(person -> {
      person.setTeam(team);
      storageManager.store(person);
    });
    log.info("Persons updated");
    return persons;
  }
}
