package pe.edu.vallegrande.sigrc.consultation.infrastructure.adapter.input.web;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import lombok.extern.slf4j.Slf4j;
import pe.edu.vallegrande.sigrc.consultation.domain.exception.NotFoundException;
import pe.edu.vallegrande.sigrc.consultation.infrastructure.adapter.input.web.dto.common.ErrorResponse;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException ex) {
        log.error("Not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("Not Found", ex.getMessage(), HttpStatus.NOT_FOUND.value(), LocalDateTime.now(ZoneOffset.UTC)));
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<ErrorResponse> handleValidation(WebExchangeBindException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .reduce((a, b) -> a + ", " + b)
                .orElse("Validation error");
        log.error("Validation error: {}", message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("Validation Error", message, HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(ZoneOffset.UTC)));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        log.error("Business validation error: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("Bad Request", ex.getMessage(), HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(ZoneOffset.UTC)));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        log.error("Unexpected error", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("Internal Server Error", "An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now(ZoneOffset.UTC)));
    }
}
