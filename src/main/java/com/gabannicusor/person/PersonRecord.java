package com.gabannicusor.person;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Builder;

@Builder
@Serdeable
@Introspected
public record PersonRecord(Long id,
                           String firstName,
                           String lastName,
                           String telephoneNumber,
                           String title) {

}
