package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.api.usuario;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @InjectMocks
    UsuarioService usuarioService;

    @Mock
    UsuarioJpaRepository usuarioRepository;

    @Test
    void retornarUsuarioResponseQuandoDetalharUsuario() {
        UsuarioEntity usuarioEntityMock = new UsuarioEntity();
        usuarioEntityMock.setId(1);
        usuarioEntityMock.setNome("João Silva");
        usuarioEntityMock.setEmail("joao.silva@example.com");
        usuarioEntityMock.setEndereco("{ \"logradouro\": \"Rua Exemplo\", \"numero\": 123, \"cidade\": \"Cidade X\", \"bairro\": \"Bairro Y\", \"estado\": \"RS\" }");
        usuarioEntityMock.setDataNascimento(LocalDate.of(1990, 5, 20));
        usuarioEntityMock.setStatus("ATIVO");
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioEntityMock));
        Usuario result = usuarioService.detalharUsuario(1L);
        assertNotNull(result);
        assertNotNull(result.getEndereco());
        verify(usuarioRepository, times(1)).findById(1L);
    }

    @Test
    void runtimeExceptionQuandoRepositorioFalhar() {
        Pageable paginacao = Pageable.ofSize(10);
        when(usuarioRepository.findAll(paginacao)).thenThrow(new IllegalStateException("Erro de banco de dados"));
        RuntimeException thrownException = assertThrows(RuntimeException.class, () -> {
            usuarioService.listarUsuarios(paginacao);
        });
        assertThrows(IllegalStateException.class, () -> {
            throw thrownException.getCause();
        });
        verify(usuarioRepository, times(1)).findAll(paginacao);
    }

    @Test
    void entityNotFoundExceptionQuandoNaoHouverUsuarios() {
        Pageable paginacao = Pageable.ofSize(10);
        when(usuarioRepository.findAll(paginacao)).thenReturn(Page.empty());
        EntityNotFoundException thrownException = assertThrows(EntityNotFoundException.class, () -> {
            usuarioService.listarUsuarios(paginacao);
        });
        assertThrows(EntityNotFoundException.class, () -> {
            throw thrownException;
        });
        verify(usuarioRepository, times(1)).findAll(paginacao);
    }

}