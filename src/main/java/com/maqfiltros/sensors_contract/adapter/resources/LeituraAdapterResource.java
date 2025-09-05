package com.maqfiltros.sensors_contract.adapter.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maqfiltros.sensors_contract.adapter.dto.LeituraSwimpDTO;
import com.maqfiltros.sensors_contract.adapter.services.LeituraAdapterService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/integracao")
@RequiredArgsConstructor
public class LeituraAdapterResource {

	private final LeituraAdapterService service;

	@PostMapping("/leituras")
	public ResponseEntity<Void> inserirLeiturasSwimp(@RequestBody LeituraSwimpDTO dto) {
		return service.inserirLeiturasSwimp(dto);

	}
}
