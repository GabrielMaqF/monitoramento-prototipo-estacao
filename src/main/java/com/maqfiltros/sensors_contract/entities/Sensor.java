package com.maqfiltros.sensors_contract.entities;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.maqfiltros.sensors_contract.enums.TipoSensor;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo_sensor", discriminatorType = DiscriminatorType.STRING) // Adicione esta linha
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "tipoSensor")
@Getter
@Setter
@ToString(exclude = { "equipamento", "leituras" }) // Evita recursão no toString
@EqualsAndHashCode(callSuper = false, of = { "id" }) // Compara entidades apenas pelo ID
@NoArgsConstructor
public abstract class Sensor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String descricao;

//	@JsonIgnore
//	@ManyToOne
//	@JoinColumn(name = "escola_id")
//	public Escola escola;
	
	@Column(unique = true)
	private String codigoExterno;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY) // Usar LAZY é uma boa prática de performance
	@JoinColumn(name = "equipamento_id")
	private Equipamento equipamento;

	@OneToMany(mappedBy = "sensor") // , cascade = CascadeType.ALL
	@OrderBy("moment ASC")
	private List<Leitura> leituras;

//	protected Sensor(Long id, String modelo, String descricao, Escola escola) {
//		super();
//		this.id = id;
////		this.tipo = tipo;
//		this.modelo = modelo;
//		this.descricao = descricao;
//		this.escola = escola;
//	}
//	protected Sensor(Long id, String marca, String modelo, String descricao, Escola escola) {
//		super();
//		this.id = id;
	//// this.tipo = tipo;
//		this.marca = marca;
//		this.modelo = modelo;
//		this.descricao = descricao;
//		this.escola = escola;
//	}
	protected Sensor(Long id, String descricao, Equipamento equipamento) {// , Escola escola
//		super();
		this.id = id;
		this.descricao = descricao;
		this.equipamento = equipamento;
//		this.escola = escola;
	}

	@JsonIgnore
	public abstract TipoSensor getTipoSensor();

	public abstract String getUnidadeMedida();

	public abstract boolean leituraRecebida(String res) throws NumberFormatException;

}
