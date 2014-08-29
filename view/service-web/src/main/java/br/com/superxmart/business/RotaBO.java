package br.com.superxmart.business;

import br.com.superxmart.dao.RotaDAO;
import br.com.superxmart.dto.PesquisaRotaDTO;
import br.com.superxmart.entidade.Mapa;
import br.com.superxmart.entidade.Rota;

public class RotaBO {

	private RotaDAO dao = new RotaDAO();

	private static final String MELHOR_ROTA = "Rota {0} com custo de {1}.";

	public Mapa cadastrarRota(Mapa pMapa) {
		for (Rota rota : pMapa.getRotas()) {
			rota.setMapa(pMapa);
		}
		Mapa mapa = dao.salvarMapa(pMapa);
		return mapa;
	}

	public Mapa buscarRota(PesquisaRotaDTO pesquisaRota) {
		Mapa mapa = dao.buscarMapaPorNome(pesquisaRota.getNomeMapa());

		return mapa;
	}
}
