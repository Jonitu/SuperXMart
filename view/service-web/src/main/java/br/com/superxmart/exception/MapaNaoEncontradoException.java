package br.com.superxmart.exception;

import java.text.MessageFormat;

import javax.xml.ws.WebFault;

@WebFault(name = "SuperXMartFault", targetNamespace = "http://ws.superxmart.com.br/")
public class MapaNaoEncontradoException extends Exception {

	private static final long serialVersionUID = -6647544772732631047L;

	private SuperXMartFault fault;

	public MapaNaoEncontradoException(String nomeMapa) {
		super(getMensagem(nomeMapa));
		preencheSoapFault(nomeMapa);
	}

	public SuperXMartFault getFaultInfo() {
		return fault;
	}

	private void preencheSoapFault(String nomeMapa) {
		this.fault = new SuperXMartFault();
		this.fault.setFaultString(getMensagem(nomeMapa));
		this.fault.setFaultCode(this.getClass().getSimpleName().replaceAll("Exception", ""));
	}

	private static String getMensagem(String nomeMapa) {
		return MessageFormat.format("O mapa {0} nao foi encontrado.", nomeMapa);
	}

}