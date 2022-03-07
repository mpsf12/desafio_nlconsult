package com.nt.consult.desafio.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserNaoAutorizadoAVotarException extends RuntimeException {

	public UserNaoAutorizadoAVotarException() {
		super();
	}

	public UserNaoAutorizadoAVotarException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserNaoAutorizadoAVotarException(String message) {
		super(message);
	}

	public UserNaoAutorizadoAVotarException(Throwable cause) {
		super(cause);
	}

}
