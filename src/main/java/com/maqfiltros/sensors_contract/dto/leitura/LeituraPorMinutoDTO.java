package com.maqfiltros.sensors_contract.dto.leitura;

import java.time.Instant;

public class LeituraPorMinutoDTO {
    private Instant moment;
    private Double valor;

    public LeituraPorMinutoDTO(Instant moment, Double valor) {
        this.moment = moment;
        this.valor = valor;
    }

    public Instant getMoment() {
        return moment;
    }

    public Double getValor() {
        return valor;
    }
}
