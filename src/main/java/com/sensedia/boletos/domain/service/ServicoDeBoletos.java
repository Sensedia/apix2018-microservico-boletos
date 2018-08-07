package com.sensedia.boletos.domain.service;

import com.sensedia.boletos.domain.Boleto;
import com.sensedia.boletos.domain.repository.RepositorioDeBoletos;
import java.util.Optional;
import lombok.NonNull;
import org.springframework.stereotype.Service;

/**
 * @author claudioed on 30/07/18.
 * Project apix2018-microservico-boletos
 */
@Service
public class ServicoDeBoletos {

  private final RepositorioDeBoletos repositorioDeBoletos;

  public ServicoDeBoletos(RepositorioDeBoletos repositorioDeBoletos) {
    this.repositorioDeBoletos = repositorioDeBoletos;
  }

  public Optional<Boleto> porId(@NonNull String id){
    return this.repositorioDeBoletos.findById(id);
  }

  public Optional<Boleto> porCodigo(@NonNull String codigoPagamento){
    return this.repositorioDeBoletos.findByCodigo(codigoPagamento);
  }

}
