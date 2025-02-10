package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.api.usecases;

import br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.api.arquivo.Arquivo;
import br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.api.arquivo.ArquivoRepository;
import br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.api.arquivo.ArquivoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListarArquivos {

    @Autowired
    ArquivoRepository arquivoRepository;

    public List<ArquivoResponse> execute(Pageable paginacao) {
        List<Arquivo> arquivos = arquivoRepository.listarTodosArquivos(paginacao);
        return arquivos.stream().map(arquivo -> new ArquivoResponse(arquivo.getNome())).toList();
    }

}
