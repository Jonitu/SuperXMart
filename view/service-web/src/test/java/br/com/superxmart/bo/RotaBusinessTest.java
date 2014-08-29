package br.com.superxmart.bo;

import org.testng.annotations.BeforeMethod;

import br.com.superxmart.business.RotaBO;

public class RotaBusinessTest {

	private RotaBO rotaBusiness;

	@BeforeMethod
	public void setUp() {
		this.rotaBusiness = new RotaBO();
	}

}
