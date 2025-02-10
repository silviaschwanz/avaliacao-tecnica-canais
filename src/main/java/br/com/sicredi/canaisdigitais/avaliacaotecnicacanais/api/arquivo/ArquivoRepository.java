package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.api.arquivo;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArquivoRepository {

    List<Arquivo> listarTodosArquivos(Pageable paginacao);

    List<Arquivo> listarArquivosUsuario(Long idUsuario, Pageable paginacao);

}
