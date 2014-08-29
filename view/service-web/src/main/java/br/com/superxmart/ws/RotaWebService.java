package br.com.superxmart.ws;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import br.com.superxmart.business.RotaBO;
import br.com.superxmart.dto.PesquisaRotaDTO;
import br.com.superxmart.entidade.Mapa;
import br.com.superxmart.entidade.Rota;

@WebService(serviceName = "Rota")
@SOAPBinding(style = Style.RPC)
public class RotaWebService {

	private static final String ESPACO = " ";

	private static final NumberFormat DECIMAL_FORMAT = new DecimalFormat("0.00");

	private RotaBO bo = new RotaBO();

	@WebMethod
	public Mapa cadastrarRota(@WebParam(name = "mapa") Mapa pMapa) {
		Mapa mapa = bo.cadastrarRota(pMapa);

		return mapa;
	}

	@WebMethod
	@WebResult(name = "melhorRota")
	public String pesquisarRota(@WebParam(name = "pesquisaDeRota") PesquisaRotaDTO pesquisaRota) {
		List<Rota> melhorRota = bo.buscarRota(pesquisaRota);

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

		String rotaEscolhida = MessageFormat.format("Rota {0}com custo de {1}.", sb.toString(), DECIMAL_FORMAT.format(9));

		return rotaEscolhida;
	}

}
