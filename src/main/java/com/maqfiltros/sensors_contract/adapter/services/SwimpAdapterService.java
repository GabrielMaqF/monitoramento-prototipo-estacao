package com.maqfiltros.sensors_contract.adapter.services;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.maqfiltros.sensors_contract.adapter.dto.LeituraSwimpDTO;
import com.maqfiltros.sensors_contract.adapter.entities.RelacaoDadosSwimp;
import com.maqfiltros.sensors_contract.adapter.repository.RelacaoDadosSwimpRepository;
import com.maqfiltros.sensors_contract.entities.Equipamento;
import com.maqfiltros.sensors_contract.entities.Escola;
import com.maqfiltros.sensors_contract.entities.Leitura;
import com.maqfiltros.sensors_contract.entities.Sensor;
import com.maqfiltros.sensors_contract.services.LeituraService;
import com.maqfiltros.sensors_contract.services.SensorService;
import com.maqfiltros.sensors_contract.utils.UidGenerator;

@Service
public class SwimpAdapterService {

	@Autowired
	private RelacaoDadosSwimpRepository repository;

	@Autowired
	private LeituraService leituraService;

//	@Autowired
//	private EscolaService escolaService;

	@Autowired
	private SensorService sensorService;

//	public void insert(Escola e) {
//		List<RelacaoDadosSwimp> l = repository.findByIdEscola(e.getId());
//
//		if (l.isEmpty()) {
//			RelacaoDadosSwimp rds = new RelacaoDadosSwimp();
//			rds.setIdEscola(e.getId());
//
//			repository.save(rds);
//		}
//	}

	public void insertSensor(Sensor sensor) {
		Equipamento equipamento = sensor.getEquipamento();
		Escola escola = equipamento.getEscola();

		String uidDispositivo;

		List<RelacaoDadosSwimp> l = repository.findByIdEscola(escola.getId());
		if (l.size() == 0 || l.size() == 3 || l.size() == 5) {
			uidDispositivo = UidGenerator.generate(16);
		} else {
			uidDispositivo = l.get((l.size() - 1)).getIdDispositivo();
		}

		RelacaoDadosSwimp rds = new RelacaoDadosSwimp(escola.getId(), equipamento.getTipoEquipamento().getCodigo(),
				sensor.getCodigoExterno(), uidDispositivo);

		repository.save(rds);
	}

	public ResponseEntity<Void> inserirLeiturasSwimp(LeituraSwimpDTO dto) {
		Leitura l = new Leitura();

		String idSwimp = dto.getDeviceId() + "_" + dto.getRecursoMonitoradoId();
		RelacaoDadosSwimp rds = repository.findByIdSwimp(idSwimp);

		Sensor sensor = sensorService.findByCodigoExterno(rds.getIdSensor());

		l.setSensor(sensor);
		l.setValor((String.valueOf(dto.getMedicaoCorrente())));
		l.setMoment(Instant.ofEpochSecond(dto.getTimestampMedicao()));
		
		leituraService.insert(l);
		
		return null;
	}

}
