package br.com.superxmart.entidade;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "nome" }) })
public class Mapa extends Entidade {

	@Column(nullable = false, length = 200)
	private String nome;

	@OneToMany(mappedBy = "mapa", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Rota> rotas = new ArrayList<Rota>();

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Rota> getRotas() {
		return rotas;
	}

	public void setRotas(List<Rota> rotas) {
		this.rotas = rotas;
	}

	public void addRota(Rota rota) {
		rotas.add(rota);
	}

}
