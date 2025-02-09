package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.api.usuario;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioMapper {

    private final ObjectMapper objectMapper;

    @Autowired
    public UsuarioMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public UsuarioResponse toResponse(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getNome(),
                usuario.getEmail(),
                converterEndereco(usuario.getEndereco()),
                usuario.getDataNascimento(),
                "ATIVO".equals(usuario.getStatus())
        );
    }

    public UsuarioSimplificadoResponse toResponseSimplificada(Usuario usuario) {
        return new UsuarioSimplificadoResponse(usuario.getNome(), usuario.getEmail());
    }

    public Endereco converterEndereco(String endereco) {
        try {
            return objectMapper.readValue(endereco, Endereco.class);
        } catch (JsonProcessingException exception) {
            throw new IllegalArgumentException("Erro ao mapear o JSON endereco para o objeto Endereço ", exception);
        }
    }

}
