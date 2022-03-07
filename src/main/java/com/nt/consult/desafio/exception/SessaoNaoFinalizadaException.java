package com.nt.consult.desafio.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class SessaoNaoFinalizadaException extends RuntimeException {

	public SessaoNaoFinalizadaException() {
		super();
	}

	public SessaoNaoFinalizadaException(String message, Throwable cause) {
		super(message, cause);
	}

	public SessaoNaoFinalizadaException(String message) {
		super(message);
	}

	public SessaoNaoFinalizadaException(Throwable cause) {
		super(cause);
	}

}
