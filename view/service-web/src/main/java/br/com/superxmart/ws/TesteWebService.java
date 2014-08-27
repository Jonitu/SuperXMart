package br.com.superxmart.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(serviceName="Rota")
public class TesteWebService implements TesteEndpoint{

	@Override
	@WebMethod
	@WebResult(name = "resultado")
	public String novoMetodo(@WebParam(name = "Texto") String paramString, @WebParam(name = "Inteiro") Integer paramInteger) {
		
		return "Hello World!!";
	}

}
