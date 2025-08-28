package com.maqfiltros.sensors_contract.services.acompanhamento.verificacoes;

import org.springframework.beans.factory.annotation.Autowired;

import com.maqfiltros.sensors_contract.services.acompanhamento.continuidade.AcompanhamentoLeituraContinua;

//@Service
public class VerificacaoAgendada{

//    @Autowired
//    private EquipamentoServiceGeneric equipamentoService;

    @Autowired
    private AcompanhamentoLeituraContinua acompanhamento;

//	@Scheduled(cron = "0 0/30 * * * *")
    public void verificarTodosOsEquipamentos() {
//    	System.out.println("++++++++++++++++++++++++++    ENTREI    ++++++++++++++++++++++++++");
    	acompanhamento.verificarEquipamentosAgendado(30, "minutes");
    }
}
