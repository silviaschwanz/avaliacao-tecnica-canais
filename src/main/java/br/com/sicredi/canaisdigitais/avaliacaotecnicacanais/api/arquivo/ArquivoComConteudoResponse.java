package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.api.arquivo;

import java.time.LocalDate;
import java.util.List;

public record ArquivoComConteudoResponse(
        String nomeArquivo,
        String titulo,
        LocalDate dataPublicacao,
        String tags
) {
}
