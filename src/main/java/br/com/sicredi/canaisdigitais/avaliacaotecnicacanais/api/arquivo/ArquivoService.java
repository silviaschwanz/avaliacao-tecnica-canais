package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.api.arquivo;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArquivoService implements ArquivoRepository{

    @Autowired
    ArquivoJpaRepository arquivoJpaRepository;

    public List<Arquivo> listarTodosArquivos(Pageable paginacao) {
        Page<ArquivoProjection> arquivos = arquivoJpaRepository.findArquivos(paginacao);
        if (arquivos.isEmpty()) {
            throw new EntityNotFoundException("Não há arquivos na base de dados");
        }
        return arquivos.stream().map(arquivo -> Arquivo.restaurar(arquivo.getNome())).toList();
    }

    public List<Arquivo> listarArquivosUsuario(Long idUsuario, Pageable paginacao) {
        Page<ArquivoComConteudoProjection> arquivosProjection = arquivoJpaRepository.findArquivosDoUsuario(idUsuario, paginacao);
        if (arquivosProjection.isEmpty()) {
            throw new EntityNotFoundException("Não há arquivos na base de dados");
        }
        return arquivosProjection.stream()
                .map(projection -> Arquivo.restaurarComConteudo(
                        projection.getNomeArquivo(),
                        projection.getConteudo()
                ))
                .collect(Collectors.toList());
    }

}
