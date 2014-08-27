package br.com.superxmart.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import br.com.superxmart.entidade.Mapa;

@WebService(serviceName="Rota")
public class RotaWebService implements RotaEndpoint{

	@Override
	@WebMethod
	@WebResult(name = "resultado")
	public String cadastrarRota(@WebParam(name = "mapa") Mapa mapa) {
		
		return "Hello World!!";
	}

}
