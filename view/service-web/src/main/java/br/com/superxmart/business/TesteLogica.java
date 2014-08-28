package br.com.superxmart.business;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.superxmart.entidade.Mapa;
import br.com.superxmart.entidade.Rota;

public class TesteLogica {
	private static final String ESPACO = " ";
	private List<List<Rota>> rotasEncontradas;
	private String nomeMapa;
	private String origem;
	private String destino;
	private Integer autonomia;
	private BigDecimal valorCombustivel;

	private NumberFormat DECIMAL_FORMAT = new DecimalFormat("0.00");

	public static void main(String[] args) {
		new TesteLogica().testar("SP", "A", "D", 10, new BigDecimal("2.509"));
	}

	private void testar(String nomeMapa, String origem, String destino, Integer autonomia, BigDecimal valorCombustivel) {
		this.nomeMapa = nomeMapa;
		this.origem = origem;
		this.destino = destino;
		this.autonomia = autonomia;
		this.valorCombustivel = valorCombustivel;
		rotasEncontradas = new ArrayList<List<Rota>>();
		Mapa mapa = getMapa(nomeMapa);

		List<Rota> rotas = mapa.getRotas();

		extracted(null, origem, destino, rotas);

		List<Rota> melhorRota = null;

		Integer menorRota = null;

		for (List<Rota> list : rotasEncontradas) {
			int index = 1;
			Integer distancia = 0;
			for (Rota rota : list) {
				distancia += rota.getDistancia();
				System.out.print(rota.getOrigem() + ESPACO);
				if (index == list.size()) {
					System.out.println(rota.getDestino());
					if (rota.getDestino().equalsIgnoreCase(this.destino)) {
						if (menorRota == null || distancia < menorRota
								|| (distancia == menorRota && list.size() < melhorRota.size())) {
							menorRota = distancia;
							melhorRota = list;
						}
					}
				}
				index++;
			}
		}

		StringBuilder sb = new StringBuilder();
		for (int index = 0; index < melhorRota.size(); index++) {
			Rota rota = melhorRota.get(index);
			sb.append(rota.getOrigem());
			sb.append(ESPACO);
			if (index + 1 == melhorRota.size()) {
				sb.append(rota.getDestino());
				sb.append(ESPACO);
			}
		}

		BigDecimal consumoEstimado = new BigDecimal(menorRota).divide(new BigDecimal(autonomia));

		BigDecimal valor = consumoEstimado.multiply(valorCombustivel);

		System.out.println(MessageFormat.format("Rota {0}com custo de {1}.", sb.toString(),
				DECIMAL_FORMAT.format(valor)));
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

			if (!rotaEncontrada.isEmpty() && this.origem.equalsIgnoreCase(origem)) {
				rotaEncontrada = new ArrayList<Rota>();
				rotasEncontradas.add(rotaEncontrada);
			}
		}
	}

	private Mapa getMapa(String nomeMapa) {
		Mapa mapa = new Mapa();
		mapa.setNome(nomeMapa);

		mapa.addRota(new Rota("A", "B", 10));
		mapa.addRota(new Rota("B", "D", 15));
		mapa.addRota(new Rota("A", "C", 20));
		mapa.addRota(new Rota("C", "D", 1));
		mapa.addRota(new Rota("B", "E", 50));
		mapa.addRota(new Rota("D", "E", 30));

		mapa.addRota(new Rota("B", "C", 1));

		return mapa;
	}
}
