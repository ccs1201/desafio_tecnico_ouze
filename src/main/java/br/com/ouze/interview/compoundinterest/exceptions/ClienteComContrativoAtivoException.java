package br.com.ouze.interview.compoundinterest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ClienteComContrativoAtivoException extends ResponseStatusException {
    public ClienteComContrativoAtivoException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }
}
