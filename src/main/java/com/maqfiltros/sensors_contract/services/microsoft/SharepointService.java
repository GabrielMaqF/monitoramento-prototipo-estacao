package com.maqfiltros.sensors_contract.services.microsoft;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maqfiltros.sensors_contract.config.api.microsoft.GraphProperties;
import com.maqfiltros.sensors_contract.enums.TipoEquipamento;
import com.maqfiltros.sensors_contract.services.microsoft.tokens.TokenMicrosoftGraph;

@Service
public class SharepointService {
	
    private final ObjectMapper objectMapper = new ObjectMapper();

	private String idLista;

	@Autowired
	private TokenMicrosoftGraph tokenGrahp;

	@Autowired
	private GraphProperties graphProperties;

	@Async//("taskExecutor")
	public CompletableFuture<List<String>> telefoneResponsaveis(Long idEquipamento, TipoEquipamento tipoEquipamento) {
		return tokenGrahp.getToken().thenComposeAsync(token -> {
			String authorization = "Bearer " + token;
			return buscarResponsaveisComToken(idEquipamento, tipoEquipamento, token).thenComposeAsync(responsaveis -> {
				if (responsaveis != null && !responsaveis.isEmpty()) {
					List<CompletableFuture<String>> futurosTelefones = responsaveis.stream().map(r -> {
						try {
							String email = getEmailResponsavel(r);
							return getTelefoneResponsavel(email, authorization); // já retorna CompletableFuture<String>
						} catch (Exception e) {
							e.printStackTrace();
							return CompletableFuture.completedFuture(""); // fallback em caso de erro
						}
					}).collect(Collectors.toList());

					return CompletableFuture.allOf(futurosTelefones.toArray(new CompletableFuture[0])).thenApply(
							v -> futurosTelefones.stream().map(CompletableFuture::join).collect(Collectors.toList()));
				}
				return CompletableFuture.completedFuture(Collections.emptyList());
			});
		});
	}

	@Async//("taskExecutor")
	public CompletableFuture<List<String>> buscarResponsaveisComToken(Long idEquipamento, TipoEquipamento tipoEquipamento,
			String token) {
		return buscarEquipamento(idEquipamento, tipoEquipamento, true, token).thenComposeAsync(fields -> {
			return CompletableFuture.completedFuture(getResponsaveis(fields));
		});
	}

	@Async//("taskExecutor")
	public CompletableFuture<String> buscarEquipamento(Long idEquipamento, TipoEquipamento tipoEquipamento,
			boolean onlyFields, String token) {

		switch (tipoEquipamento) {
		case Hidrometro: {
			this.idLista = graphProperties.getListHidrometro();
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + tipoEquipamento);
		}

		HttpClient client = HttpClient.newHttpClient();
		String authorization = "Bearer " + token;

		return buscarItensListaPorEquipamentoId(authorization, idEquipamento, client)
				.thenComposeAsync(responseListItens -> {
					if (responseListItens.statusCode() == 200) {
						String id = getIdValue(responseListItens);
						if (id != null && !id.isEmpty()) {
							return buscarItemPorId(id, client, authorization).thenComposeAsync(responseItem -> {
								if (onlyFields) {
									return CompletableFuture.completedFuture(getCustomFields(responseItem));
								}
								return CompletableFuture.completedFuture(responseItem.body());
							});
						} else {
							return CompletableFuture.completedFuture("{}");
						}
					}
					return CompletableFuture.completedFuture("{ \"erro\": \"Falha ao consultar lista\" }");
				});
	}

