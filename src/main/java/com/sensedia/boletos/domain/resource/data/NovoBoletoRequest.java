package com.sensedia.boletos.domain.resource.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import lombok.Data;

@Data
@ApiModel(value = "request para criacao de um novo boleto")
public class NovoBoletoRequest {

  @ApiModelProperty("cpf do sacado")
  private String cpf;

  @ApiModelProperty("valor do boleto")
  private BigDecimal valor;

}
