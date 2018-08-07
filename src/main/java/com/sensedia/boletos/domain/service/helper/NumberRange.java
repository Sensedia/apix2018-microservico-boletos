package com.sensedia.boletos.domain.service.helper;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class NumberRange {

  Long start;

  Long end;

}
