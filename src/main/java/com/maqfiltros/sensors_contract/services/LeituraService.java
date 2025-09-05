package com.maqfiltros.sensors_contract.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.maqfiltros.sensors_contract.dto.leitura.LeituraPorMinutoDTO;
//import com.maqfiltros.sensors_contract.entities.Hidrometro;
import com.maqfiltros.sensors_contract.entities.Leitura;
import com.maqfiltros.sensors_contract.entities.Sensor;
import com.maqfiltros.sensors_contract.repositorys.LeituraRepository;
import com.maqfiltros.sensors_contract.resources.exceptions.DatabaseException;
import com.maqfiltros.sensors_contract.services.exceptions.InvalidValueException;
import com.maqfiltros.sensors_contract.services.generic.SensorServiceFactory;
import com.maqfiltros.sensors_contract.services.generic.SensorServiceGeneric;

@Service
public class LeituraService {

	/*
	 * @Autowired //-----------------------------TESTAR DEPOSI---------------
	 * private LeituraRepository repository;
	 * 
	 * @Autowired private EquipamentoServiceFactory serviceFactory;
	 */

	private final LeituraRepository repository;
	private final SensorServiceFactory serviceFactory;

//	@Autowired
	public LeituraService(LeituraRepository repository, SensorServiceFactory serviceFactory) {
		this.repository = repository;
		this.serviceFactory = serviceFactory;
	}

//	@Autowired
//	private EquipamentoServiceGeneric equipamentoServiceGeneric;
//
//	@Autowired
//	private EquipamentoService equipamentoService;

	public List<LeituraPorMinutoDTO> obterLeiturasPorMes(Long idSensor, String tm, String mesParam) {
		// Timezone de São Paulo
		ZoneId zoneId = ZoneId.of("America/Sao_Paulo");

		// Obtém o ano e mês com base no timezone local
		LocalDate agoraSP = LocalDate.now(zoneId);

		// Mês vigente por padrão
		YearMonth anoMes;
		if (mesParam == null) {
//	        anoMes = YearMonth.now(); // mês atual
			anoMes = YearMonth.from(agoraSP);
		} else {
//	        int mes = Integer.parseInt(mesParam);
//	        int ano = LocalDate.now().getYear(); // pode ajustar se quiser aceitar ano também
//	        anoMes = YearMonth.of(ano, mes);
			int mes = Integer.parseInt(mesParam);
			int ano = agoraSP.getYear(); // Pode ser customizado futuramente
			anoMes = YearMonth.of(ano, mes);
		}

//	    Instant inicio = anoMes.atDay(1).atStartOfDay().toInstant(ZoneOffset.UTC);
//	    Instant fim = anoMes.plusMonths(1).atDay(1).atStartOfDay().toInstant(ZoneOffset.UTC);

		Instant inicio = anoMes.atDay(1).atStartOfDay(zoneId).toInstant();

		Instant fim = anoMes.plusMonths(1).atDay(1).atStartOfDay(zoneId).toInstant();

		List<LeituraPorMinutoDTO> leituras;
		if ("dia".equalsIgnoreCase(tm)) {
//			return repository.buscarLeiturasAgrupadasPorDia(idEquipamento, inicio, fim);
			leituras = repository.buscarLeiturasAgrupadasPorDia(idSensor, inicio, fim);
		} else {
//			return repository.buscarLeiturasAgrupadasPorMinuto(idEquipamento, inicio, fim);
			leituras = repository.buscarLeiturasAgrupadasPorMinuto(idSensor, inicio, fim);
		}

		return leituras;
	}

	public List<LeituraPorMinutoDTO> obterLeiturasPorPeriodo(Long idSensor, String dataBR) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate data = LocalDate.parse(dataBR, formatter);

		Instant inicio = data.atStartOfDay().toInstant(ZoneOffset.UTC);
		Instant fim = data.plusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC);

		return repository.buscarLeiturasPorDia(idSensor, inicio, fim);
	}

	public List<LeituraPorMinutoDTO> obterLeiturasPorPeriodo(Long idSensor, String dataInicio, String dataFim) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate dtInicio = LocalDate.parse(dataInicio, formatter);
		LocalDate dtFim = LocalDate.parse(dataFim, formatter);

		ZoneId zonaBrasil = ZoneId.of("America/Bahia"); // ou America/Sao_Paulo se for horário oficial
		Instant inicio = dtInicio.atStartOfDay(zonaBrasil).toInstant();
		Instant fim = dtFim.atStartOfDay(zonaBrasil).toInstant();

		return repository.buscarLeiturasPorDia(idSensor, inicio, fim);
	}

	public List<LeituraPorMinutoDTO> obterLeiturasTratadasUltimoPeriodo(Long idSensor, String periodo) {
		return repository.buscarLeiturasHidrometroPorIntervalo(idSensor, periodo);
	}

	public List<LeituraPorMinutoDTO> obterLeiturasTratadas(Long idSensor) {
		return repository.buscarLeiturasTratadas(idSensor);
	}

	public List<Leitura> findAll() {
		return repository.findAll();
	}

	public Leitura findById(Long id) {
		Optional<Leitura> obj = repository.findById(id);
		return obj.get();
	}

	public Leitura findByIdSensor(Long id) {
		Optional<Leitura> obj = repository.findById(id);
		return obj.get();
	}

	public Leitura insert(Leitura obj) {
		try {
//			boolean persistir = false;
//			persistir = obj.getEquipamento().leituraRecebida(obj.getValor());

			Sensor sensor = obj.getSensor();
			boolean persistir = sensor.leituraRecebida(obj.getValor());

//			if (persistir) {
				// 1. Usa o Factory para obter o serviço correto (HidrometroService ou
				// SensorNivelService)
				SensorServiceGeneric<Sensor, ?> service = (SensorServiceGeneric<Sensor, ?>) serviceFactory
						.getService(sensor.getTipoSensor());

				// 2. Chama o método de atualização no serviço específico que foi encontrado
				service.updateLeitura(sensor.getId(), sensor);

				// 3. Salva a leitura no banco de dados
				return repository.save(obj);
//				equipamentoServiceGeneric.updateLeitura(obj.getEquipamento().getId(), obj.getEquipamento());
//
//				Equipamento equipamentoCompleto = equipamentoService.findByIdComLeituras(obj.getEquipamento().getId());
////				acompanhamento.verificarLeituraContinua(equipamentoCompleto);
//
//				return repository.save(obj);
//			}
		} catch (NumberFormatException e) {
			throw new InvalidValueException(obj.getValor());
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
//		return obj;
	}

}
