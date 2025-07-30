package com.gabannicusor.team;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.gabannicusor.common.BaseEntity;
import com.gabannicusor.person.PersonEntity;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@Serdeable
@SuperBuilder
@Introspected
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true, exclude = {"members"})
public class TeamEntity extends BaseEntity {

  String teamName;

  @ToString.Exclude
  @JsonManagedReference
  Set<PersonEntity> members;
}
