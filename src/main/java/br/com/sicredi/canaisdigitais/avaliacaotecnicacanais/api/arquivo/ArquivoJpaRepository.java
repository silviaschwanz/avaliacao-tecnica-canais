package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.api.arquivo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArquivoJpaRepository extends JpaRepository<ArquivoEntity, Long> {

    List<ArquivoEntity> findByIdUsuarioOwner(Long idUsuario);

}
