package br.com.superxmart.entidade;

public class Rota extends Entidade {

	private String origem;

	private String destino;

	private Integer distancia;

	public Rota() {
	}

	public Rota(String origem, String destino, Integer distancia) {
		this.origem = origem;
		this.destino = destino;
		this.distancia = distancia;
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

	public Integer getDistancia() {
		return distancia;
	}

	public void setDistancia(Integer distancia) {
		this.distancia = distancia;
	}

	@Override
	public String toString() {
		return "Rota [origem=" + origem + ", destino=" + destino + ", distancia=" + distancia + "]";
	}

}
