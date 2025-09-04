package com.maqfiltros.sensors_contract.entities;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Escola implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String endereco;

//	@JsonIgnore
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@OneToMany(mappedBy = "escola") // , cascade = CascadeType.ALL
	private List<Equipamento> equipamentos;

	public Escola(Long id, String nome, String endereco) {
		super();
		this.id = id;
		this.nome = nome;
		this.endereco = endereco;
	}

}
