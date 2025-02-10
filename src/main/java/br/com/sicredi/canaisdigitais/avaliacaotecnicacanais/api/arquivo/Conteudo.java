package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.api.arquivo;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public record Conteudo(
        String titulo,
        LocalDate dataPublicacao,
        List<String> tags
) {

    public Conteudo(String titulo, LocalDate dataPublicacao, String tagsString) {
        this(titulo, dataPublicacao, Arrays.asList(tagsString.split(";")));
    }

}
