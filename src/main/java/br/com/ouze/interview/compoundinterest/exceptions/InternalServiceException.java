package br.com.ouze.interview.compoundinterest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InternalServiceException extends ResponseStatusException {
    public InternalServiceException(String msg, Exception e) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, msg, e);
    }
}
