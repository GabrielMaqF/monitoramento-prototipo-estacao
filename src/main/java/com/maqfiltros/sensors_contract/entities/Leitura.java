package com.maqfiltros.sensors_contract.entities;

import java.io.Serializable;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.maqfiltros.sensors_contract.resources.exceptions.DatabaseException;
//import com.maqfiltros.sensors_contract.services.exceptions.InvalidValueException;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Leitura implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
	private Instant moment;
	private String valor;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "sensor_id")
	private Sensor sensor;

	public Leitura() {

	}

	public Leitura(Long id, Instant moment, String valor, Sensor sensor) {
		super();
		this.id = id;
		this.moment = moment;
		this.sensor = sensor;
		this.valor = valor;
	}
}
