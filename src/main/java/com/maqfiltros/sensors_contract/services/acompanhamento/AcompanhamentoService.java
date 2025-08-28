//package com.maqfiltros.sensors_contract.services.acompanhamento;
//
//import java.util.List;
//import java.util.concurrent.CompletableFuture;
//import java.util.stream.Collectors;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Async;
//
//import com.maqfiltros.sensors_contract.entities.Equipamento;
//import com.maqfiltros.sensors_contract.enums.TipoEquipamento;
//import com.maqfiltros.sensors_contract.services.microsoft.SharepointService;
//import com.maqfiltros.sensors_contract.services.whatsapp.WhatsappService;
//
////@Service
////@EnableAsync
//public class AcompanhamentoService {
//
////	private final SharepointService sharepointService;
////	private final WhatsappService whatsappService;
////
////	public AcompanhamentoService(SharepointService sharepointService, WhatsappService whatsappService) {
////		this.sharepointService = sharepointService;
////		this.whatsappService = whatsappService;
////	}
//
//	@Autowired
//	private SharepointService sharepointService;
//
//	@Autowired
//	private WhatsappService whatsappService;
//
//	@Async
//	public CompletableFuture<List<String>> enviarAlertaBaixaVazao(Equipamento equipamento, String situacao) {
//		Long idEquipamento = equipamento.getId();
//		String nomeCliente = equipamento.getEscola().getNome();
//		TipoEquipamento tipoEquipamento = equipamento.getTipoEquipamento();
//
//		return sharepointService.telefoneResponsaveis(idEquipamento, tipoEquipamento).thenComposeAsync(telefones -> {
//			List<CompletableFuture<String>> envios = telefones.stream()
//					.map(telefone -> whatsappService.sendAlertBaixaVazao(telefone, nomeCliente, situacao))
//					.collect(Collectors.toList());
//
//			return CompletableFuture.allOf(envios.toArray(new CompletableFuture[0]))
//					.thenApply(v -> envios.stream().map(CompletableFuture::join).collect(Collectors.toList()));
//		});
//	}
//}
