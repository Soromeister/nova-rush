package com.gabannicusor.common;


import com.gabannicusor.person.PersonEntity;
import com.gabannicusor.team.TeamEntity;
import io.hypersistence.tsid.TSID;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Singleton;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Getter;

@Getter
@Singleton
public class DataRoot {
  private Map<TSID, PersonEntity> persons = new ConcurrentHashMap<>();
  private Map<TSID, TeamEntity> teams = new ConcurrentHashMap<>();

  @PostConstruct
  public void init() {
    if (persons == null) {
      persons = new ConcurrentHashMap<>();
    }
    if (teams == null) {
      teams = new ConcurrentHashMap<>();
    }
  }
}
