package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.exception.dto;

import br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.exception.HttpStatusCustom;

import java.time.LocalDateTime;

public record ResponseException(
        LocalDateTime timestamp,
        String status,
        String message,
        String path
) {

    public ResponseException(String status, String message, String path) {
        this(LocalDateTime.now(), status, message, path);
    }

    public ResponseException(HttpStatusCustom httpStatusCustom, String path, String message) {
        this(
                httpStatusCustom.getHttpStatus().name(),
                getMessageOrDefault(message, httpStatusCustom),
                path
        );
    }

    public ResponseException(HttpStatusCustom httpStatusCustom, String path) {
        this(
                httpStatusCustom.getHttpStatus().name(),
                httpStatusCustom.getDefaultMessage(),
                path
        );
    }

    private static String getMessageOrDefault(String message, HttpStatusCustom httpStatusCustom) {
        if (message != null && !message.isBlank()) {
            return message;
        }
        return httpStatusCustom.getDefaultMessage();
    }

}
