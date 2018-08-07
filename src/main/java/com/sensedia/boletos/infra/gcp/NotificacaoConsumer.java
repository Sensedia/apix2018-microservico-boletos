package com.sensedia.boletos.infra.gcp;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gcp.pubsub.core.PubSubOperations;
import org.springframework.cloud.gcp.pubsub.integration.AckMode;
import org.springframework.cloud.gcp.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import org.springframework.cloud.gcp.pubsub.support.GcpPubSubHeaders;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Component;

import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.gson.Gson;
import com.sensedia.boletos.domain.SolicitacaoPagamentoDetalheOutput;
import com.sensedia.boletos.domain.SolicitacaoPagamentoInput;
import com.sensedia.boletos.domain.SolicitacaoPagamentoOutput;
import com.sensedia.boletos.domain.StatusRetornoEnum;
import com.sensedia.boletos.domain.service.ServicoValidacaoBoleto;
import com.sensedia.boletos.infra.gcp.NotificacaoProducer.NotificacaoOutputGateway;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class NotificacaoConsumer {

	private final Gson gson;
	
	private final String subscriptionName;

	private final ServicoValidacaoBoleto servicoValidacaoBoleto;

	private final NotificacaoProducer.NotificacaoOutputGateway notificacaoOutputGateway;

	public NotificacaoConsumer(
			@Value("${subscription.name}")
					String subscriptionName, Gson gson, ServicoValidacaoBoleto servicoValidacaoBoleto,
			NotificacaoOutputGateway notificacaoOutputGateway) {
		this.subscriptionName = subscriptionName;
		this.gson = gson;
		this.servicoValidacaoBoleto = servicoValidacaoBoleto;
		this.notificacaoOutputGateway = notificacaoOutputGateway;
	}

	@Bean
	public PubSubInboundChannelAdapter messageChannelAdapter(
			@Qualifier("boletosInputChannel")
					MessageChannel inputChannel, PubSubOperations pubSubTemplate) {
		PubSubInboundChannelAdapter adapter = new PubSubInboundChannelAdapter(pubSubTemplate, subscriptionName);
		adapter.setOutputChannel(inputChannel);
		adapter.setAckMode(AckMode.MANUAL);
		return adapter;
	}

	@Bean
	@ServiceActivator(inputChannel = "boletosInputChannel")
	public MessageHandler messageReceiver() {
		return message -> {
			log.debug("Mensagem recebida: " + message.getPayload());

			AckReplyConsumer consumer = (AckReplyConsumer) message.getHeaders().get(GcpPubSubHeaders.ACKNOWLEDGEMENT);

			try {
				SolicitacaoPagamentoInput solicitacaoPagamentoInput = gson.fromJson(message.getPayload().toString(), SolicitacaoPagamentoInput.class);

				final boolean boletoValido = this.servicoValidacaoBoleto.validaBoleto(solicitacaoPagamentoInput);

				if (boletoValido) {
					log.info("Boleto encontrado para o cpf " + solicitacaoPagamentoInput.getUsuarioCPF());
					SolicitacaoPagamentoOutput solicitacaoPagamentoOutput = new SolicitacaoPagamentoOutput(
							solicitacaoPagamentoInput.getId(), Collections.singletonList(
							new SolicitacaoPagamentoDetalheOutput(StatusRetornoEnum.SUCESSO, "Boleto Valido")));
					notificacaoOutputGateway.sendToPubsub(gson.toJson(solicitacaoPagamentoOutput));
				} else {
					log.error("Boleto NAO encontrado para o cpf " + solicitacaoPagamentoInput.getUsuarioCPF());
					SolicitacaoPagamentoOutput solicitacaoPagamentoOutput = new SolicitacaoPagamentoOutput(
							solicitacaoPagamentoInput.getId(), Collections.singletonList(
							new SolicitacaoPagamentoDetalheOutput(StatusRetornoEnum.ERROR, "Boleto nao encontrado")));
					notificacaoOutputGateway.sendToPubsub(gson.toJson(solicitacaoPagamentoOutput));
				}
				consumer.ack();

			} catch (Exception e) {
				log.error("Erro processando mensagem", e);
				consumer.nack();
			}
		};
	}

}
