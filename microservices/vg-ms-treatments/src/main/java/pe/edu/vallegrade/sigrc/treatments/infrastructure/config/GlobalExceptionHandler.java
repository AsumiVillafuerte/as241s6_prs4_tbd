package pe.edu.vallegrade.sigrc.treatments.infrastructure.config;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import pe.edu.vallegrade.sigrc.treatments.application.dto.common.ErrorResponse;
import pe.edu.vallegrade.sigrc.treatments.domain.exceptions.DomainException;
import pe.edu.vallegrade.sigrc.treatments.domain.exceptions.NotFoundException;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalExceptionHandler {
        // 404 - no encontrado
        @ExceptionHandler(NotFoundException.class)
        @ResponseStatus(HttpStatus.NOT_FOUND)
        public Mono<ErrorResponse> handleNotFound(NotFoundException ex) {
                return Mono.just(ErrorResponse.builder()
                                .status(404)
                                .message(ex.getMessage())
                                .timestamp(LocalDateTime.now())
                                .build());
        }

        // 400 - regla de negocio violada
        @ExceptionHandler(DomainException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public Mono<ErrorResponse> handleDomain(DomainException ex) {
                return Mono.just(ErrorResponse.builder()
                                .status(400)
                                .message(ex.getMessage())
                                .timestamp(LocalDateTime.now())
                                .build());
        }

        // 400 - validaciones @Valid
        @ExceptionHandler(WebExchangeBindException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public Mono<ErrorResponse> handleValidation(WebExchangeBindException ex) {
                String errores = ex.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                                .collect(Collectors.joining(", "));

                return Mono.just(ErrorResponse.builder()
                                .status(400)
                                .message(errores)
                                .timestamp(LocalDateTime.now())
                                .build());
        }

        // 500 - error inesperado
        @ExceptionHandler(Exception.class)
        @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
        public Mono<ErrorResponse> handleGeneral(Exception ex) {
                ex.printStackTrace(); // ← agrega esto temporalmente
                return Mono.just(ErrorResponse.builder()
                                .status(500)
                                .message("Error interno del servidor: " + ex.getMessage())
                                .timestamp(LocalDateTime.now())
                                .build());
        }
}
