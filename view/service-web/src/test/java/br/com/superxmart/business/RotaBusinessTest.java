package br.com.superxmart.business;

import java.math.BigDecimal;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class RotaBusinessTest {

	private RotaBusiness rotaBusiness;

	@BeforeMethod
	public void setUp() {
		this.rotaBusiness = new RotaBusiness();
	}

	@Test
	public void buscarRota() {
		String melhorRota = rotaBusiness.buscarRota("SP", "A", "D", 10, new BigDecimal("2.50"));
		Assert.assertEquals(melhorRota, "Rota A B D com custo de 6,25.", "A Rota esta errada");
	}
}