	@Async//("taskExecutor")
	private CompletableFuture<String> getTelefoneResponsavel(String email, String authorization) {
		return getUsuario(email, authorization).thenComposeAsync(user -> {
			JsonNode telefone;
			try {
//				System.out.println("+++++++++++++++++++++++++GET TELEFONE RESPONSAVEL+++++++++++++++++++++++++");
//				telefone = new JSONObject(user).getString("mobilePhone");
				telefone = objectMapper.readTree(user).path("mobilePhone");
				return CompletableFuture.completedFuture(telefone.asText(null));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return CompletableFuture.completedFuture("");
		});
	}

	@Async//("taskExecutor")
	private CompletableFuture<String> getUsuario(String email, String authorization) {
		String url = "https://graph.microsoft.com/v1.0/users('" + email + "')";
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).header("Authorization", authorization).GET()
				.build();

		HttpClient client = HttpClient.newHttpClient();
		return client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(response -> {
			if (response.statusCode() == 200) {
				return response.body();
			} else {
				System.err.println("Erro: Status code " + response.statusCode() + ", body: " + response.body());
				return "{\"error\": \"Request failed or user invalid\"}";
			}
		}).exceptionally(ex -> {
			ex.printStackTrace();
			return "{\"error\": \"Request failed or user invalid\"}";
		});
//		try {
//			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//			if (response.statusCode() == 200) {
//				return CompletableFuture.completedFuture(response.body());
//			}
//		} catch (IOException | InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		return CompletableFuture.completedFuture("{}");
	}

//	private String getEmailResponsavel(String responsavel) throws JSONException {
//		return new JSONObject(responsavel).getString("Email");
//	}
	
//	private String getEmailResponsavel(String responsavel) throws JSONException {
//	    if (responsavel.trim().startsWith("[")) {
//	        JSONArray array = new JSONArray(responsavel);
//
//			System.out.println("+++++++++++++++++++++++++GET EMAIL RESPONSAVEL IF+++++++++++++++++++++++++");
//	        return array.getJSONObject(0).getString("Email");
//	    } else {
//	    	System.out.println("+++++++++++++++++++++++++GET EMAIL RESPONSAVEL ELSE+++++++++++++++++++++++++");
//	        return new JSONObject(responsavel).getString("Email");
//	    }
//	}
	
	public String getEmailResponsavel(String responsavel) {
	    try {
	        JsonNode node = objectMapper.readTree(responsavel);

	        if (node.isArray()) {
	            return node.get(0).get("Email").asText();
	        } else {
	            return node.get("Email").asText();
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        return "";
	    }
	}


	@Async//("taskExecutor")
	public CompletableFuture<List<String>> buscarResponsaveis(Long idEquipamento, TipoEquipamento tipoEquipamento) {
		return buscarEquipamento(idEquipamento, tipoEquipamento, true).thenComposeAsync(fields -> {
			return CompletableFuture.completedFuture(getResponsaveis(fields));
		});
	}

	@Async//("taskExecutor")
	public CompletableFuture<String> buscarEquipamento(Long idEquipamento, TipoEquipamento tipoEquipamento,
			boolean onlyFields) {
		return tokenGrahp.getToken().thenComposeAsync(token -> {
			switch (tipoEquipamento) {
			case Hidrometro: {
				this.idLista = graphProperties.getListHidrometro();
				break;
			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + tipoEquipamento);
			}
			HttpClient client = HttpClient.newHttpClient();
			String authorization = "Bearer " + token;

			return buscarItensListaPorEquipamentoId(authorization, idEquipamento, client)
					.thenComposeAsync(responseListItens -> {
						if (responseListItens.statusCode() == 200) {
							String id = getIdValue(responseListItens);
							if (id != null && !id.isEmpty()) {
								return buscarItemPorId(id, client, authorization).thenComposeAsync(responseItem -> {
									if (onlyFields) {
										return CompletableFuture.completedFuture(getCustomFields(responseItem));
									}
									return CompletableFuture.completedFuture(responseItem.body());
								});
							} else {
								return CompletableFuture.completedFuture("{}");
							}
						}
						return CompletableFuture.completedFuture("{ \"erro\": \"Falha ao consultar lista\" }");
					});
		});
	}

	@Async//("taskExecutor")
	private CompletableFuture<HttpResponse<String>> buscarItemPorId(String id, HttpClient client,
			String authorization) {// throws
		// InterruptedException {

		String url = "https://graph.microsoft.com/v1.0/sites/" + graphProperties.getSiteMonitoramento() + "/lists/"
				+ idLista + "/items/" + id;

		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).header("Authorization", authorization).GET()
				.build();

//		HttpResponse<String> response = null;

		return client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
//		try {
//			response = client.send(request, HttpResponse.BodyHandlers.ofString());
//			return CompletableFuture.completedFuture(response);
//		} catch (IOException | InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return CompletableFuture.completedFuture(response);
	}

