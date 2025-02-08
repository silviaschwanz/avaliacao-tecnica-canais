package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.infra.exception.response;

public record ValidationError(
        String field,
        String message
) {

}
