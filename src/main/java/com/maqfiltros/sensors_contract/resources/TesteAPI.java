//package com.maqfiltros.sensors_contract.resources;
//
//import java.util.List;
//import java.util.concurrent.CompletableFuture;
//import java.util.stream.Collectors;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.EnableAsync;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.maqfiltros.sensors_contract.enums.TipoEquipamento;
//import com.maqfiltros.sensors_contract.services.microsoft.SharepointService;
//import com.maqfiltros.sensors_contract.services.microsoft.tokens.TokenMicrosoftGraph;
//import com.maqfiltros.sensors_contract.services.whatsapp.WhatsappService;
//
//@RestController
//@EnableAsync
//public class TesteAPI {
//
//	@Autowired
//	private SharepointService sharepointService;
//
//	@Autowired
//	private WhatsappService whatsappService;
//
//	@Autowired
//	private TokenMicrosoftGraph tokenMicrosoftGraph;
//	
////	@PostMapping("/teste-telefone/{email}/{auth}")
////	public CompletableFuture<String> testarTelefoneResponsavel(@PathVariable String email, @PathVariable String auth) {
////	    return sharepointService.getTelefoneResponsavel(email, ("Bearer " + auth))
////	        .thenApply(telefone -> "Telefone retornado: " + telefone);
////	}
//
//	@PostMapping("/teste-responsaveis")
//	public List<String> testarResponsavel(@RequestBody String jsonResponsavel) {
//		return sharepointService.getResponsaveis(jsonResponsavel);
//	}
//
//	@PostMapping("/teste-email")
//	public String testarEmailResponsavel(@RequestBody String jsonResponsavel) {
//	    return sharepointService.getEmailResponsavel(jsonResponsavel);
//	}
//	
//	@GetMapping("/teste-token")
//	public String testarGeracaoToken() {
//		try {
//			long start = System.currentTimeMillis(); // Marca o início
//
//			CompletableFuture<String> tokenFuture = tokenMicrosoftGraph.getToken();
//			String token = tokenFuture.get(); // Espera o token ser gerado
//
//			long end = System.currentTimeMillis(); // Marca o fim
//			long duration = end - start; // Calcula duração
//
//			return "Token gerado: " + token + "\nTempo de execução: " + duration + " ms";
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "Erro ao gerar token: " + e.getMessage();
//		}
//	}
//
//	@GetMapping("/equipamento/{idEquipamento}")
//	public CompletableFuture<String> buscarEquipamento(@PathVariable Long idEquipamento) {
//		TipoEquipamento tipoEquipamento = TipoEquipamento.Hidrometro;
//
//		return sharepointService.buscarEquipamento(idEquipamento, tipoEquipamento, true);
//	}
//
//	@GetMapping("/responsaveis/{idEquipamento}")
//	public CompletableFuture<List<String>> buscarResponsaveis(@PathVariable Long idEquipamento) {
//		TipoEquipamento tipoEquipamento = TipoEquipamento.Hidrometro;
//
//		return sharepointService.buscarResponsaveis(idEquipamento, tipoEquipamento);
//	}
//
//	@GetMapping("/lista-telefonica/{idEquipamento}")
//	public CompletableFuture<List<String>> listaTelefonica(@PathVariable Long idEquipamento) {
//		TipoEquipamento tipoEquipamento = TipoEquipamento.Hidrometro;
//
//		return sharepointService.telefoneResponsaveis(idEquipamento, tipoEquipamento);
//	}
//
//	@GetMapping("/ping")
//	public String ping() {
//		return "pong";
//	}
//
//	@GetMapping("/enviar-zap/{idEquipamento}")
//	public CompletableFuture<List<String>> enviarZap(@PathVariable Long idEquipamento) {
//		TipoEquipamento tipoEquipamento = TipoEquipamento.Hidrometro;
//
//		return sharepointService.telefoneResponsaveis(idEquipamento, tipoEquipamento).thenComposeAsync(telefones -> {
//			List<CompletableFuture<String>> envios = telefones.stream()
//					.map(t -> whatsappService.sendAlertBaixaVazao(t, "GABRIEL", "RUIM")).collect(Collectors.toList());
//
//			// Espera todos os envios completarem
//			return CompletableFuture.allOf(envios.toArray(new CompletableFuture[0]))
//					.thenApply(v -> envios.stream().map(CompletableFuture::join) // agora é seguro dar join, pois todos
//																					// completaram
//							.collect(Collectors.toList()));
//		});
//	}
//}
