package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.api.usuario;

public record Endereco(
        String logradouro,
        int numero,
        String cidade,
        String bairro,
        String estado
) {
}
