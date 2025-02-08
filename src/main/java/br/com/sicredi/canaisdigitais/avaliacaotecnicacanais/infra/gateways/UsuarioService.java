package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.infra.gateways;

import br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.infra.controller.usuario.UsuarioResponse;
import br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.infra.controller.usuario.UsuarioSimplificadoResponse;
import br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.infra.persistence.Usuario;
import br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.infra.persistence.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    @Transactional
    public UsuarioResponse detalharUsuario(Long idUsuario) {
        var optionalUsuario = usuarioRepository.findById(idUsuario);

        if (optionalUsuario.isEmpty()) {
            return null;
        }
        var usuario = optionalUsuario.get();
        return new UsuarioResponse(
                usuario.getNome(),
                usuario.getEmail(),
                usuarioMapper.converterEndereco(usuario.getEndereco()),
                usuario.getDataNascimento(),
                usuario.getStatus()
        );
    }

    @Transactional
    public UsuarioSimplificadoResponse detalharUsuarioSimplificado(Long idUsuario) {
        var optionalUsuario = usuarioRepository.findById(idUsuario);

        if (optionalUsuario.isEmpty()) {
            return null;
        }
        var usuario = optionalUsuario.get();
        return new UsuarioSimplificadoResponse(
                usuario.getNome(),
                usuario.getEmail()
        );
    }

    public List<UsuarioResponse> listarUsuarios(Pageable paginacao) {
        Page<Usuario> usuarios = usuarioRepository.findAll(paginacao);
        if(usuarios.isEmpty()) {
            throw new EntityNotFoundException("Não há usuários na base de dados");
        }
        return usuarios.stream().map(usuarioMapper::toReponse).toList();
    }

}
