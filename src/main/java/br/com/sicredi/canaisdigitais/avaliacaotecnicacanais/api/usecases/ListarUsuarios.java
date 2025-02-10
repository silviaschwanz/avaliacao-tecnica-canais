package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.api.usecases;

import br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.api.usuario.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListarUsuarios {

    @Autowired
    UsuarioRepository usuarioRepository;

    public List<UsuarioResponse> execute(Pageable paginacao) {
        List<Usuario> usuarios = usuarioRepository.listarUsuarios(paginacao);
        return usuarios.stream().map(this::toResponse).toList();
    }

    public UsuarioResponse toResponse(Usuario usuario) {
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
