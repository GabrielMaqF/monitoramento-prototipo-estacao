package com.maqfiltros.sensors_contract;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling
//@EnableConfigurationProperties() // {GraphTokenProperties.class, GraphProperties.class, TokenWhatsapp.class,
									// MqttProperties.class}
public class SensorsContractApplication {

	public static void main(String[] args) {
		SpringApplication.run(SensorsContractApplication.class, args);
	}

}
