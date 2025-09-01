package com.maqfiltros.sensors_contract.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "api.security")
@Validated
@Getter
@Setter
public class ApiGenericConfig {
	private String key;
@PostConstruct
public void init() {
    System.out.println("urlToken: " + key);
}
}
