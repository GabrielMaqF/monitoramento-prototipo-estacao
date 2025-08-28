package com.maqfiltros.sensors_contract.config.api.microsoft;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

//@Component
@ConfigurationProperties(prefix = "api.microsoft.graph")
@Validated
public class GraphTokenProperties {

    private String urlToken;	
    private String clientId;
    private String clientSecret;
    private String scope;
    private String grantType;
    
//    @PostConstruct
//    public void init() {
//        System.out.println("urlToken: " + urlToken);
//        System.out.println("clientId: " + clientId);
//        System.out.println("clientSecret: " + clientSecret);
//        System.out.println("scope: " + scope);
//        System.out.println("grantType: " + grantType);
//    }
    
    public String getUrlToken() {
        return urlToken;
    }

    public void setUrlToken(String urlToken) {
        this.urlToken = urlToken;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }
}
