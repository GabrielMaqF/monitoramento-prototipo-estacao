package com.maqfiltros.sensors_contract.services.microsoft.tokens;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maqfiltros.sensors_contract.config.api.microsoft.GraphTokenProperties;

@Service
public class TokenMicrosoftGraph {

    @Autowired
    private GraphTokenProperties graphTokenProperties;

    @Autowired
    private ObjectMapper objectMapper;

    private final HttpClient httpClient = HttpClient.newHttpClient();

    @Async//("taskExecutor")
    public CompletableFuture<String> getToken() {
        return gerarTokenAsync().thenApply(ResponseTokenMicrosftGraph::getAccessToken);
    }

    private CompletableFuture<ResponseTokenMicrosftGraph> gerarTokenAsync() {
        try {
            Map<String, String> parameters = Map.of(
                    "client_id", graphTokenProperties.getClientId(),
                    "scope", graphTokenProperties.getScope(),
                    "client_secret", graphTokenProperties.getClientSecret(),
                    "grant_type", graphTokenProperties.getGrantType()
            );

            String form = parameters.entrySet().stream()
                    .map(entry -> URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8) + "=" +
                                  URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8))
                    .collect(Collectors.joining("&"));

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(graphTokenProperties.getUrlToken()))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(form))
                    .build();

            return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(response -> {
                        if (response.statusCode() == 200) {
                            try {
                                return objectMapper.readValue(response.body(), ResponseTokenMicrosftGraph.class);
                            } catch (Exception e) {
                                throw new RuntimeException("Erro ao deserializar token", e);
                            }
                        } else {
                            throw new RuntimeException("Erro ao obter token: " + response.statusCode() + " - " + response.body());
                        }
                    });

        } catch (Exception e) {
            CompletableFuture<ResponseTokenMicrosftGraph> failed = new CompletableFuture<>();
            failed.completeExceptionally(new RuntimeException("Falha ao gerar token", e));
            return failed;
        }
    }
}


//package com.maqfiltros.sensors_contract.services.microsoft.tokens;
//
//import java.net.URI;
//import java.net.URLEncoder;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//import java.nio.charset.StandardCharsets;
//import java.util.Map;
//import java.util.concurrent.CompletableFuture;
//import java.util.stream.Collectors;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.maqfiltros.sensors_contract.config.api.microsoft.GraphTokenProperties;
//
//@Service
//public class TokenMicrosoftGraph {
//
//	@Autowired
//	private GraphTokenProperties graphTokenProperties;
//	@Autowired
//	private ObjectMapper objectMapper;
//	@Async
//	public CompletableFuture<String> getToken() {
//		ResponseTokenMicrosftGraph responseToken = geradorToken();
//		String token = responseToken.getAccessToken();
//		return CompletableFuture.completedFuture(token);
//	}
//
//	private ResponseTokenMicrosftGraph geradorToken() {
//		try {
//			Map<String, String> parameters = Map.of("client_id", graphTokenProperties.getClientId(), "scope",
//					graphTokenProperties.getScope(), "client_secret", graphTokenProperties.getClientSecret(), "grant_type",
//					graphTokenProperties.getGrantType());
//
//			String form = parameters.entrySet().stream()
//					.map(entry -> URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8) + "="
//							+ URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8))
//					.collect(Collectors.joining("&"));
//
//			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(graphTokenProperties.getUrlToken()))
//					.header("Content-Type", "application/x-www-form-urlencoded")
//					.POST(HttpRequest.BodyPublishers.ofString(form)).build();
//
//			HttpClient client = HttpClient.newHttpClient();
//			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//
//			if (response.statusCode() == 200) {
//				objectMapper = new ObjectMapper();
//				return objectMapper.readValue(response.body(), ResponseTokenMicrosftGraph.class);
//			} else {
//				throw new RuntimeException("Erro ao obter token: " + response.statusCode() + " - " + response.body());
//			}
//
//		} catch (Exception e) {
//			throw new RuntimeException("Falha ao gerar token", e);
//		}
//	}
//}
