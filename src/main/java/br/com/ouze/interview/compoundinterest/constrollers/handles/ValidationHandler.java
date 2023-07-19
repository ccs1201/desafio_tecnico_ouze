package br.com.ouze.interview.compoundinterest.constrollers.handles;

import br.com.ouze.interview.compoundinterest.dtos.ErrorMessageResponse;
import br.com.ouze.interview.compoundinterest.exceptions.BadRequestException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.stream.Collectors;

import static java.text.MessageFormat.format;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@ControllerAdvice
public class ValidationHandler {

    @ResponseBody
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    void exceptionHandler(ValidationException e) {
        throw new BadRequestException(e.getMessage(), e);
    }

    @ResponseBody
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ErrorMessageResponse handleValidationExceptions(MethodArgumentNotValidException e, HttpServletRequest httpServletRequest) {
        return ErrorMessageResponse.builder()
                .message(e.getBindingResult()
                        .getAllErrors()
                        .parallelStream()
                        .map(objectError -> format("{0}: {1}", ((FieldError) objectError).getField(), (objectError).getDefaultMessage()))
                        .sorted()
                        .collect(Collectors.joining()))
                .error(e.getClass().getSimpleName())
                .status(BAD_REQUEST.value())
                .path(httpServletRequest.getRequestURI())
                .build();
    }
}
