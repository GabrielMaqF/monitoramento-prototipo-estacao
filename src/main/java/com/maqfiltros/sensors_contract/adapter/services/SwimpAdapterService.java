package com.maqfiltros.sensors_contract.adapter.services;

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
import com.maqfiltros.sensors_contract.services.SensorService;
import com.maqfiltros.sensors_contract.utils.UidGenerator;

@Service
public class SwimpAdapterService {

	@Autowired
	private RelacaoDadosSwimpRepository repository;

//	@Autowired
//	private EscolaService escolaService;

	@Autowired
	private SensorService sensorService;

	public void insert(Escola e) {
		List<RelacaoDadosSwimp> l = repository.findByIdEscola(e.getId());

		if (l.isEmpty()) {
			RelacaoDadosSwimp rds = new RelacaoDadosSwimp();
			rds.setIdEscola(e.getId());

			repository.save(rds);
		}
	}

	public void insert(Equipamento e) {
		String uidDispositivo;

		List<RelacaoDadosSwimp> l = repository.findByIdEscola(e.getEscola().getId());
		if (l.size() == 0 || l.size() == 3 || l.size() == 5) {
			uidDispositivo = (UidGenerator.generate(16) + "_" + e.getTipoEquipamento().getCodigo());
		}else {
			uidDispositivo = (l.get((l.size()-1)).getIdDispositivo() + "_" + e.getTipoEquipamento().getCodigo());
		}
		
		RelacaoDadosSwimp rds = new RelacaoDadosSwimp();
		rds.setIdEscola(e.getId());
		rds.setIdDispositivo(uidDispositivo);
		rds.setIdEquipamento(e.getTipoEquipamento().getCodigo());

		repository.save(rds);
	}

	public ResponseEntity<Void> inserirLeiturasSwimp(LeituraSwimpDTO dto) {
		Leitura l = new Leitura();

		String idSensor = dto.getDeviceId() + "_" + dto.getRecursoMonitoradoId();
		sensorService.findById(idSensor);

		return null;
	}

}
