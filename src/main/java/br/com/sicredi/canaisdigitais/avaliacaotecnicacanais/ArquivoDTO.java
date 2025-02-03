package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArquivoDTO {

    private String nomeArquivo;
    private Long idUsuarioOwner;
    private ConteudoDTO conteudo;

}