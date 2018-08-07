package com.sensedia.boletos.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author claudioed on 30/07/18.
 * Project apix2018-microservico-boletos
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "boleto")
public class Boleto {

  @Id
  private String id;

  private String codigo;

  private String cpf;

}
