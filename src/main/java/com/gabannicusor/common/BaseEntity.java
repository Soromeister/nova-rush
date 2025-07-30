package com.gabannicusor.common;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import java.time.Instant;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@Serdeable
@SuperBuilder
@Introspected
@EqualsAndHashCode(exclude = {"id", "createdAt", "updatedAt", "deletedAt"})
public abstract class BaseEntity{

  protected Long id;
  protected Instant createdAt;
  protected Instant updatedAt;
  protected Instant deletedAt;
}
