package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.infra.exception.dto;

import br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.infra.exception.TypeError;

import java.time.LocalDateTime;
import java.util.List;

public record ResponseError(
        LocalDateTime timestamp,
        String errorId,
        String message,
        String path,
        List<ValidationError> validationErrors
) {

    public ResponseError(TypeError typeError, String path, List<ValidationError> validationErrors) {
        this(
                LocalDateTime.now(),
                typeError.getErrorId(),
                typeError.getDefaultMessage(),
                path,
                validationErrors
        );
    }

    public ResponseError(TypeError errorType, String path) {
        this(errorType, path, null);
    }

}
