package com.sensedia.boletos.infra.gcp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;

@Configuration
public class PubSubConfiguration {

	@Bean
	public MessageChannel boletosInputChannel() {
		return new DirectChannel();
	}

}
