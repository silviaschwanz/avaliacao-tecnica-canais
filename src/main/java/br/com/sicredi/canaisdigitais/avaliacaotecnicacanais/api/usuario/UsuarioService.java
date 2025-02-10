package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.api.usuario;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService implements UsuarioRepository{

    @Autowired
    UsuarioJpaRepository usuarioRepository;

    public Usuario detalharUsuario(Long idUsuario) {
        UsuarioEntity usuarioEntity = buscarUsuario(idUsuario);
        return restaurar(usuarioEntity);
    }

    public Usuario detalharUsuarioSimplificado(Long idUsuario) {
        UsuarioEntity usuarioEntity = buscarUsuario(idUsuario);
        return Usuario.restaurarSimplificado(usuarioEntity.getNome(), usuarioEntity.getEmail());
    }

    public List<Usuario> listarUsuarios(Pageable paginacao) {
        try {
            Page<UsuarioEntity> usuarios = usuarioRepository.findAll(paginacao);
            if (usuarios.isEmpty()) {
                throw new EntityNotFoundException("Não há usuários na base de dados");
            }
            return usuarios.stream().map(this::restaurar).toList();
        } catch (EntityNotFoundException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private Usuario restaurar(UsuarioEntity usuario) {
        return Usuario.restaurar(
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getEndereco(),
                usuario.getDataNascimento(),
                usuario.getStatus()
        );
    }

    private UsuarioEntity buscarUsuario(Long idUsuario) {
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
