package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum HttpStatusCustom {
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "A solicitação está inválida"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "O recurso solicitado não foi encontrado"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno no servidor"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "Acesso não autorizado"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "Permissão negada.");

    private final HttpStatus httpStatus;
    private final String defaultMessage;

    HttpStatusCustom(HttpStatus httpStatus, String defaultMessage) {
        this.httpStatus = httpStatus;
        this.defaultMessage = defaultMessage;
    }

}
