package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.api.arquivo;

import lombok.Getter;

@Getter
public class Arquivo {

    private final String nome;
    private Conteudo conteudo;

    public Arquivo(String nome) {
        stringNotNullOrIsBlank(nome);
        this.nome = nome;
    }

    public Arquivo(String nome, Conteudo conteudo) {
        stringNotNullOrIsBlank(nome);
        this.nome = nome;
        this.conteudo = conteudo;
    }

    public static Arquivo restaurar(String nome) {
        return new Arquivo(nome);
    }

    public static Arquivo restaurarComConteudo(String nome, Conteudo conteudo) {
        return new Arquivo(nome, conteudo);
    }

    private void stringNotNullOrIsBlank(String field) {
        if(field == null || field.isBlank()) {
            throw new IllegalArgumentException("O parâmetro nome não pode ser nulo, " +
                    "vazio ou estar em branco");
        }
    }

}
