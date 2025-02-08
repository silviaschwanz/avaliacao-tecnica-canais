package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.infra.gateways;

import br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.infra.controller.usuario.DetalharUsuarioResponse;
import br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.infra.controller.usuario.DetalharUsuarioSimplificadoResponse;
import br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.infra.controller.usuario.EnderecoDTO;
import br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.infra.persistence.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public DetalharUsuarioResponse detalharUsuario(Long idUsuario) {
        var optionalUsuario = usuarioRepository.findById(idUsuario);

        if (optionalUsuario.isEmpty()) {
            return null;
        }
        var usuario = optionalUsuario.get();
        return new DetalharUsuarioResponse(
                usuario.getNome(),
                usuario.getEmail(),
                converterEndereco(usuario.getEndereco()),
                usuario.getDataNascimento(),
                usuario.getStatus()
        );
    }

    @Transactional
    public DetalharUsuarioSimplificadoResponse detalharUsuarioSimplificado(Long idUsuario) {
        var optionalUsuario = usuarioRepository.findById(idUsuario);

        if (optionalUsuario.isEmpty()) {
            return null;
        }
        var usuario = optionalUsuario.get();
        return new DetalharUsuarioSimplificadoResponse(
                usuario.getNome(),
                usuario.getEmail()
        );
    }

    private EnderecoDTO converterEndereco(String endereco) {
        try {
            return objectMapper.readValue(endereco, EnderecoDTO.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
