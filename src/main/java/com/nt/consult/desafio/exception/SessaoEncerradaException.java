package com.nt.consult.desafio.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class SessaoEncerradaException extends RuntimeException {

	public SessaoEncerradaException() {
		super();
	}

	public SessaoEncerradaException(String message, Throwable cause) {
		super(message, cause);
	}

	public SessaoEncerradaException(String message) {
		super(message);
	}

	public SessaoEncerradaException(Throwable cause) {
		super(cause);
	}
	
}
