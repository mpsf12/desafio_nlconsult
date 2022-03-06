package com.nt.consult.desafio.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class SessaoNotFoundException extends RuntimeException {

	public SessaoNotFoundException() {
		super();
	}

	public SessaoNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public SessaoNotFoundException(String message) {
		super(message);
	}

	public SessaoNotFoundException(Throwable cause) {
		super(cause);
	}

}
