package com.sensedia.boletos.domain.service;

import com.sensedia.boletos.domain.Boleto;
import com.sensedia.boletos.domain.repository.RepositorioDeBoletos;
import com.sensedia.boletos.domain.service.helper.NumberRange;
import com.sensedia.boletos.domain.service.helper.NumberSpec;
import com.sensedia.boletos.domain.service.helper.RandomHelper;
import com.sensedia.boletos.domain.service.helper.RangeHelper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.StringJoiner;
import java.util.UUID;
import org.springframework.util.StringUtils;

@Service
public class GeradorDeBoletoService {

  private final RepositorioDeBoletos repositorioDeBoletos;

  private final NumberRange cincoDigitos = RangeHelper.builder()
      .numberSpec(NumberSpec.builder().length(5).start(0L).end(99999L).build()).build().range();

  private final NumberRange seisDigitos = RangeHelper.builder()
      .numberSpec(NumberSpec.builder().length(6).start(0L).end(999999L).build()).build().range();

  private final NumberRange umDigito = RangeHelper.builder()
      .numberSpec(NumberSpec.builder().length(1).start(0L).end(9L).build()).build().range();

  public GeradorDeBoletoService(RepositorioDeBoletos repositorioDeBoletos) {
    this.repositorioDeBoletos = repositorioDeBoletos;
  }

  public Boleto novoBoleto(BigDecimal valor, String cpf) {
    final Long primeiroBloco = RandomHelper.builder().min(cincoDigitos.getStart())
        .max(cincoDigitos.getEnd()).build()
        .rand();
    final Long segundoBloco = RandomHelper.builder().min(cincoDigitos.getStart())
        .max(cincoDigitos.getEnd()).build()
        .rand();
    final Long terceiroBloco = RandomHelper.builder().min(cincoDigitos.getStart())
        .max(cincoDigitos.getEnd()).build()
        .rand();
    final Long quartoBloco = RandomHelper.builder().min(seisDigitos.getStart())
        .max(seisDigitos.getEnd()).build()
        .rand();
    final Long quintoBloco = RandomHelper.builder().min(cincoDigitos.getStart())
        .max(cincoDigitos.getEnd()).build()
        .rand();
    final Long sextoBloco = RandomHelper.builder().min(seisDigitos.getStart())
        .max(seisDigitos.getEnd()).build()
        .rand();

    final Long setimoBloco = RandomHelper.builder().min(umDigito.getStart())
        .max(umDigito.getEnd()).build()
        .rand();

    final String oitavoBloco = "0000000000000".substring(valor.toString().length()) + valor.toString();

    final StringJoiner joiner = new StringJoiner("").add(primeiroBloco.toString())
        .add(segundoBloco.toString())
        .add(terceiroBloco.toString()).add(quartoBloco.toString()).add(quintoBloco.toString())
        .add(sextoBloco.toString()).add(setimoBloco.toString()).add(oitavoBloco);
    final Boleto boleto = Boleto.builder().codigo(joiner.toString()).cpf(cpf)
        .id(UUID.randomUUID().toString()).build();
    return this.repositorioDeBoletos.save(boleto);
  }

}
