package com.maqfiltros.sensors_contract.entities;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.maqfiltros.sensors_contract.enums.TipoSensor;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("Hidrometro")
@JsonTypeName("Hidrometro")
@Getter
@Setter
@NoArgsConstructor
public class Hidrometro extends Sensor {
	private static final long serialVersionUID = 1L;

	private byte pulsosPorLitro;
	private Long qntTotalLitros;

	@Column(nullable = false)
	private Long contadorInicialLitros = 0L;

	@Column(nullable = false)
	private Long producaoLitrosAlvoMes = 0L;

	@Column(nullable = false)
	private Double vazaoSistema = 0.0;

	@Column(nullable = false)
	private Double percentualMinimoVazao = 0.7;

	// Construtor personalizado que chama o construtor da superclasse
	public Hidrometro(String modelo, String descricao, Escola escola, byte pulsosPorLitro, Long contadorInicialLitros) {
		super(null, modelo, descricao, escola); // Respeita o DIP
		this.pulsosPorLitro = pulsosPorLitro;
		this.contadorInicialLitros = contadorInicialLitros;
	}

	public Long getQntTotalLitros() {
		long produzida = (qntTotalLitros == null) ? 0L : qntTotalLitros;
		return getContadorInicialLitros() + produzida;
	}

	public long getQntProduzida() {
		return (qntTotalLitros == null) ? 0L : qntTotalLitros;
	}

	@Override
	public boolean leituraRecebida(String res) throws NumberFormatException {
		if (res == null || res.isEmpty()) {
			return false;
		}
		int pulsosRecebidos = Integer.parseInt(res);
		addPulso(pulsosRecebidos);
		return true;
	}

	public void addPulso(int pulsos) {
		long litrosAcumulados = getQntProduzida() + (long) (pulsos * this.getPulsosPorLitro());
		this.setQntTotalLitros(litrosAcumulados);
	}

	@Override
	public TipoSensor getTipoSensor() {
		return TipoSensor.Hidrometro;
	}

	@Override
	public String getUnidadeMedida() {
		return "Litros";
	}
}

