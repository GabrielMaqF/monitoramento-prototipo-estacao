package com.maqfiltros.sensors_contract.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

//@Configuration
@ConfigurationProperties(prefix = "mqtt.broker")
@Validated
public class MqttProperties {
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
