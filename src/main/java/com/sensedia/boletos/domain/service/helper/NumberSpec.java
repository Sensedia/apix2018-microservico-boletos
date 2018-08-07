package com.sensedia.boletos.domain.service.helper;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class NumberSpec {

  private Long start;

  private Long end;

  private Integer length;

}
