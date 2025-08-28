package com.maqfiltros.sensors_contract.resources;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.maqfiltros.sensors_contract.dto.EquipamentoDTO;
import com.maqfiltros.sensors_contract.entities.Equipamento;
import com.maqfiltros.sensors_contract.services.EquipamentoService;

@RestController
@RequestMapping("/equipamentos")
public class EquipamentoResource {

    private final EquipamentoService equipamentoService;

    public EquipamentoResource(EquipamentoService equipamentoService) {
        this.equipamentoService = equipamentoService;
    }

    @GetMapping("/escola/com-leituras/{escolaId}")
    public ResponseEntity<?> listarPorEscolaComOpcionalELeituras(@PathVariable Long escolaId, @RequestParam(value = "tm", required = false) String tm) {
        List<Equipamento> equipamentos = equipamentoService.buscarPorEscola(escolaId);
        List<EquipamentoDTO> resultado = equipamentos.stream()
                .map(e -> new EquipamentoDTO(e, tm, true))
                .toList();
        return ResponseEntity.ok(resultado);
    }
    
    @GetMapping("/escola/{escolaId}")
    public ResponseEntity<?> listarPorEscola(@PathVariable Long escolaId) {
        List<Equipamento> equipamentos = equipamentoService.buscarPorEscola(escolaId);
        List<EquipamentoDTO> resultado = equipamentos.stream()
                .map(e -> new EquipamentoDTO(e))
                .toList();
        return ResponseEntity.ok(resultado);
    }
}
