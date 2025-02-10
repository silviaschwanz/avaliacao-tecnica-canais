package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.api.usecases;

import br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.api.usuario.Usuario;
import br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.api.usuario.UsuarioRepository;
import br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.api.usuario.UsuarioSimplificadoResponse;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DetalharUsuarioSimplificadoTest {

    @Mock
    UsuarioRepository usuarioRepository;

    @InjectMocks
    DetalharUsuarioSimplificado detalharUsuarioSimplificado;

    @Test
    void deveRetornarUsuarioSimplificadoQuandoUsuarioExiste() {
        Long idUsuario = 1L;
        Usuario usuarioMock = Usuario.restaurarSimplificado("João Silva", "joao.silva@example.com");
        when(usuarioRepository.detalharUsuarioSimplificado(idUsuario)).thenReturn(usuarioMock);
        UsuarioSimplificadoResponse response = detalharUsuarioSimplificado.execute(idUsuario);
        assertEquals("João Silva", response.nome());
        assertEquals("joao.silva@example.com", response.email());
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoExiste() {
        Long idUsuario = 999L;
        when(usuarioRepository.detalharUsuarioSimplificado(idUsuario))
                .thenThrow(new EntityNotFoundException("Usuário não encontrado"));
        assertThrows(EntityNotFoundException.class, () -> detalharUsuarioSimplificado.execute(idUsuario));
    }

    @Test
    void deveLancarExcecaoQuandoIdUsuarioNulo() {
        assertThrows(IllegalArgumentException.class, () -> detalharUsuarioSimplificado.execute(null));
    }

    @Test
    void deveChamarRepositorioComIdCorreto() {
        Long idUsuario = 1L;
        Usuario usuarioMock = Usuario.restaurarSimplificado("João Silva", "joao.silva@example.com");
        when(usuarioRepository.detalharUsuarioSimplificado(idUsuario)).thenReturn(usuarioMock);
        detalharUsuarioSimplificado.execute(idUsuario);
        verify(usuarioRepository, times(1)).detalharUsuarioSimplificado(idUsuario);
    }

    @Test
    void deveRetornarExcecaoLancadaPeloRepositorio() {
        Long idUsuario = 2L;
        when(usuarioRepository.detalharUsuarioSimplificado(idUsuario)).thenThrow(new IllegalArgumentException(
               "O parâmetro nome não pode ser nulo, vazio ou estar em branco"
        ));
        assertThrows(IllegalArgumentException.class, () -> detalharUsuarioSimplificado.execute(idUsuario));
    }

}