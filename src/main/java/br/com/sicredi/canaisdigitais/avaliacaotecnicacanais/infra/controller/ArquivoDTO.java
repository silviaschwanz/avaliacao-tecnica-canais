package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.infra.controller;

import br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.infra.gateways.ConteudoDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArquivoDTO {

    private String nomeArquivo;
    private Long idUsuarioOwner;
    private ConteudoDTO conteudo;

}