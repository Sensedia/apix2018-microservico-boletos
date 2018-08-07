package com.sensedia.boletos.domain.resource;

import com.sensedia.boletos.domain.Boleto;
import com.sensedia.boletos.domain.resource.data.NovoBoletoRequest;
import com.sensedia.boletos.domain.service.GeradorDeBoletoService;
import com.sensedia.boletos.domain.service.ServicoDeBoletos;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.net.URI;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author claudioed on 30/07/18.
 * Project apix2018-microservico-boletos
 */
@RestController
@RequestMapping("/api/boletos")
@Api(tags = {"geracao de boletos"})
public class BoletoResource {

  private final GeradorDeBoletoService geradorDeBoletoService;

  private final ServicoDeBoletos servicoDeBoletos;

  public BoletoResource(GeradorDeBoletoService geradorDeBoletoService,
      ServicoDeBoletos servicoDeBoletos) {
    this.geradorDeBoletoService = geradorDeBoletoService;
    this.servicoDeBoletos = servicoDeBoletos;
  }

  @PostMapping
  @ApiOperation(value = "Cria um novo boleto de acordo com os valores passados no request")
  public ResponseEntity<Boleto> novoBoleto(@RequestBody NovoBoletoRequest request,UriComponentsBuilder uriBuilder) {
    final Boleto boleto = this.geradorDeBoletoService
        .novoBoleto(request.getValor(), request.getCpf());
    URI location = uriBuilder.path("api/boletos/{id}")
        .buildAndExpand(boleto.getId())
        .toUri();
    return ResponseEntity.created(location).body(boleto);
  }

  @GetMapping("/{id}")
  @ApiOperation(value = "Busca um boleto pelo identificador")
  public ResponseEntity<Boleto> porID(@PathVariable("id") String id) {
    final Optional<Boleto> boleto = this.servicoDeBoletos.porId(id);
    return boleto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

}
