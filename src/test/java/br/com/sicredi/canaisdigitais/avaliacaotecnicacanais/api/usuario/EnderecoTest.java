package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.api.usuario;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EnderecoTest {

    @Test
    void criarEnderecoValido() {
        Endereco endereco = new Endereco(
                "Rua das Palmeiras",
                123,
                "Cidade X",
                "Bairro Y",
                "RS"
        );
    assertEquals("Rua das Palmeiras", endereco.logradouro());
    assertEquals(123, endereco.numero());
    assertEquals("Cidade X", endereco.cidade());
    assertEquals("Bairro Y", endereco.bairro());
    assertEquals("RS", endereco.estado());

    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" "})
    void naoDeveCriarEnderecoComLogradouroInvalido(String logradouro) {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Endereco(
                    logradouro,
                    123,
                    "Cidade X",
                    "Bairro y",
                    "RS"
            );
        });
        String mensagemEsperada = "O parâmetro logradouro não pode ser nulo, vazio ou estar em branco";
        assertEquals(mensagemEsperada, exception.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" "})
    void naoDeveCriarEnderecoComCidadeInvalida(String cidade) {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Endereco(
                    "Rua das Palmeiras",
                    123,
                    cidade,
                    "Bairro y",
                    "RS"
            );
        });
        String mensagemEsperada = "O parâmetro cidade não pode ser nulo, vazio ou estar em branco";
        assertEquals(mensagemEsperada, exception.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" "})
    void naoDeveCriarEnderecoComBairroInvalida(String bairro) {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Endereco(
                    "Rua das Palmeiras",
                    123,
                    "Cidade X",
                    bairro,
                    "RS"
            );
        });
        String mensagemEsperada = "O parâmetro bairro não pode ser nulo, vazio ou estar em branco";
        assertEquals(mensagemEsperada, exception.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" "})
    void naoDeveCriarEnderecoComEstadoInvalida(String estado) {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Endereco(
                    "Rua das Palmeiras",
                    123,
                    "Cidade X",
                    "Bairro y",
                    estado
            );
        });
        String mensagemEsperada = "O parâmetro estado não pode ser nulo, vazio ou estar em branco";
        assertEquals(mensagemEsperada, exception.getMessage());
    }

    @Test
    void naoDeveCriarEnderecoComNumeroInvalido() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Endereco(
                    "Rua das Palmeiras",
                    (int) -1.2,
                    "Cidade X",
                    "Bairro y",
                    "RS"
            );
        });
        assertEquals("O numero deve ser maior que zero", exception.getMessage());
    }

}