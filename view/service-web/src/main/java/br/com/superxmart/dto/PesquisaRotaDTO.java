package br.com.superxmart.dto;

import java.math.BigDecimal;

public class PesquisaRotaDTO {

	private String nomeMapa;

	private String origem;

	private String destino;

	private Integer autonomiaVeiculo;

	private BigDecimal valorLitroCombustivel;

	public String getNomeMapa() {
		return nomeMapa;
	}

	public void setNomeMapa(String nomeMapa) {
		this.nomeMapa = nomeMapa;
	}

	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public Integer getAutonomiaVeiculo() {
		return autonomiaVeiculo;
	}

	public void setAutonomiaVeiculo(Integer autonomiaVeiculo) {
		this.autonomiaVeiculo = autonomiaVeiculo;
	}

	public BigDecimal getValorLitroCombustivel() {
		return valorLitroCombustivel;
	}

	public void setValorLitroCombustivel(BigDecimal valorLitroCombustivel) {
		this.valorLitroCombustivel = valorLitroCombustivel;
	}

}
