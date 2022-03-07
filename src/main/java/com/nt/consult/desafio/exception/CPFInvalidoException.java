package com.nt.consult.desafio.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CPFInvalidoException extends RuntimeException {

	public CPFInvalidoException() {
		super();
	}

	public CPFInvalidoException(String message, Throwable cause) {
		super(message, cause);
	}

	public CPFInvalidoException(String message) {
		super(message);
	}

	public CPFInvalidoException(Throwable cause) {
		super(cause);
	}

}
