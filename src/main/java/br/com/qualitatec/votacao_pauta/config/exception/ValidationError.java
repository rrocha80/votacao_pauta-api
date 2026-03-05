package br.com.qualitatec.votacao_pauta.config.exception;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
public class ValidationError {

    private int status;
    private String error;
    private Map<String, String> fields;
    private String path;
    private LocalDateTime timestamp;

    public ValidationError(int status, String error,
                           Map<String, String> fields, String path) {
        this.status = status;
        this.error = error;
        this.fields = fields;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }
}

