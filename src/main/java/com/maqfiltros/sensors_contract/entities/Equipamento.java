package com.maqfiltros.sensors_contract.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Equipamento {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String marca, modelo, descricao, tag;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "escola_id")
	private Escola escola;

	@OneToMany(mappedBy = "equipamento", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Sensor> sensores;
}
