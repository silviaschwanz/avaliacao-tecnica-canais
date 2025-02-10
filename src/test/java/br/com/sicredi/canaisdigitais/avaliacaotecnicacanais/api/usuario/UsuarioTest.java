package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.api.usuario;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


class UsuarioTest {

    @Test
    void restaurarUsuarioEmEstadoValido() {
        Usuario usuario = Usuario.restaurar(
                "João Silva",
                "joao.silva@example.com",
                "{ \"logradouro\": \"Rua Exemplo\", \"numero\": 123, \"cidade\": \"Cidade X\", \"bairro\": \"Bairro Y\", \"estado\": \"RS\" }",
                LocalDate.of(1990, 5, 20),
                "ATIVO"
        );
        Endereco enderecoEsperado = new Endereco(
                "Rua Exemplo",
                123,
                "Cidade X",
                "Bairro Y",
                "RS"
        );
        assertEquals("João Silva", usuario.getNome());
        assertEquals("joao.silva@example.com", usuario.getEmail());
        assertEquals(enderecoEsperado, usuario.getEndereco());
        assertEquals(LocalDate.of(1990, 5, 20), usuario.getDataNascimento());
        assertTrue(usuario.getStatus());
    }

    @Test
    void erroParaConverterEnderecoQuandoRestauraUsuario() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> Usuario.restaurar(
                        "João Silva",
                        "joao.silva@example.com",
                        "{Rua Exemplo: 123, Cidade X, Bairro Y, RS}",
                        LocalDate.of(1990, 5, 20),
                        "ATIVO"
                )
        );
        assertEquals("Erro ao mapear o JSON endereco para o objeto Endereço ", exception.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" "})
    void naoDeveRestaurarUsuarioComNomeInvalido(String nome) {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Usuario.restaurar(
                    nome,
                    "joao.silva@example.com",
                    "{ \"logradouro\": \"Rua Exemplo\", \"numero\": 123, \"cidade\": \"Cidade X\", \"bairro\": \"Bairro Y\", \"estado\": \"RS\" }",
                    LocalDate.of(1990, 5, 20),
                    "ATIVO"
            );
        });
        String mensagemEsperada = "O parâmetro nome não pode ser nulo, vazio ou estar em branco";
        Assertions.assertEquals(mensagemEsperada, exception.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" "})
    void naoDeveRestaurarUsuarioComEmailInvalido(String email) {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Usuario.restaurar(
                    "João Silva",
                    email,
                    "{ \"logradouro\": \"Rua Exemplo\", \"numero\": 123, \"cidade\": \"Cidade X\", \"bairro\": \"Bairro Y\", \"estado\": \"RS\" }",
                    LocalDate.of(1990, 5, 20),
                    "ATIVO"
            );
        });
        String mensagemEsperada = "O parâmetro email não pode ser nulo, vazio ou estar em branco";
        Assertions.assertEquals(mensagemEsperada, exception.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" "})
    void naoDeveRestaurarUsuarioSimplificadoComNomeInvalido(String nome) {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Usuario.restaurarSimplificado(
                    nome,
                    "joao.silva@example.com"
            );
        });
        String mensagemEsperada = "O parâmetro nome não pode ser nulo, vazio ou estar em branco";
        Assertions.assertEquals(mensagemEsperada, exception.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" "})
    void naoDeveRestaurarUsuarioSimplificadoComEmailInvalido(String email) {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Usuario.restaurarSimplificado(
                    "João Silva",
                    email
            );
        });
        String mensagemEsperada = "O parâmetro email não pode ser nulo, vazio ou estar em branco";
        Assertions.assertEquals(mensagemEsperada, exception.getMessage());
    }


}