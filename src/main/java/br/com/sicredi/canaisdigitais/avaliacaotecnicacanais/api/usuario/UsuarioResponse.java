package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.api.usuario;

import java.time.LocalDate;

public record UsuarioResponse(
        String nome,
        String email,
        EnderecoDTO endereco,
        LocalDate dataNascimento,
        String status
) {
}
