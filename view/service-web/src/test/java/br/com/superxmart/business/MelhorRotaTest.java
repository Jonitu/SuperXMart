package br.com.superxmart.business;

import java.math.BigDecimal;

import org.testng.Assert;
import org.testng.annotations.Test;

import br.com.superxmart.dto.MelhorRotaDTO;
import br.com.superxmart.dto.PesquisaRotaDTO;
import br.com.superxmart.entidade.Mapa;
import br.com.superxmart.entidade.Rota;

public class MelhorRotaTest {

	@Test
	public void testeEncontrarMelhorRotaAvaliacaoTecnica() {
		PesquisaRotaDTO pesquisaRotaDTO = criarPesquisaRotaDTO();

		MelhorRotaDTO melhorRotaDTO = new MelhorRota(pesquisaRotaDTO, getMapaPadrao()).encontrarMelhorRota();

		Assert.assertEquals(melhorRotaDTO.getMelhorRota(), "Rota A B D com custo de 6,25.");
	}

	@Test
	public void testeEncontrarMelhorRotaIgnorandoLooping() {
		PesquisaRotaDTO pesquisaRotaDTO = criarPesquisaRotaDTO();

		Mapa mapa = getMapaPadrao();
		mapa.addRota(new Rota("B", "A", 1));

		MelhorRotaDTO melhorRotaDTO = new MelhorRota(pesquisaRotaDTO, mapa).encontrarMelhorRota();

		Assert.assertEquals(melhorRotaDTO.getMelhorRota(), "Rota A B D com custo de 6,25.");
	}

	@Test
	public void testeEncontrarMelhorRotaComDistanciaMenorComMaisRotas() {
		PesquisaRotaDTO pesquisaRotaDTO = criarPesquisaRotaDTO();

		Mapa mapa = getMapaPadrao();
		mapa.addRota(new Rota("B", "F", 1));
		mapa.addRota(new Rota("F", "D", 1));

		MelhorRotaDTO melhorRotaDTO = new MelhorRota(pesquisaRotaDTO, mapa).encontrarMelhorRota();

		Assert.assertEquals(melhorRotaDTO.getMelhorRota(), "Rota A B F D com custo de 3,00.");
	}

	@Test
	public void testeNaoEncontrarRota() {

		PesquisaRotaDTO pesquisaRotaDTO = criarPesquisaRotaDTO();

		Mapa mapa = getMapaPadrao();
		mapa.addRota(new Rota("B", "F", 1));
		mapa.addRota(new Rota("F", "D", 1));

		MelhorRotaDTO melhorRotaDTO = new MelhorRota(pesquisaRotaDTO, mapa).encontrarMelhorRota();

		Assert.assertEquals(melhorRotaDTO.getMelhorRota(), "Rota A B F D com custo de 3,00.");
	}

	private PesquisaRotaDTO criarPesquisaRotaDTO() {
		PesquisaRotaDTO pesquisaRotaDTO = new PesquisaRotaDTO();
		pesquisaRotaDTO.setOrigem("A");
		pesquisaRotaDTO.setDestino("D");
		pesquisaRotaDTO.setAutonomiaVeiculo(10);
		pesquisaRotaDTO.setValorLitroCombustivel(new BigDecimal("2.5"));
		return pesquisaRotaDTO;
	}

	private Mapa getMapaPadrao() {
		Mapa mapa = new Mapa();

		mapa.addRota(new Rota("A", "B", 10));
		mapa.addRota(new Rota("B", "D", 15));
		mapa.addRota(new Rota("A", "C", 20));
		mapa.addRota(new Rota("C", "D", 30));
		mapa.addRota(new Rota("B", "E", 50));
		mapa.addRota(new Rota("D", "E", 30));

		return mapa;
	}
}
