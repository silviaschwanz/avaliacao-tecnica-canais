package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.api.usuario;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class Usuario {

    private String nome;
    private String email;
    private Endereco endereco;
    private LocalDate dataNascimento;
    private Boolean status;

    private Usuario(String nome, String email, String endereco, LocalDate dataNascimento, String status) {
        stringNotNullOrIsBlank(nome, "nome");
        stringNotNullOrIsBlank(email, "email");
        this.nome = nome;
        this.email = email;
        this.endereco = converterEndereco(endereco);
        this.dataNascimento = dataNascimento;
        this.status = "ATIVO".equals(status);
    }

    private Usuario(String nome, String email) {
        stringNotNullOrIsBlank(nome, "nome");
        stringNotNullOrIsBlank(email, "email");
        this.nome = nome;
        this.email = email;
    }

    public static Usuario restaurar(String nome, String email, String endereco, LocalDate dataNascimento, String status) {
        return new Usuario(nome, email, endereco, dataNascimento, status);
    }

    public static Usuario restaurarSimplificado(String nome, String email) {
        return new Usuario(nome, email);
    }

    private void stringNotNullOrIsBlank(String field, String nameField) {
        if(field == null || field.isBlank()) {
            throw new IllegalArgumentException("O parâmetro " + nameField + " não pode ser nulo, " +
                    "vazio ou estar em branco");
        }
    }

    private Endereco converterEndereco(String endereco) {
        try {
            ObjectMapper objectMapper =  new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return objectMapper.readValue(endereco, Endereco.class);
        } catch (JsonProcessingException exception) {
            throw new IllegalArgumentException("Erro ao mapear o JSON endereco para o objeto Endereço ", exception);
        }
    }

}
