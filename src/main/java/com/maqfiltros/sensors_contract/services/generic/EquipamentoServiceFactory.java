package com.maqfiltros.sensors_contract.services.generic;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.maqfiltros.sensors_contract.enums.TipoEquipamento;

@Component
public class EquipamentoServiceFactory {

    private final Map<TipoEquipamento, EquipamentoServiceGeneric<?, ?>> serviceMap;

    /**
     * O Spring injeta automaticamente uma lista de todos os beans que herdam
     * de EquipamentoServiceGeneric (ou seja, HidrometroService, SensorNivelService, etc).
     */
//    @Autowired
    public EquipamentoServiceFactory(List<EquipamentoServiceGeneric<?, ?>> services) {
        // Mapeia cada serviço ao seu tipo de equipamento correspondente
        this.serviceMap = services.stream()
                .collect(Collectors.toMap(EquipamentoServiceGeneric::getTipoEquipamento, Function.identity()));
    }

    /**
     * Retorna o serviço específico para o tipo de equipamento fornecido.
     */
    public EquipamentoServiceGeneric<?, ?> getService(TipoEquipamento tipo) {
        EquipamentoServiceGeneric<?, ?> service = serviceMap.get(tipo);
        if (service == null) {
            throw new IllegalArgumentException("Nenhum serviço implementado para o tipo: " + tipo);
        }
        return service;
    }
}