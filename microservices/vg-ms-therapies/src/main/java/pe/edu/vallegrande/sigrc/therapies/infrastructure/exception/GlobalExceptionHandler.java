package pe.edu.vallegrande.sigrc.therapies.infrastructure.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.edu.vallegrande.sigrc.therapies.domain.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;
import java.util.Map;
import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Mono<Map<String, String>> handleNotFound(NotFoundException ex) {
        return Mono.just(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(WebExchangeBindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<Map<String, Object>> handleValidation(WebExchangeBindException ex) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("error", "Validation failed");
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getFieldErrors().forEach(fe -> fieldErrors.put(fe.getField(), fe.getDefaultMessage()));
        errors.put("fields", fieldErrors);
        return Mono.just(errors);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<Map<String, String>> handleGeneral(Exception ex) {
        log.error("Unhandled exception", ex);
        return Mono.just(Map.of("error", "Internal server error"));
    }
}
