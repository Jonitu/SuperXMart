package br.com.superxmart.business;

import java.math.BigDecimal;

import br.com.superxmart.entidade.Mapa;
import br.com.superxmart.entidade.Rota;

public class RotaBusiness {

	private static final String MELHOR_ROTA = "Rota {0} com custo de {1}.";

	public String buscarRota(String nomeMapa, String origem, String destino, Integer autonomia, BigDecimal valorCombustivel) {
		Mapa mapa = getMapa(nomeMapa);
		
		
		
		return MELHOR_ROTA;
	}

	private Mapa getMapa(String nomeMapa) {
		Mapa mapa = new Mapa();
		mapa.setNome("SP");
		mapa.addRota(new Rota());

		mapa.addRota(new Rota("A", "B", 10));
		mapa.addRota(new Rota("B", "D", 15));
		mapa.addRota(new Rota("A", "C", 20));
		mapa.addRota(new Rota("C", "D", 30));
		mapa.addRota(new Rota("B", "E", 50));
		mapa.addRota(new Rota("D", "E", 30));

		return mapa;
	}
}
