package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.api.arquivo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ArquivoJpaRepository extends JpaRepository<ArquivoEntity, Long> {

    @Query("SELECT a.nomeArquivo AS nome FROM ArquivoEntity a")
    Page<ArquivoProjection> findArquivos(Pageable paginacao);

    @Query("""
           SELECT a.nomeArquivo AS nomeArquivo,
                  a.conteudo AS conteudo
           FROM ArquivoEntity a
           WHERE a.idUsuarioOwner = :idUsuario
           """)
    Page<ArquivoComConteudoProjection> findArquivosDoUsuario(@Param("idUsuario") Long idUsuario, Pageable paginacao);

}
