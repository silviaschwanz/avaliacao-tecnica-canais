package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.infra.exception.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum TypeError {
        BAD_REQUEST("bad_request", HttpStatus.BAD_REQUEST, "A solicitação está inválida."),
        NOT_FOUND("not_found", HttpStatus.NOT_FOUND, "O recurso solicitado não foi encontrado."),
        INTERNAL_SERVER_ERROR("internal_server_error", HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno do servidor."),
        UNAUTHORIZED("unauthorized", HttpStatus.UNAUTHORIZED, "Acesso não autorizado."),
        FORBIDDEN("forbidden", HttpStatus.FORBIDDEN, "Permissão negada.");

        private final String errorId;
        private final HttpStatus httpStatus;
        private final String defaultMessage;

        TypeError(String errorId, HttpStatus httpStatus, String defaultMessage) {
            this.errorId = errorId;
            this.httpStatus = httpStatus;
            this.defaultMessage = defaultMessage;
        }

    public static TypeError fromStatusCode(HttpStatus httpStatus) {
        for (TypeError type : TypeError.values()) {
            if (type.getHttpStatus() == httpStatus) {
                return type;
            }
        }
        throw new IllegalArgumentException("Tipo de erro não encontrado para o código de status: " + httpStatus);
    }

}
