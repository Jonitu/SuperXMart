package br.com.superxmart.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.superxmart.dto.MelhorRotaDTO;
import br.com.superxmart.dto.PesquisaRotaDTO;
import br.com.superxmart.entidade.Mapa;
import br.com.superxmart.entidade.Rota;

/**
 * 
 * @author Jonathas Lopes de Souza
 * 
 *         Classe responsavel por encontrar a melhor Rota
 *
 */
public class MelhorRota {

	/**
	 * Lista de possiveis rotas encontradas a partir do ponto de origem
	 */
	private List<List<Rota>> rotasEncontradas = new ArrayList<List<Rota>>();

	private PesquisaRotaDTO pesquisaRotaDTO;

	private Mapa mapa;

	public MelhorRota(PesquisaRotaDTO pesquisaRotaDTO, Mapa mapa) {
		this.pesquisaRotaDTO = pesquisaRotaDTO;
		this.mapa = mapa;
	}

	public MelhorRotaDTO encontrarMelhorRota() {

		procurarRotas();

		List<Rota> melhorRotaEncontrada = null;

		Integer distanciaMenorRotaEncontrada = null;

		for (List<Rota> list : rotasEncontradas) {
			int index = 1;
			Integer distanciaDestaRota = 0;
			for (Rota rota : list) {
				distanciaDestaRota += rota.getDistancia();
				if (index == list.size()) {
					if (ehODestinoPesquisado(rota)) {
						if (distanciaMenorRotaEncontrada == null || distanciaDestaRota < distanciaMenorRotaEncontrada
								|| (distanciaDestaRota == distanciaMenorRotaEncontrada && list.size() < melhorRotaEncontrada.size())) {
							distanciaMenorRotaEncontrada = distanciaDestaRota;
							melhorRotaEncontrada = list;
						}
					}
				}
				index++;
			}
		}

		BigDecimal consumoEstimado = new BigDecimal(distanciaMenorRotaEncontrada).divide(new BigDecimal(pesquisaRotaDTO
				.getAutonomiaVeiculo()));

		BigDecimal custoEstimadoDaRota = consumoEstimado.multiply(pesquisaRotaDTO.getValorLitroCombustivel());

		return new MelhorRotaDTO(melhorRotaEncontrada, custoEstimadoDaRota);
	}

	private boolean ehODestinoPesquisado(Rota rota) {
		return rota.getDestino().equalsIgnoreCase(this.pesquisaRotaDTO.getDestino());
	}

	/**
	 * Responsavel por iniciar a pesquisa a partir do ponto de origem pesquisado
	 */
	private void procurarRotas() {
		List<Rota> rotaInicial = new ArrayList<Rota>();
		rotasEncontradas.add(rotaInicial);
		procurarProximaRota(rotaInicial, pesquisaRotaDTO.getOrigem(), pesquisaRotaDTO.getDestino(), mapa.getRotas());
	}

	/**
	 * Responsavel por procurar a rota baseada nos parametros. Caso somente
	 * encontre origem, mas com o outro destino, entao recursivamente ele metodo
	 * irah se chamando até que encontre o destino ou acabe as possibilidades
	 * 
	 * Este metodo encontra todos os caminhos baseado na origem, independente do
	 * destino
	 * 
	 * @param rotaEncontrada
	 * @param origem
	 * @param destino
	 * @param rotas
	 */
	private void procurarProximaRota(List<Rota> rotaEncontrada, String origem, String destino, List<Rota> rotas) {
		// Valida se a origem jah passou por alguma rota, entao deve ser
		// disconsiderado, para nao gear looping infinito de rotas
		if (jaPassouPeloPontoComoOrigem(origem, rotaEncontrada)) {
			return;
		}

		List<Rota> rotaEncontradaCopy = new ArrayList<Rota>(rotaEncontrada);

		// flag que controla se foi encontrada alguma rota para este looping.
		// Isto eh necessario pois se encontrar mais de uma rota para o Loop,
		// então deve se gerar uma nova lista de rotas
		boolean encontradaRotaNesteLoop = false;

		for (Rota rota : rotas) {

			// Valida origem e destino com a rota
			if (saoIguais(rota.getOrigem(), origem) && saoIguais(rota.getDestino(), destino)) {
				List<Rota> novaRota = rotaEncontrada;
				if (encontradaRotaNesteLoop) {
					new ArrayList<Rota>(rotaEncontradaCopy);
					rotasEncontradas.add(novaRota);
				}
				novaRota.add(rota);

				encontradaRotaNesteLoop = true;

				// Valida a origem com a origem da rota
			} else if (saoIguais(rota.getOrigem(), origem)) {
				List<Rota> novaRota = rotaEncontrada;
				if (encontradaRotaNesteLoop) {
					novaRota = new ArrayList<Rota>(rotaEncontradaCopy);
					rotasEncontradas.add(novaRota);
				}

				novaRota.add(rota);

				encontradaRotaNesteLoop = true;
				procurarProximaRota(novaRota, rota.getDestino(), destino, rotas);
			}

			if (!rotaEncontrada.isEmpty() && saoIguais(pesquisaRotaDTO.getOrigem(), origem)) {
				rotaEncontrada = new ArrayList<Rota>();
				rotasEncontradas.add(rotaEncontrada);
			}
		}
	}

	/**
	 * Valida se o ponto esta como origem em alguma das rotas
	 * 
	 * @param ponto
	 * @param rotas
	 * @return boolean - Se a origem esta em algumas das rotas já tracadas
	 */
	public boolean jaPassouPeloPontoComoOrigem(String ponto, List<Rota> rotas) {
		for (Rota rota : rotas) {
			if (saoIguais(rota.getOrigem(), ponto)) {
				return true;
			}
		}
		return false;
	}

	private boolean saoIguais(String valorA, String valorB) {
		return valorA.equalsIgnoreCase(valorB);
	}

}
