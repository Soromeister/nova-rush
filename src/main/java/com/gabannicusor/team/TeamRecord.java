package com.gabannicusor.team;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import java.util.Set;
import lombok.Builder;

@Builder
@Serdeable
@Introspected
public record TeamRecord(Long id,
                         String teamName,
                         Set<Long> members) {

}
