package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.api.usecases;

import br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.api.usuario.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetalharUsuarioSimplificado {

    @Autowired
    UsuarioRepository usuarioRepository;

    public UsuarioSimplificadoResponse execute(Long idUsuario) {
        if (idUsuario == null) {
            throw new IllegalArgumentException("O ID do usuário não pode ser nulo");
        }
        Usuario usuario = usuarioRepository.detalharUsuarioSimplificado(idUsuario);
        return new UsuarioSimplificadoResponse(
                usuario.getNome(),
                usuario.getEmail()
        );
    }

}
