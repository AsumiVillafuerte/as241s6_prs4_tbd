package pe.edu.vallegrande.sigrc.shopping.infrastructure.adapter.in.rest;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import pe.edu.vallegrande.sigrc.shopping.application.dto.common.ErrorResponse;
import pe.edu.vallegrande.sigrc.shopping.domain.exception.*;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Mono<ErrorResponse> handleNotFound(NotFoundException ex) {
        return Mono.just(ErrorResponse.of("Recurso no encontrado", ex.getMessage()));
    }

    @ExceptionHandler(DuplicateComprobanteException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Mono<ErrorResponse> handleDuplicate(DuplicateComprobanteException ex) {
        return Mono.just(ErrorResponse.of("Comprobante duplicado", ex.getMessage()));
    }

    @ExceptionHandler(BusinessRuleException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<ErrorResponse> handleBusinessRule(BusinessRuleException ex) {
        return Mono.just(ErrorResponse.of("Regla de negocio violada", ex.getMessage()));
    }

    @ExceptionHandler(ExternalServiceException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public Mono<ErrorResponse> handleExternal(ExternalServiceException ex) {
        return Mono.just(ErrorResponse.of("Error en servicio externo", ex.getMessage()));
    }

    @ExceptionHandler(WebExchangeBindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<ErrorResponse> handleValidation(WebExchangeBindException ex) {
        String detail = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        return Mono.just(ErrorResponse.of("Datos inválidos", detail));
    }

    @ExceptionHandler(DomainException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<ErrorResponse> handleDomain(DomainException ex) {
        return Mono.just(ErrorResponse.of("Error de dominio", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<ErrorResponse> handleGeneral(Exception ex) {
        return Mono.just(ErrorResponse.of("Error interno del servidor", ex.getMessage()));
    }
}
