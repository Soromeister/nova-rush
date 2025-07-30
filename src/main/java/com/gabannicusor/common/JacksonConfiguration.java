package com.gabannicusor.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Primary;
import jakarta.inject.Singleton;

@Singleton
public class JacksonConfiguration {

  @Bean
  @Primary
  public ObjectMapper objectMapper() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, false);
    mapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
    return mapper;
  }
}
