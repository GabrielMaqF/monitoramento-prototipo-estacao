package com.maqfiltros.sensors_contract.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.maqfiltros.sensors_contract.enums.TipoEquipamento;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

	@Enumerated(EnumType.STRING) // Armazena o nome do enum no banco (ex: "BOMBA_ETE_1")
	@Column(name = "codigo_padrao") // Define o nome da coluna no banco
	private TipoEquipamento tipoEquipamento;

	private String marca, modelo, descricao, tag;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "escola_id")
	private Escola escola;

	@OneToMany(mappedBy = "equipamento", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Sensor> sensores;
}
