package com.maqfiltros.sensors_contract;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.maqfiltros.sensors_contract.config.MqttProperties;
import com.maqfiltros.sensors_contract.config.api.microsoft.GraphProperties;
import com.maqfiltros.sensors_contract.config.api.microsoft.GraphTokenProperties;
import com.maqfiltros.sensors_contract.services.whatsapp.token.TokenWhatsapp;

@SpringBootApplication
@EnableAsync
@EnableScheduling
@EnableConfigurationProperties({GraphTokenProperties.class, GraphProperties.class, TokenWhatsapp.class, MqttProperties.class})
public class SensorsContractApplication {

	public static void main(String[] args) {
		SpringApplication.run(SensorsContractApplication.class, args);
	}

}
