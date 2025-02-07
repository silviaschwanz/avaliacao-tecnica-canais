package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.infra.gateways;

import br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.infra.persistence.Arquivo;
import br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.infra.persistence.ArquivoRepository;
import br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.infra.controller.ArquivoDTO;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ArquivoService {

    private final ArquivoRepository arquivoRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public List<ArquivoDTO> listarTodosArquivos() {
        return arquivoRepository.findAll().stream().map(arquivo -> toDTO(arquivo, false)).toList();
    }

    @SneakyThrows
    private ArquivoDTO toDTO(Arquivo arquivo, boolean comConteudo) {
        var arquivoDTO = new ArquivoDTO();
        arquivoDTO.setNomeArquivo(arquivo.getNomeArquivo());
        arquivoDTO.setIdUsuarioOwner(arquivo.getIdUsuarioOwner());

        if (comConteudo) {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
            var conteudo = objectMapper.readValue(arquivo.getConteudo(), ConteudoDTO.class);
            arquivoDTO.setConteudo(conteudo);
        }

        return arquivoDTO;
    }

    public List<ArquivoDTO> listarArquivosUsuario(Long idUsuario) {
        return arquivoRepository.findByIdUsuarioOwner(idUsuario).stream().map(arquivo -> toDTO(arquivo, true)).toList();
    }

}
