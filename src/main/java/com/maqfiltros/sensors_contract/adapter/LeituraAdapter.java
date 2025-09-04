package com.maqfiltros.sensors_contract.adapter;

import com.maqfiltros.sensors_contract.entities.Leitura;

public interface LeituraAdapter<T> {

	Leitura adapter(T dto);

}
