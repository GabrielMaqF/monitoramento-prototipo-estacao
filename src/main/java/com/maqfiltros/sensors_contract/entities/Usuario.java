package com.maqfiltros.sensors_contract.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String uidFirebase;

	@Column(nullable = false)
	private String username, password;
	
	@ManyToOne
	@JoinColumn(name = "escola_id") // Boa prática para definir o nome da coluna de chave estrangeira
	private Escola escola;

	@Column(nullable = false)
	private boolean ativo;
}
