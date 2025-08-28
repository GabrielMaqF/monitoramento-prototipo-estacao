package com.maqfiltros.sensors_contract.entities;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.maqfiltros.sensors_contract.enums.TipoEquipamento;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo_equipamento", discriminatorType = DiscriminatorType.STRING) // Adicione esta linha
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "tipoEquipamento")
@Data
@NoArgsConstructor
public abstract class Equipamento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String modelo;
	private String descricao;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "escola_id")
	public Escola escola;

	@OneToMany(mappedBy = "equipamento") // , cascade = CascadeType.ALL
	@OrderBy("moment ASC")
	private List<Leitura> leituras;


	protected Equipamento(Long id, String modelo, String descricao, Escola escola) {
		super();
		this.id = id;
//		this.tipo = tipo;
		this.modelo = modelo;
		this.descricao = descricao;
		this.escola = escola;
	}
	
	public abstract TipoEquipamento getTipoEquipamento();

	public abstract String getUnidadeMedida();

	public abstract boolean leituraRecebida(String res) throws NumberFormatException;

}