	@Async//("taskExecutor")
	private CompletableFuture<HttpResponse<String>> buscarItensListaPorEquipamentoId(String authorization,
			Long idEquipamento, HttpClient client) {
		String filter = URLEncoder.encode("fields/id_equipamento eq " + idEquipamento, StandardCharsets.UTF_8);
		String url = String.format("https://graph.microsoft.com/v1.0/sites/%s/lists/%s/items/?$filter=%s",
				graphProperties.getSiteMonitoramento(), idLista, filter);

		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).header("Authorization", authorization).GET()
				.build();

		return client.sendAsync(request, HttpResponse.BodyHandlers.ofString());

//		HttpResponse<String> response = null;
//		try {
//			response = client.send(request, HttpResponse.BodyHandlers.ofString());
//			return CompletableFuture.completedFuture(response);
//		} catch (IOException | InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		return CompletableFuture.completedFuture(response);
	}

	public List<String> getResponsaveis(String response) {
		try {
//			System.out.println("+++++++++++++++++++++++++GET RESPONSAVEIS+++++++++++++++++++++++++");
//			JSONObject json = new JSONObject(response);
	        JsonNode json = objectMapper.readTree(response);

			if (!json.has("RESPONSAVEIS") || !json.get("RESPONSAVEIS").isArray()) {
				return Collections.emptyList();
			}

	        List<String> listaResponsaveis = new ArrayList<>();
	        for (JsonNode responsavelNode : json.get("RESPONSAVEIS")) {
	            listaResponsaveis.add(responsavelNode.toString());
	        }
//			JSONArray list = json.getJSONArray("RESPONSAVEIS");
//			List<String> listaResponsaveis = new ArrayList<>();
//			for (int i = 0; i < list.length(); i++) {
////				listaResponsaveis.add(list.getString(i));
//
//	            Object item = list.get(i);
//
//				System.out.println("+++++++++++++++++++++++++GET RESPONSAVEIS INSTANCEOF+++++++++++++++++++++++++");
//	            if (item instanceof JSONObject) {
//	                listaResponsaveis.add(((JSONObject) item).toString());
//	            } else if (item instanceof String) {
//	                listaResponsaveis.add((String) item);
//	            }else {	            	
//					listaResponsaveis.add(list.getString(i));
//	            }
//			}

			return listaResponsaveis;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Collections.emptyList();
	}

	private String getCustomFields(HttpResponse<String> response) {
		try {
//			System.out.println("+++++++++++++++++++++++++GET CUSTOM FILED+++++++++++++++++++++++++");
//			JSONObject fields = deserializarObj(response.body(), "fields");
//			return fields.toString();
			JsonNode fields = deserializarObj(response.body(), "fields");
			return fields != null ? fields.toString() : "{}";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "{}";
	}
	
	private String getIdValue(HttpResponse<String> response) {
	    try {
//	        System.out.println("+++++++++++++++++++++++++GET ID VALUE+++++++++++++++++++++++++");

	        ObjectMapper mapper = new ObjectMapper();
	        JsonNode jsonResponse = mapper.readTree(response.body());
	        JsonNode values = jsonResponse.get("value");

	        if (values != null && values.isArray() && values.size() > 0) {
	            JsonNode first = values.get(0);
	            return first.has("id") ? first.get("id").asText() : null;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}

//	private String getIdValue(HttpResponse<String> response) {
//		try {
//			System.out.println("+++++++++++++++++++++++++GET ID VALUE+++++++++++++++++++++++++");
//			JSONObject jsonResponse = new JSONObject(response.body());
//			JSONArray values = jsonResponse.getJSONArray("value");
//
//			if (values.length() > 0) {
//				return values.getJSONObject(0).getString("id");
//			}
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
//	}

//	private JSONObject deserializarObj(String response, String campo) throws JSONException {
//
//		System.out.println("+++++++++++++++++++++++++DESERIALIZAR OBJ+++++++++++++++++++++++++");
//		return new JSONObject(response).getJSONObject(campo);
//	}
	private JsonNode deserializarObj(String response, String campo) throws JsonProcessingException {

//		System.out.println("+++++++++++++++++++++++++DESERIALIZAR OBJ+++++++++++++++++++++++++");
		return new ObjectMapper().readTree(response).get(campo);
	}
}
