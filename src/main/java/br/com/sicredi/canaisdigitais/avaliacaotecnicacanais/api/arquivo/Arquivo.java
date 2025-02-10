package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.api.arquivo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;

@Getter
public class Arquivo {

    private String nome;
    private Conteudo conteudo;

    public Arquivo(String nome) {
        stringNotNullOrIsBlank(nome);
        this.nome = nome;
    }

    public Arquivo(String nome, String conteudo) {
        stringNotNullOrIsBlank(nome);
        this.nome = nome;
        this.conteudo = converterConteudo(conteudo);
    }

    public static Arquivo restaurar(String nome) {
        return new Arquivo(nome);
    }

    public static Arquivo restaurarComConteudo(String nome, String conteudo) {
        return new Arquivo(nome, conteudo);
    }

    private void stringNotNullOrIsBlank(String field) {
        if(field == null || field.isBlank()) {
            throw new IllegalArgumentException("O parâmetro nomeArquivo não pode ser nulo, " +
                    "vazio ou estar em branco");
        }
    }

    private Conteudo converterConteudo(String conteudo) {
        try {
            ObjectMapper objectMapper =  new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.readValue(conteudo, Conteudo.class);
        } catch (JsonProcessingException exception) {
            throw new IllegalArgumentException("Erro ao mapear o JSON conteudo para o objeto Conteudo ", exception);
        }
    }

}
