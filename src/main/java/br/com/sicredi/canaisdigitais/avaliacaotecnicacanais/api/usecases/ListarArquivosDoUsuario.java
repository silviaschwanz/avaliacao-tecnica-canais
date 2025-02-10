package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.api.usecases;

import br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.api.arquivo.Arquivo;
import br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.api.arquivo.ArquivoComConteudoResponse;
import br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.api.arquivo.ArquivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListarArquivosDoUsuario {

    @Autowired
    ArquivoRepository arquivoRepository;

    public List<ArquivoComConteudoResponse> execute(Long idUsuario, Pageable paginacao) {
        List<Arquivo> arquivos = arquivoRepository.listarArquivosUsuario(idUsuario, paginacao);
        return arquivos.stream().map(this::toResponse).toList();
    }

    private ArquivoComConteudoResponse toResponse(Arquivo arquivo) {
        return new ArquivoComConteudoResponse(
                arquivo.getNome(),
                arquivo.getConteudo().titulo(),
                arquivo.getConteudo().dataPublicacao(),
                arquivo.getConteudo().tags()
        );
    }

}
