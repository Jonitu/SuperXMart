package br.com.superxmart.business;

import br.com.superxmart.dao.RotaDAO;
import br.com.superxmart.dto.MelhorRotaDTO;
import br.com.superxmart.dto.PesquisaRotaDTO;
import br.com.superxmart.entidade.Mapa;
import br.com.superxmart.entidade.Rota;
import br.com.superxmart.exception.MapaNaoEncontradoException;
import br.com.superxmart.exception.NenhumaRotaEncontradaException;

public class RotaBO {

	private RotaDAO dao = new RotaDAO();

	public Mapa cadastrarRota(Mapa pMapa) {
		Mapa mapa = dao.buscarMapaPorNome(pMapa.getNome());

		if (mapa != null) {
			for (Rota rota : pMapa.getRotas()) {
				mapa.addRota(rota);
			}
		} else {
			mapa = pMapa;
		}

		for (Rota rota : mapa.getRotas()) {
			rota.setMapa(mapa);
		}
		return dao.salvarMapa(mapa);
	}

	public MelhorRotaDTO buscarRota(PesquisaRotaDTO pesquisaRota) throws MapaNaoEncontradoException, NenhumaRotaEncontradaException {
		Mapa mapa = dao.buscarMapaPorNome(pesquisaRota.getNomeMapa());

		if (mapa == null) {
			throw new MapaNaoEncontradoException(pesquisaRota.getNomeMapa());
		}

		return new MelhorRota(pesquisaRota, mapa).encontrarMelhorRota();
	}
}