/*
 * package com.maqfiltros.sensors_contract.entities;
 * 
 * import com.fasterxml.jackson.annotation.JsonTypeName; import
 * com.maqfiltros.sensors_contract.enums.TipoEquipamento;
 * 
 * import jakarta.persistence.Column; import
 * jakarta.persistence.DiscriminatorValue; import jakarta.persistence.Entity;
 * 
 * @Entity
 * 
 * @DiscriminatorValue("Hidrometro") // Adicione esta linha
 * 
 * @JsonTypeName("Hidrometro") public class Hidrometro extends Equipamento {
 * private static final long serialVersionUID = 1L;
 * 
 * private byte pulsosPorLitro; private Long qntTotalLitros;
 * 
 * @Column(nullable = false) private Long contadorInicialLitros = 0L;
 * 
 * @Column(nullable = false) private Long producaoLitrosAlvoMes = 0L;
 * 
 * @Column(nullable = false) private Double vazaoSistema = 0.0;
 * 
 * @Column(nullable = false) private Double percentualMinimoVazao = 0.7;
 * 
 * // private static Integer tipoEquipamento =
 * TipoEquipamento.Hidrometro.getCode();
 * 
 * public Hidrometro() { // setTipo(tipoEquipamento); }
 * 
 * public Hidrometro(byte pulsosPorLitro, String modelo, String descricao,
 * Escola escola) { super(null, modelo, descricao, escola); //
 * setTipo(tipoEquipamento); this.pulsosPorLitro = pulsosPorLitro; }
 * 
 * public Hidrometro(byte pulsosPorLitro, String modelo, String descricao,
 * Escola escola, long contadorInicialLitros) { super(null, modelo, descricao,
 * escola); // setTipo(tipoEquipamento); this.pulsosPorLitro = pulsosPorLitro;
 * this.setContadorInicialLitros(contadorInicialLitros); }
 * 
 * public Hidrometro(byte pulsosPorLitro, String modelo, String descricao,
 * Escola escola, long contadorInicialLitros, long producaoLitrosAlvoMes, double
 * vazaoSistema) { super(null, modelo, descricao, escola); //
 * setTipo(tipoEquipamento); this.pulsosPorLitro = pulsosPorLitro;
 * this.setContadorInicialLitros(contadorInicialLitros);
 * this.setProducaoLitrosAlvoMes(producaoLitrosAlvoMes);
 * this.setVazaoSistema(vazaoSistema); }
 * 
 * public Double getPercentualMinimoVazao() { return (percentualMinimoVazao ==
 * null) ? 0.7 : percentualMinimoVazao; }
 * 
 * public void setPercentualMinimoVazao(Double percentualMinimoVazao) {
 * this.percentualMinimoVazao = percentualMinimoVazao; }
 * 
 * public Double getVazaoSistema() { return vazaoSistema; }
 * 
 * public void setVazaoSistema(double vazaoSistema) { this.vazaoSistema =
 * vazaoSistema; }
 * 
 * public Long getProducaoLitrosAlvoMes() { return producaoLitrosAlvoMes; }
 * 
 * public void setProducaoLitrosAlvoMes(long producaoLitrosAlvoMes) {
 * this.producaoLitrosAlvoMes = producaoLitrosAlvoMes; }
 * 
 * public long getContadorInicialLitros() { return (contadorInicialLitros ==
 * null) ? 0L : contadorInicialLitros; }
 * 
 * public void setContadorInicialLitros(long contadorInicialLitros) {
 * this.contadorInicialLitros = contadorInicialLitros; }
 * 
 * public byte getPulsosPorLitro() { return pulsosPorLitro; }
 * 
 * public void setPulsosPorLitro(byte pulsosPorLitro) { this.pulsosPorLitro =
 * pulsosPorLitro; }
 * 
 * public Long getQntTotalLitros() { return this.getContadorInicialLitros() +
 * this.getQntProduzida(); }
 * 
 * public long getQntProduzida() { return (qntTotalLitros == null) ? 0L :
 * qntTotalLitros; }
 * 
 * public void setQntTotalLitros(Long qntTotalLitros) { this.qntTotalLitros =
 * qntTotalLitros; }
 * 
 * @Override public String getUnidadeMedida() { return "Litros"; }
 * 
 * @Override public boolean leituraRecebida(String res) throws
 * NumberFormatException { if (res == null || res.isEmpty()) { return false; }
 * 
 * int pulsosRecebidos = Integer.parseInt(res);
 * 
 * // Calcula a quantidade máxima de pulsos esperada em um minuto // Ex: (50
 * L/min * 1000) / (60 seg * 10 pulsos/L) = 83.3 pulsos/seg -> ajuste o //
 * cálculo se necessário // O cálculo original parece converter vazão para
 * pulsos por minuto. // double pulsosMaximosPorMinuto =
 * Math.ceil((this.vazaoSistema * 1000) / (60.0 * this.getPulsosPorLitro())); //
 * // if (pulsosRecebidos >= 0 && (pulsosRecebidos <= pulsosMaximosPorMinuto ||
 * vazaoSistema == 0.0)) { this.addPulso(pulsosRecebidos); // Mude o nome de
 * addLitro para addPulso para maior clareza return true; // } // // return
 * false; }
 * 
 * // Renomeie 'addLitro' para 'addPulso' para refletir o que ele realmente faz
 * public void addPulso(int pulsos) { long litrosAcumulados =
 * this.getQntProduzida() + (pulsos * this.getPulsosPorLitro());
 * this.setQntTotalLitros(litrosAcumulados); // O nome deste setter é confuso,
 * já que ele define os produzidos }
 * 
 * @Override public TipoEquipamento getTipoEquipamento() { return
 * TipoEquipamento.Hidrometro; } }
 */