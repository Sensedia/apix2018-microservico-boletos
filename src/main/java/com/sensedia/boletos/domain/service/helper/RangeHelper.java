package com.sensedia.boletos.domain.service.helper;

import java.util.Collections;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RangeHelper {

  NumberSpec numberSpec;

  public NumberRange range() {
    Integer suffixStartDigits = String.valueOf(numberSpec.getStart()).length();
    Integer suffixEndDigits = String.valueOf(numberSpec.getEnd()).length();
    final String suffixStart = String.join("", Collections.nCopies(numberSpec.getLength() - suffixStartDigits, "0"));
    final String suffixEnd = String.join("", Collections.nCopies(numberSpec.getLength() -suffixEndDigits, "9"));
    return NumberRange.builder().start(Long.valueOf(numberSpec.getStart()+suffixStart)).end(Long.valueOf(numberSpec.getEnd()+suffixEnd)).build();
  }

}
