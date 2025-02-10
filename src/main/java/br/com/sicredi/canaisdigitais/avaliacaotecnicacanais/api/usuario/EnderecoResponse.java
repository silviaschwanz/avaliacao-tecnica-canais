package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.api.usuario;

public record EnderecoResponse(
        String logradouro,
        int numero,
        String cidade,
        String bairro,
        String estado
) {
}
