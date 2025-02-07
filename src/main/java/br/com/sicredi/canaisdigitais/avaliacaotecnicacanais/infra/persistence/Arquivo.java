package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.infra.persistence;

import jakarta.persistence.*;
import lombok.Data;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "ARQUIVO")
@Data
public class Arquivo {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @Column(name = "NOME")
    private String nomeArquivo;

    @Column(name = "ID_USUARIO")
    private Long idUsuarioOwner;

    @Column(name = "CONTEUDO")
    private String conteudo;

}