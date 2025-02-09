package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.api.usuario;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioJpaRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    @Autowired
    public UsuarioService(UsuarioJpaRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    public UsuarioResponse detalharUsuario(Long idUsuario) {
        return usuarioMapper.toResponse(buscarUsuario(idUsuario));
    }

    public UsuarioSimplificadoResponse detalharUsuarioSimplificado(Long idUsuario) {
        return usuarioMapper.toResponseSimplificada(buscarUsuario(idUsuario));
    }

    public List<UsuarioResponse> listarUsuarios(Pageable paginacao) {
        try {
            Page<Usuario> usuarios = usuarioRepository.findAll(paginacao);
            if (usuarios.isEmpty()) {
                throw new EntityNotFoundException("Não há usuários na base de dados");
            }
            return usuarios.stream().map(usuarioMapper::toResponse).toList();
        } catch (EntityNotFoundException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private Usuario buscarUsuario(Long idUsuario) {
        try {
            return usuarioRepository.findById(idUsuario)
                    .orElseThrow(() -> new EntityNotFoundException("Usuário de id " + idUsuario + "não encontrado"));
        } catch (EntityNotFoundException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

}
