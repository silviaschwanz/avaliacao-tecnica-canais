package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.infra.gateways;

import br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.infra.controller.EnderecoDTO;
import br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.infra.controller.UsuarioDTO;
import br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.infra.persistence.UsuarioRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
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
    public UsuarioDTO detalharUsuario(Long idUsuario) {
        var optionalUsuario = usuarioRepository.findById(idUsuario);

        if (optionalUsuario.isEmpty()) {
            return null;
        }
        var usuario = optionalUsuario.get();

        var usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome(usuario.getNome());
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setDataNascimento(usuario.getDataNascimento());
        usuarioDTO.setEndereco(converterEndereco(usuario.getEndereco()));
        usuarioDTO.setAtivo("ATIVO".equals(usuario.getStatus()) ? true : false );
        return usuarioDTO;
    }

    @Transactional
    public UsuarioDTO detalharUsuarioSimplificado(Long idUsuario) {
        var optionalUsuario = usuarioRepository.findById(idUsuario);

        if (optionalUsuario.isEmpty()) {
            return null;
        }
        var usuario = optionalUsuario.get();

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        var usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome(usuario.getNome());
        usuarioDTO.setEmail(usuario.getEmail());
        return usuarioDTO;
    }

    private EnderecoDTO converterEndereco(String endereco) {
        try {
            return objectMapper.readValue(endereco, EnderecoDTO.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
//
//INSERT INTO USUARIO (NOME, EMAIL, ENDERECO, DATA_NASCIMENTO, STATUS) VALUES ('João Paulo Fonseca', 'joaopfonseca@gmail.com', '{ "logradouro": "Rua dos Andradas", "numero": 564, "cidade": "Gravataí", "bairro": "Centro", "estado": "RS" }', '1941-01-21', 'ATIVO');
//INSERT INTO USUARIO (NOME, EMAIL, ENDERECO, DATA_NASCIMENTO) VALUES ('Vitor Pereira da Costa', 'vitorpcosta@hotmail.com', '{ "logradouro": "Avenida Burguesia", "numero": 111, "cidade": "Belém", "bairro": "Rio Branco", "estado": "PA" }', '1974-09-25', 'ATIVO');
//INSERT INTO USUARIO (NOME, EMAIL, ENDERECO, DATA_NASCIMENTO) VALUES ('Regina Maria Silva', 'reginasilva@outlook.com', '{ "logradouro": "Rua dos Ingleses", "numero": 956, "cidade": "Rio de Janeiro", "bairro": "Copacabana", "estado": "RJ", "cep": "31014360" }', '1998-01-01', 'ATIVO');
//INSERT INTO USUARIO (NOME, EMAIL, ENDERECO, DATA_NASCIMENTO) VALUES ('Maria Joaquina', 'joaquinamaria@gmail.com', '{ "logradouro": "Alameda de Barros", "numero": 54, "cidade": "São Paulo", "bairro": "Morumbi", "estado": "SP", "cep": "44045340" }', '1984-07-30', 'INATIVO');