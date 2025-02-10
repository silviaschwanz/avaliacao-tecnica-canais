package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.api.usecases;

import br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.api.usuario.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetalharUsuario {

    @Autowired
    UsuarioRepository usuarioRepository;

    public UsuarioResponse execute(Long idUsuario) {
        Usuario usuario = usuarioRepository.detalharUsuario(idUsuario);
        return new UsuarioResponse(
                usuario.getNome(),
                usuario.getEmail(),
                new EnderecoResponse(
                        usuario.getEndereco().logradouro(),
                        usuario.getEndereco().numero(),
                        usuario.getEndereco().cidade(),
                        usuario.getEndereco().bairro(),
                        usuario.getEndereco().estado()
                ),
                usuario.getDataNascimento(),
                usuario.getStatus()
        );
    }

}
