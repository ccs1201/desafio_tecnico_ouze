package br.com.ouze.interview.compoundinterest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InternalServerErrorException extends ResponseStatusException {
    public InternalServerErrorException(String reason, Throwable cause) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, reason, cause);
    }
}
