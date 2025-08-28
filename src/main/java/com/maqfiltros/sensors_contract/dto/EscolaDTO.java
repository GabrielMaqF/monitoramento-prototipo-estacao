package com.maqfiltros.sensors_contract.dto;

public class EscolaDTO {
	private Long id;
	private String nome;
	private String endereco;

	public EscolaDTO() {
	}

	public EscolaDTO(Long id, String nome, String endereco) {
		this.id = id;
		this.nome = nome;
		this.endereco = endereco;
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getEndereco() {
		return endereco;
	}
}
