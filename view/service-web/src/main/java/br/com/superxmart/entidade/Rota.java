package br.com.superxmart.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "origem", "destino", "mapa_id" }) })
public class Rota extends Entidade {

	@Column(length = 200, nullable = false)
	private String origem;

	@Column(length = 200, nullable = false)
	private String destino;

	@Column(nullable = false)
	private Integer distancia;

	@ManyToOne(optional = false)
	@XmlTransient
	private Mapa mapa;

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

	@XmlTransient
	public Mapa getMapa() {
		return mapa;
	}

	public void setMapa(Mapa mapa) {
		this.mapa = mapa;
	}

}
