package com.sensedia.boletos.domain.service.helper;

import lombok.Builder;
import lombok.Value;
import org.apache.commons.math3.random.RandomDataGenerator;


@Value
@Builder
public class RandomHelper {

  private static final RandomDataGenerator randomDataGenerator = new RandomDataGenerator();

  Long min;

  Long max;

  public Long rand() {
    if (min >= max) {
      throw new IllegalArgumentException("max must be greater than min");
    }
    return randomDataGenerator.nextSecureLong(min,max);
  }

}
