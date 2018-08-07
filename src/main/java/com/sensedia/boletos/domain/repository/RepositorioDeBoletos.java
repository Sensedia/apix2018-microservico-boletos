package com.sensedia.boletos.domain.repository;

import com.sensedia.boletos.domain.Boleto;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

/**
 * @author claudioed on 30/07/18.
 * Project apix2018-microservico-boletos
 */
public interface RepositorioDeBoletos extends CrudRepository<Boleto,String> {

  Optional<Boleto> findByCodigo(String codigoPagamento);

}
