package br.com.superxmart.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.superxmart.dto.PesquisaRotaDTO;
import br.com.superxmart.entidade.Mapa;
import br.com.superxmart.entidade.Rota;

public class MelhorRota {
	private List<List<Rota>> rotasEncontradas;

	private PesquisaRotaDTO pesquisaRotaDTO;
	private Mapa mapa;

	public MelhorRota(PesquisaRotaDTO pesquisaRotaDTO, Mapa mapa) {
		this.pesquisaRotaDTO = pesquisaRotaDTO;
		this.mapa = mapa;
	}

	public List<Rota> testar() {
		rotasEncontradas = new ArrayList<List<Rota>>();

		List<Rota> rotas = mapa.getRotas();

		extracted(null, pesquisaRotaDTO.getOrigem(), pesquisaRotaDTO.getDestino(), rotas);

		List<Rota> melhorRota = null;

		Integer menorRota = null;

		for (List<Rota> list : rotasEncontradas) {
			int index = 1;
			Integer distancia = 0;
			for (Rota rota : list) {
				distancia += rota.getDistancia();
				if (index == list.size()) {
					if (rota.getDestino().equalsIgnoreCase(this.pesquisaRotaDTO.getDestino())) {
						if (menorRota == null || distancia < menorRota || (distancia == menorRota && list.size() < melhorRota.size())) {
							menorRota = distancia;
							melhorRota = list;
						}
					}
				}
				index++;
			}
		}

		BigDecimal consumoEstimado = new BigDecimal(menorRota).divide(new BigDecimal(pesquisaRotaDTO.getAutonomiaVeiculo()));

		BigDecimal valor = consumoEstimado.multiply(pesquisaRotaDTO.getValorLitroCombustivel());

		return melhorRota;
	}

	private void extracted(List<Rota> rotaEncontrada, String origem, String destino, List<Rota> rotas) {
		if (rotaEncontrada == null) {
			rotaEncontrada = new ArrayList<Rota>();
			rotasEncontradas.add(rotaEncontrada);
		}
		List<Rota> rotaEncontradaCopy = new ArrayList<Rota>(rotaEncontrada);

		boolean encontrouRota = false;

		for (Rota rota : rotas) {

			// Valida origem e destino com a rota
			if (rota.getOrigem().equalsIgnoreCase(origem) && rota.getDestino().equalsIgnoreCase(destino)) {
				System.out.println("Fim Origem/Destino Final: " + rota.getOrigem() + "/" + rota.getDestino());
				if (encontrouRota) {
					List<Rota> novaRota = new ArrayList<Rota>(rotaEncontradaCopy);
					novaRota.add(rota);
					rotasEncontradas.add(novaRota);
				} else {
					rotaEncontrada.add(rota);
				}

				encontrouRota = true;
				// Valida a origem com a origem da rota
			} else if (rota.getOrigem().equalsIgnoreCase(origem)) {
				System.out.println("Inicio/Meio Origem/Destino: " + rota.getOrigem() + "/" + rota.getDestino());
				List<Rota> novaRota = rotaEncontrada;
				if (encontrouRota) {
					novaRota = new ArrayList<Rota>(rotaEncontradaCopy);
					novaRota.add(rota);
					rotasEncontradas.add(novaRota);
				} else {
					novaRota.add(rota);
				}
				encontrouRota = true;
				extracted(novaRota, rota.getDestino(), destino, rotas);
			}

			if (!rotaEncontrada.isEmpty() && pesquisaRotaDTO.getOrigem().equalsIgnoreCase(origem)) {
				rotaEncontrada = new ArrayList<Rota>();
				rotasEncontradas.add(rotaEncontrada);
			}
		}
	}

}
