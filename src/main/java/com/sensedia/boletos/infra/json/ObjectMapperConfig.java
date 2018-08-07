package com.sensedia.boletos.infra.json;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author claudioed on 05/08/18.
 * Project apix2018-microservico-boletos
 */
@Configuration
public class ObjectMapperConfig {

  @Bean
  public ObjectMapper mapper(){
    final ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    return mapper;
  }

}
