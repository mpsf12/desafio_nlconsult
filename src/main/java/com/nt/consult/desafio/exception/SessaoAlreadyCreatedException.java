package com.nt.consult.desafio.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class SessaoAlreadyCreatedException extends RuntimeException {

	public SessaoAlreadyCreatedException() {
		super();
	}

	public SessaoAlreadyCreatedException(String message, Throwable cause) {
		super(message, cause);
	}

	public SessaoAlreadyCreatedException(String message) {
		super(message);
	}

	public SessaoAlreadyCreatedException(Throwable cause) {
		super(cause);
	}

	
}
