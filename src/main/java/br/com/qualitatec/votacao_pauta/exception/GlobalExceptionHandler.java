package br.com.qualitatec.votacao_pauta.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ValidationError handleValidationErrors(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(error.getField(), error.getDefaultMessage())
                );

        return new ValidationError(
                HttpStatus.BAD_REQUEST.value(),
                "Validation Error",
                errors,
                request.getRequestURI()
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDatabaseError(
            DataIntegrityViolationException ex) {

        return ResponseEntity
                .badRequest()
                .body("Erro de integridade no banco de dados.");
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(BusinessException.class)
    public ApiError handleBusinessException(
            BusinessException ex,
            HttpServletRequest request) {

        return new ApiError(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Business Error",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ApiError handleGenericException(
            Exception ex,
            HttpServletRequest request) {

        return new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                "Erro inesperado",
                request.getRequestURI()
        );
    }

    @ExceptionHandler(CpfInvalidoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleCpfInvalidoException(
            CpfInvalidoException ex,
            HttpServletRequest request) {

        return new ApiError(
                HttpStatus.NOT_FOUND.value(),
                "CPF inválido",
                ex.getMessage(),
                request.getRequestURI()
        );
    }
}
