package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.api.usuario;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UsuarioRepository {

    Usuario detalharUsuario(Long idUsuario);

    Usuario detalharUsuarioSimplificado(Long idUsuario);

    List<Usuario> listarUsuarios(Pageable paginacao);

}
