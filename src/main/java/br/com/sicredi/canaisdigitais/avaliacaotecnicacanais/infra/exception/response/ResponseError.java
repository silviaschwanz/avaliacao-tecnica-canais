package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.infra.exception.response;

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
