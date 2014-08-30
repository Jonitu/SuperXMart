package br.com.superxmart.exception;

import java.text.MessageFormat;

import javax.xml.ws.WebFault;

import br.com.superxmart.dto.PesquisaRotaDTO;

@WebFault(name = "SuperXMartFault", targetNamespace = "http://ws.superxmart.com.br/")
public class NenhumaRotaEncontradaException extends Exception {

	private static final long serialVersionUID = 6852151610265091576L;

	private static final String ROTA_NAO_ENCONTRADA = "A Rota origem {0} a {1} do mapa {2} nao foi encontrada";

	private SuperXMartFault fault;

	public NenhumaRotaEncontradaException(PesquisaRotaDTO dto) {
		super(getMensagem(dto));
		preencheSoapFault(getMensagem(dto));
	}

	public SuperXMartFault getFaultInfo() {
		return fault;
	}

	private void preencheSoapFault(String mensagem) {
		this.fault = new SuperXMartFault();
		this.fault.setFaultString(mensagem);
		this.fault.setFaultCode(this.getClass().getSimpleName().replaceAll("Exception", ""));
	}

	private static String getMensagem(PesquisaRotaDTO dto) {
		return MessageFormat.format(ROTA_NAO_ENCONTRADA, dto.getOrigem(), dto.getDestino(), dto.getNomeMapa());
	}

}
