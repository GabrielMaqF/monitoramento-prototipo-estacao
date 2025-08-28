package com.maqfiltros.sensors_contract.services.whatsapp;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.maqfiltros.sensors_contract.services.whatsapp.token.TokenWhatsapp;

@Service
public class WhatsappService {

	@Autowired
	private TokenWhatsapp tokenWhatsapp;

	@Async("taskExecutor")
	public CompletableFuture<String> sendAlertBaixaVazao(String telefone, String nomeEscola, String situacao) {
		telefone = formatTelefone(telefone);

		if (telefone == null || telefone == "") {
			return CompletableFuture.completedFuture("{\"error\": \"Request failed or phone invalid\"}");
		}

		String url = "https://graph.facebook.com/v22.0/204938112707902/messages";
		String authorization = "Bearer " + tokenWhatsapp.getUrlToken();

		String jsonBody = String.format("""
				{
				    "messaging_product": "whatsapp",
				    "recipient_type": "individual",
				    "to": "%s",
				    "type": "template",
				    "template": {
				        "name": "alerta_baixa_vazao",
				        "language": {
				            "code": "pt_BR"
				        },
				        "components": [
				            {
				                "type": "header",
				                "parameters": [
				                    {
				                        "type": "text",
				                        "text": "%s"
				                    }
				                ]
				            },
				            {
				                "type": "body",
				                "parameters": [
				                    {
				                        "type": "text",
				                        "text": "%s"
				                    },
				                    {
				                        "type": "text",
				                        "text": "%s"
				                    }
				                ]
				            }
				        ]
				    }
				}
				""", telefone, nomeEscola, nomeEscola, situacao);

		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).header("Authorization", authorization)
				.header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(jsonBody)).build();

		HttpClient client = HttpClient.newHttpClient();
		return client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(response -> {
			if (response.statusCode() == 200) {
				return response.body();
			} else {
				System.err.println("Erro: Status code " + response.statusCode() + ", body: " + response.body());
				return "{\"error\": \"Request failed or phone invalid\"}";
			}
		}).exceptionally(ex -> {
			ex.printStackTrace();
			return "{\"error\": \"Request failed or phone invalid\"}";
		});

	}

	private String formatTelefone(String telefone) {
		if (telefone == null)
			return "";

		String clean = telefone.replaceAll("[^\\d]", "");

		if (!clean.startsWith("55")) {
			clean = "55" + clean;
		}

		if (clean.matches("^55\\d{2}\\d{8,9}$")) {
			return clean;
		}

		return "";
	}

}
