package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ConteudoDTO {

    private String titulo;
    private LocalDate dataPublicacao;
    private String tags;

}
