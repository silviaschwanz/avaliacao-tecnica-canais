package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.api.usecases;

import br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.api.arquivo.ArquivoRepository;
import br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.api.arquivo.ArquivoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListarArquivos {

    @Autowired
    ArquivoRepository arquivoRepository;

    public ArquivoResponse executar() {

    }

}
