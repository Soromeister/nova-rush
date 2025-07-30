package com.gabannicusor.person;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gabannicusor.common.BaseEntity;
import com.gabannicusor.team.TeamEntity;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@Serdeable
@SuperBuilder
@Introspected
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true, exclude = {"team"})
public class PersonEntity extends BaseEntity {

  String firstName;
  String lastName;
  String telephoneNumber;
  String title;

  @ToString.Exclude
  @JsonBackReference
  TeamEntity team;
}
