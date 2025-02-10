package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.api.arquivo;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public record Conteudo(
        String titulo,
        LocalDate dataPublicacao,
        String tags
) {

}
