package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.api.arquivo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ArquivoTest {

    @Test
    void restaurarArquivoComNomeValido() {
        Arquivo arquivo = Arquivo.restaurar("arquivo213.txt");
        assertEquals("arquivo213.txt", arquivo.getNome());

    }

    @Test
    void restaurarArquivoComConteudo() {
        String titulo = "Inflação e Impactos na Sociedade";
        LocalDate dataPublicacao = LocalDate.parse("2012-08-22");
        String tagsString = "economia;inflacao;governo;taxadejuros";
        Conteudo conteudo = new Conteudo(titulo, dataPublicacao, tagsString);
        Arquivo arquivo = Arquivo.restaurarComConteudo("arquivo213.txt", conteudo);
        assertEquals("arquivo213.txt", arquivo.getNome());
        assertEquals(titulo, arquivo.getConteudo().titulo());
        assertEquals(dataPublicacao, arquivo.getConteudo().dataPublicacao());
        var tagsEsperadas = Arrays.asList(tagsString.split(";"));
        assertEquals(tagsEsperadas, arquivo.getConteudo().tags());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" "})
    void naoDeveRestaurarArquivoComNomeInvalido(String nome) {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Arquivo.restaurar(nome);
        });
        String mensagemEsperada = "O parâmetro nome não pode ser nulo, vazio ou estar em branco";
        assertEquals(mensagemEsperada, exception.getMessage());
    }

}