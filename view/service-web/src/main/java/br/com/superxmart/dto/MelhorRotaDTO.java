package br.com.superxmart.dto;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.List;

import br.com.superxmart.entidade.Rota;

public class MelhorRotaDTO {

	private static final String ESPACO = " ";

	private static final NumberFormat DECIMAL_FORMAT = new DecimalFormat("0.00");

	private List<Rota> rotas;
	private BigDecimal custoEstimadoDaRota;
	private String melhorRota;

	protected MelhorRotaDTO() {

	}

	public MelhorRotaDTO(List<Rota> melhorRotaEncontrada, BigDecimal custoEstimadoDaRota) {
		this.rotas = melhorRotaEncontrada;
		this.custoEstimadoDaRota = custoEstimadoDaRota;
		definirTextoMehorRota();
	}

	public void definirTextoMehorRota() {
		StringBuilder sb = new StringBuilder();
		for (int index = 0; index < rotas.size(); index++) {
			Rota rota = rotas.get(index);
			sb.append(rota.getOrigem());
			sb.append(ESPACO);
			if (index + 1 == rotas.size()) {
				sb.append(rota.getDestino());
				sb.append(ESPACO);
			}
		}

		this.melhorRota = MessageFormat.format("Rota {0}com custo de {1}.", sb.toString(), DECIMAL_FORMAT.format(custoEstimadoDaRota));
	}

	public List<Rota> getRotas() {
		return rotas;
	}

	public void setRotas(List<Rota> rotas) {
		this.rotas = rotas;
	}

	public BigDecimal getCustoEstimadoDaRota() {
		return custoEstimadoDaRota;
	}

	public void setCustoEstimadoDaRota(BigDecimal custoEstimadoDaRota) {
		this.custoEstimadoDaRota = custoEstimadoDaRota;
	}

	public String getMelhorRota() {
		return melhorRota;
	}

	public void setMelhorRota(String melhorRota) {
		this.melhorRota = melhorRota;
	}

}
