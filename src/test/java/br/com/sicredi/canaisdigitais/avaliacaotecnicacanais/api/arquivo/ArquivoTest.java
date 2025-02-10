package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.api.arquivo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ArquivoTest {

    @Test
    void restaurarArquivoComNomeValido() {
        Arquivo arquivo = Arquivo.restaurar("arquivo213.txt");
        assertEquals("arquivo213.txt", arquivo.getNome());

    }

    @Test
    void restaurarArquivoComConteudo() {
        String conteudoJson = "{"
                + "\"titulo\": \"Estudo Sobre Microbiologia\", "
                + "\"dataPublicacao\": \"2005-03-27\", "
                + "\"tags\": [\"cientifico\", \"experimento\", \"biologia\", \"validacao\", \"microbiota\"]"
                + "}";
        Arquivo arquivo = Arquivo.restaurarComConteudo("arquivo213.txt", conteudoJson);
        assertEquals("arquivo213.txt", arquivo.getNome());
        assertEquals("Estudo Sobre Microbiologia", arquivo.getConteudo().titulo());
        assertEquals(LocalDate.of(2005,03,27), arquivo.getConteudo().dataPublicacao());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" "})
    void naoDeveRestaurarArquivoComNomeInvalido(String nome) {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Arquivo.restaurar(nome);
        });
        String mensagemEsperada = "O parâmetro nomeArquivo não pode ser nulo, vazio ou estar em branco";
        assertEquals(mensagemEsperada, exception.getMessage());
    }

}