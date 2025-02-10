package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.api.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioJpaRepository extends JpaRepository<UsuarioEntity, Long> {

    @Query("SELECT u.nome AS nome, u.email AS email FROM UsuarioEntity u WHERE u.id = :idUsuario")
    Optional<UsuarioSimplificadoProjection> findUsuarioSimplificadoById(@Param("idUsuario") Long idUsuario);

}
