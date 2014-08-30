package br.com.superxmart.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import br.com.superxmart.business.RotaBO;
import br.com.superxmart.dto.MelhorRotaDTO;
import br.com.superxmart.dto.PesquisaRotaDTO;
import br.com.superxmart.entidade.Mapa;
import br.com.superxmart.exception.MapaNaoEncontradoException;
import br.com.superxmart.exception.NenhumaRotaEncontradaException;

@WebService(serviceName = "Rota")
@SOAPBinding(style = Style.RPC)
public class RotaWebService {

	private RotaBO bo = new RotaBO();

	@WebMethod
	public Mapa cadastrarRota(@WebParam(name = "mapa") Mapa pMapa) {
		Mapa mapa = bo.cadastrarRota(pMapa);

		return mapa;
	}

	@WebMethod
	public MelhorRotaDTO pesquisarRota(@WebParam(name = "pesquisaDeRota") PesquisaRotaDTO pesquisaRota) throws MapaNaoEncontradoException,
			NenhumaRotaEncontradaException {
		MelhorRotaDTO melhorRotaDTO = bo.buscarRota(pesquisaRota);
		return melhorRotaDTO;
	}

}
