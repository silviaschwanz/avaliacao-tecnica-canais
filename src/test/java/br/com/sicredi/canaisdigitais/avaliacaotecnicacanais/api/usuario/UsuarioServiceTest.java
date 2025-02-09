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

    @Mock
    private UsuarioMapper usuarioMapper;

    @Test
    void returnaUsuarioResponseQuandoDetalharUsuario() {
        Usuario usuarioMock = new Usuario();
        usuarioMock.setId(1);
        usuarioMock.setNome("João Silva");
        usuarioMock.setEmail("joao.silva@example.com");
        usuarioMock.setEndereco("Rua Exemplo, 123, Cidade X, Bairro Y, RS");
        usuarioMock.setDataNascimento(LocalDate.of(1990, 5, 20));
        usuarioMock.setStatus("ATIVO");
        Endereco enderecoResponseMock = new Endereco(
                "Rua Exemplo",
                123,
                "Cidade X",
                "Bairro Y",
                "RS"
        );
        UsuarioResponse usuarioResponseMock = new UsuarioResponse(
                "João Silva",
                "joao.silva@example.com",
                enderecoResponseMock,
                LocalDate.of(1990, 5, 20),
                "ATIVO"
        );
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioMock));
        when(usuarioMapper.toResponse(usuarioMock)).thenReturn(usuarioResponseMock);
        UsuarioResponse result = usuarioService.detalharUsuario(1L);
        assertNotNull(result);
        assertNotNull(result.endereco());
        verify(usuarioRepository, times(1)).findById(1L);
        verify(usuarioMapper, times(1)).toResponse(usuarioMock);
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