package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.infra.exception.dto;

public record ValidationError(
        String field,
        String message
) {

}
