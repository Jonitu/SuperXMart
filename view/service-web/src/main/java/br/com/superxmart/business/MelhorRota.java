package br.com.superxmart.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.superxmart.dto.MelhorRotaDTO;
import br.com.superxmart.dto.PesquisaRotaDTO;
import br.com.superxmart.entidade.Mapa;
import br.com.superxmart.entidade.Rota;
import br.com.superxmart.exception.NenhumaRotaEncontradaException;

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

	public MelhorRotaDTO encontrarMelhorRota() throws NenhumaRotaEncontradaException {

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

		if (melhorRotaEncontrada == null) {
			throw new NenhumaRotaEncontradaException(pesquisaRotaDTO);
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
	 * irah se chamando ate que encontre o destino ou acabe as possibilidades
	 * 
	 * Este metodo encontra todos os caminhos baseado na origem, independente do
	 * destino
	 * 
	 * @param rotaDeEntrada
	 * @param origem
	 * @param destino
	 * @param rotas
	 */
	private void procurarProximaRota(List<Rota> rotaDeEntrada, String origem, String destino, List<Rota> rotas) {
		// Valida se a origem jah passou por alguma rota, entao deve ser
		// disconsiderado, para nao gear looping infinito de rotas
		if (jaPassouPeloPontoComoOrigem(origem, rotaDeEntrada)) {
			return;
		}

		// Cria uma copia do ponto atual da lista de Rotas, pois se surger mais
		// de uma rota, deve-se usar a lista como entrou
		List<Rota> rotaEncontradaCopia = new ArrayList<Rota>(rotaDeEntrada);

		// flag que controla se foi encontrada alguma rota para este looping.
		// Isto eh necessario pois se encontrar mais de uma rota para o Loop,
		// entao deve se gerar uma nova lista de rotas
		boolean encontradaRotaNesteLoop = false;

		for (Rota rota : rotas) {

			// Valida origem e destino com a rota
			if (saoIguais(rota.getOrigem(), origem) && saoIguais(rota.getDestino(), destino)) {

				List<Rota> novaRota = criarNovaRota(encontradaRotaNesteLoop, rotaDeEntrada, rotaEncontradaCopia);

				novaRota.add(rota);

				encontradaRotaNesteLoop = true;

				// Valida a origem com a origem da rota
			} else if (saoIguais(rota.getOrigem(), origem)) {
				List<Rota> novaRota = criarNovaRota(encontradaRotaNesteLoop, rotaDeEntrada, rotaEncontradaCopia);

				novaRota.add(rota);

				encontradaRotaNesteLoop = true;

				// Segue para a proxima rota, pois o destino nao foi encontrado
				// ainda
				procurarProximaRota(novaRota, rota.getDestino(), destino, rotas);
			}

			// Necessario criar uma nova lista para cada origem igual encontrada
			// no loop inicial
			if (!rotaDeEntrada.isEmpty() && saoIguais(pesquisaRotaDTO.getOrigem(), origem)) {
				rotaDeEntrada = new ArrayList<Rota>();
				rotasEncontradas.add(rotaDeEntrada);
			}
		}
	}

	/**
	 * Metodo que verifica se deve ser criada uma nova lista de rotas ou se deve
	 * permanecer na mesma lista
	 * 
	 * @param encontradaRotaNesteLoop
	 * @param rotaDeEntrada
	 * @param rotaEncontradaCopia
	 * @return lista que rotas
	 */
	private List<Rota> criarNovaRota(boolean encontradaRotaNesteLoop, List<Rota> rotaDeEntrada, List<Rota> rotaEncontradaCopia) {
		if (encontradaRotaNesteLoop) {
			List<Rota> novaRota = new ArrayList<Rota>(rotaEncontradaCopia);
			rotasEncontradas.add(novaRota);
			return novaRota;
		}
		return rotaDeEntrada;
	}

	/**
	 * Valida se o ponto esta como origem em alguma das rotas
	 * 
	 * @param ponto
	 * @param rotas
	 * @return boolean - Se a origem esta em algumas das rotas ja tracadas
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
