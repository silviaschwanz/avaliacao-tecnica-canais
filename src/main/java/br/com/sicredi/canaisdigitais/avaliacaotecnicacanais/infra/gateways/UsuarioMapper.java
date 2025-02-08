package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.infra.gateways;

import br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.infra.controller.usuario.EnderecoDTO;
import br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.infra.controller.usuario.UsuarioResponse;
import br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.infra.persistence.Usuario;
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

    public UsuarioResponse toReponse(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getNome(),
                usuario.getEmail(),
                converterEndereco(usuario.getEndereco()),
                usuario.getDataNascimento(),
                usuario.getStatus()
        );
    }

    public EnderecoDTO converterEndereco(String endereco) {
        try {
            return objectMapper.readValue(endereco, EnderecoDTO.class);
        } catch (JsonProcessingException exception) {
            throw new IllegalArgumentException("Erro ao mapear o JSON para o objeto Endereço ", exception);
        }
    }

}
