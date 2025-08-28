package com.maqfiltros.sensors_contract.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

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

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo_equipamento", discriminatorType = DiscriminatorType.STRING) // Adicione esta linha
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "tipoEquipamento")

public abstract class Equipamento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String modelo;
	private String descricao;

//	@JsonInclude(JsonInclude.Include.NON_NULL)
//	@Transient
//	private Integer tipo = 1;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "cliente_id")
	public Cliente cliente;

	@OneToMany(mappedBy = "equipamento") // , cascade = CascadeType.ALL
	@OrderBy("moment ASC")
	private List<Leitura> leituras;

	protected Equipamento() {

	}

	protected Equipamento(Long id, String modelo, String descricao, Cliente cliente) {
		super();
		this.id = id;
//		this.tipo = tipo;
		this.modelo = modelo;
		this.descricao = descricao;
		this.cliente = cliente;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

//	public TipoEquipamento getTipo() {
//		return TipoEquipamento.valueOf(tipo);
//	}
//
//	public void setTipo(Integer tipo) {
//		this.tipo = tipo;
//	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Leitura> getLeituras() {
		return leituras;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Equipamento other = (Equipamento) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Equipamento [id=" + id + ", descricao=" + descricao + ", cliente=" + cliente
				+ ", leituras=" + "]";//tipo=" + tipo + ",
	}
	
	public abstract TipoEquipamento getTipoEquipamento();
	
	public abstract String getUnidadeMedida();

	public abstract boolean leituraRecebida(String res) throws NumberFormatException;

}
