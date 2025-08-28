//package com.maqfiltros.sensors_contract.services.acompanhamento.continuidade;
//
//import java.time.Duration;
//import java.time.Instant;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Async;
//
//import com.maqfiltros.sensors_contract.dto.leitura.LeituraPorMinutoDTO;
//import com.maqfiltros.sensors_contract.entities.Equipamento;
//import com.maqfiltros.sensors_contract.entities.Hidrometro;
//import com.maqfiltros.sensors_contract.enums.TipoEquipamento;
//import com.maqfiltros.sensors_contract.interfaces.consultas.EquipamentoResumido;
//import com.maqfiltros.sensors_contract.services.EquipamentoService;
//import com.maqfiltros.sensors_contract.services.LeituraService;
//import com.maqfiltros.sensors_contract.services.acompanhamento.AcompanhamentoService;
//import com.maqfiltros.sensors_contract.services.generic.EquipamentoServiceFactory;
//import com.maqfiltros.sensors_contract.services.generic.EquipamentoServiceGeneric;
//
////@Service
////@EnableAsync
//public class AcompanhamentoLeituraContinua {
//
//	@Autowired
//	private AcompanhamentoService acompanhamentoService;
//
//	@Autowired
//	private LeituraService leituraService;
//
//	@Autowired
//	private EquipamentoService equipamentoService;
////	private EquipamentoServiceGeneric equipamentoService;
//	@Autowired
//	private EquipamentoServiceFactory serviceFactory;
//
////	public void verificarLeituraContinua(Equipamento equipamentoCompleto) {
////		EquipamentoDTO equipamento = new EquipamentoDTO(equipamentoCompleto, "min");
////
////		String msg = ("Tamanho: " + equipamento.getLeituras().size());
////		logger.info(msg);
////
////		switch (equipamento.getTipo()) {
////		case Hidrometro: {
//////			acompanhamentoHidrometroAoTerLeitura(equipamento);
////			break;
////		}
////		default:
////			throw new IllegalArgumentException("Unexpected value: " + equipamento.getTipo());
////		}
////
////	}
//
//	/*
//	 * @Async // ("taskExecutor") public void verificarEquipamentosAgendado(int
//	 * periodo, String unidade) { List<EquipamentoResumido> equipamentos =
//	 * equipamentoService.findEquipamentoResumido();
//	 * equipamentos.forEach(equipamento -> { //
//	 * Hibernate.initialize(equipamento.getLeituras());
//	 * System.out.println(equipamento); verificarLeituraAgendada(equipamento, 2,
//	 * "minutes"); }); }
//	 */
//
////    @Async
////    public void verificarEquipamentosAgendado(int periodo, String unidade) {
////        // 3. USE O FACTORY PARA OBTER UM SERVIÇO QUALQUER
////        // Como 'findEquipamentoResumido' busca na tabela base 'Equipamento',
////        // qualquer um dos serviços filhos (HidrometroService, etc.) pode executar a query.
//////        EquipamentoServiceGeneric<?, ?> anyService = serviceFactory.getService(TipoEquipamento.Hidrometro);
//////        List<EquipamentoResumido> equipamentos = anyService.findEquipamentoResumido();
////    	List<EquipamentoResumido> equipamentos = equipamentoService.findAll().stream().forEach(e -> new EquipamentoResumido(e));
////
////        equipamentos.forEach(equipamento -> {
////            verificarLeituraAgendada(equipamento, 2, "minutes");
////        });
////    }
//
//	@Async
//	public void verificarEquipamentosAgendado(int periodo, String unidade) {
//		// Usa o EquipamentoService para buscar todos os resumos
//		List<EquipamentoResumido> equipamentos = equipamentoService.findEquipamentoResumido();
//
//		equipamentos.forEach(equipamento -> {
//			verificarLeituraAgendada(equipamento, periodo, unidade);
//		});
//	}
//
//	public void verificarLeituraAgendada(EquipamentoResumido equipamentoResumido, Integer periodo, String unidade) {
//		TipoEquipamento tipo = TipoEquipamento.valueOf(equipamentoResumido.getTipo());
//
//		// Usa o Factory para obter o serviço ESPECÍFICO do tipo
//		EquipamentoServiceGeneric<?, ?> service = serviceFactory.getService(tipo);
//		Equipamento equipamento = service.findById(equipamentoResumido.getId());
//
//		switch (tipo) {
//		case Hidrometro: {
//			acompanhamentoHidrometroAoTerLeitura((Hidrometro) equipamento, periodo, unidade);
//			break;
//		}
//		case SensorNivel: {
//			System.out.println("Verificando acompanhamento para SensorNivel ID: " + equipamento.getId());
//			// Aqui entraria a sua lógica para o Sensor de Nível
//			break;
//		}
//		default:
//			throw new IllegalArgumentException("Lógica de acompanhamento não implementada para o tipo: " + tipo);
//		}
//	}
////	public void verificarLeituraAgendada(EquipamentoResumido equipamentoResumido, Integer periodo, String unidade) {
////		TipoEquipamento tipo = TipoEquipamento.valueOf(equipamentoResumido.getTipo());
////
////		// 4. USE O FACTORY PARA OBTER O SERVIÇO ESPECÍFICO
////		EquipamentoServiceGeneric<?, ?> service = serviceFactory.getService(tipo);
////		Equipamento equipamento = service.findById(equipamentoResumido.getId());
////
////		switch (tipo) {
////		case Hidrometro: {
////			acompanhamentoHidrometroAoTerLeitura((Hidrometro) equipamento, periodo, unidade);
////			break;
////		}
////		case SensorNivel: {
////			// Aqui você deve colocar a lógica de acompanhamento para o SensorNivel.
////			// Por exemplo, chamar um novo método:
////			// acompanhamentoSensorNivel((SensorNivel) equipamento, periodo, unidade);
////
////			// Por enquanto, vamos apenas registrar que ele foi encontrado
////			System.out.println("Verificando acompanhamento para SensorNivel ID: " + equipamento.getId());
////			break; // Não esqueça o break!
////		}
////		default:
////			throw new IllegalArgumentException("Unexpected value: " + tipo);
////		}
////	}
//
//	/*
//	 * // @Async("taskExecutor") public void
//	 * verificarLeituraAgendada(EquipamentoResumido equipamentoResumido, Integer
//	 * periodo, String unidade) {
//	 * 
//	 * // String msg = ("Tamanho: " + equipamento.getLeituras().size()); //
//	 * logger.info(msg);
//	 * 
//	 * switch (TipoEquipamento.valueOf(equipamentoResumido.getTipo())) { case
//	 * Hidrometro: { Hidrometro equipamento = (Hidrometro)
//	 * equipamentoService.findById(equipamentoResumido.getId());
//	 * acompanhamentoHidrometroAoTerLeitura((Hidrometro) equipamento, periodo,
//	 * unidade); break; } default: throw new
//	 * IllegalArgumentException("Unexpected value: " +
//	 * equipamentoResumido.getTipo()); }
//	 * 
//	 * }
//	 */
//	private void acompanhamentoHidrometroAoTerLeitura(Hidrometro hidrometro, Integer periodoMedido, String unidade) {
////		System.out.println(
////				"=========================================ENTREIIIIII=========================================");
////		System.out.println(
////				"=========================================ENTREIIIIII=========================================");
////		System.out.println(
////				"=========================================ENTREIIIIII=========================================");
//		String intervalo = (periodoMedido + " " + unidade);
//
//		List<LeituraPorMinutoDTO> leituraCompleta = leituraService
//				.obterLeiturasTratadasUltimoPeriodo(hidrometro.getId(), intervalo);
//
//		int tamanho = leituraCompleta.size();
//
////		System.out.println("================================================================================");
////		System.out.println("================================================================================");
////		System.out.println("Tamanho: " + tamanho);
////		System.out.println("Periodo: " + (periodoMedido));
////		System.out.println("================================================================================");
////		System.out.println("================================================================================");
//		if (tamanho == 0)
//			return; // 30
//
//		List<LeituraPorMinutoDTO> leituras = new ArrayList<>();
//
//		for (int i = (tamanho - 1); i > 0; i--) {
//			Instant atual = leituraCompleta.get(i).getMoment();
//			Instant anterior = leituraCompleta.get(i - 1).getMoment();
//
////			System.out.println("================================================================================");
////			System.out.println("================================================================================");
////			String msg = "\nAtual:\t" + atual + "\nProx.:\t" + anterior;
////			System.out.println(msg);
////			System.out.println(i);
////			System.out.println("================================================================================");
////			System.out.println("================================================================================");
//
//			long diffEmMinutos = Duration.between(anterior, atual).toMinutes();
//
//			if (diffEmMinutos > 1) {
////				String msg2 = ("Intervalo maior que 1 minuto entre " + atual + " e " + anterior);
////				logger.info(msg2);
//				return;
//			}
//			leituras.add(leituraCompleta.get(i));
//		}
//		leituras.add(leituraCompleta.get(0));
//
//		Double totalLido = 0.0;
//		for (LeituraPorMinutoDTO lt : leituras) {
//			totalLido += lt.getValor();
//		}
//
//		Double vazao = (totalLido * 60) / tamanho;
//		Double vazaoMinima = hidrometro.getVazaoSistema() * hidrometro.getPercentualMinimoVazao();
////		System.out.println("================================================================================");
////		System.out.println("================================================================================");
////		System.out.println("Qnt leitura: " + leituras.size());
////		System.out.println("Total lido: " + totalLido);
////		System.out.println("Vazao: " + vazao);
////		System.out.println("VazaoMinima: " + vazaoMinima);
////		System.out.println("VazaoSistema: " + hidrometro.getVazaoSistema());
////		System.out.println("Percentual: " + hidrometro.getPercentualMinimoVazao());
////		System.out.println("Equipamento com 30 minutos de leitura contínua!");
////		System.out.println("================================================================================");
////		System.out.println("================================================================================");
//
//		if (vazao < vazaoMinima) {
//			String situacao = "RUIM";
//
//			if (vazao < (vazaoMinima * 0.5)) {
//				situacao = "CRÍTICA";
//			}
//			acompanhamentoService.enviarAlertaBaixaVazao(hidrometro, situacao)
//					.thenAccept(respostas -> respostas.forEach(resp ->
////								System.out.println("Resposta WhatsApp: " + resp)
//					System.out.println("Alerta enviado"))).exceptionally(ex -> {
//						System.out.println("Erro ao enviar alerta de baixa vazão" + ex);
//						return null;
//					});
//
//		}
//	}
//
//}
