package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsuarioDTO {

    @NotBlank
    private String nome;

    @NotBlank
    private String email;

    @NotBlank
    private EnderecoDTO endereco;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataNascimento;

    @NotNull
    private Boolean ativo;

}
