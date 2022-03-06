package com.nt.consult.desafio.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PautaNotFoundException extends RuntimeException {

	public PautaNotFoundException() {
		super();
	}

	public PautaNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public PautaNotFoundException(String message) {
		super(message);
	}

	public PautaNotFoundException(Throwable cause) {
		super(cause);
	}

}
