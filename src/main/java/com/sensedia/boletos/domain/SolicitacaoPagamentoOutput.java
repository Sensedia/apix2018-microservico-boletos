package com.sensedia.boletos.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SolicitacaoPagamentoOutput implements Serializable {

	private static final long serialVersionUID = 1L;

	private final Long id;

	private final Collection<SolicitacaoPagamentoDetalheOutput> detalhes;

	public Collection<SolicitacaoPagamentoDetalheOutput> getRetornos() {
		return Collections.unmodifiableCollection(detalhes);
	}

}
