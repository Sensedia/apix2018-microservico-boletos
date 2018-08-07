package com.sensedia.boletos.domain.service;

import com.sensedia.boletos.domain.Boleto;
import com.sensedia.boletos.domain.SolicitacaoPagamentoInput;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * @author claudioed on 05/08/18.
 * Project apix2018-microservico-boletos
 */
@Service
public class ServicoValidacaoBoleto {

  private final ServicoDeBoletos servicoDeBoletos;

  public ServicoValidacaoBoleto(ServicoDeBoletos servicoDeBoletos) {
    this.servicoDeBoletos = servicoDeBoletos;
  }

  public boolean validaBoleto(SolicitacaoPagamentoInput solicitacaoPagamento) {
    final Optional<Boleto> optionalBoleto = this.servicoDeBoletos
        .porCodigo(solicitacaoPagamento.getCodigoPagamento());
    return optionalBoleto.isPresent();
  }

}
