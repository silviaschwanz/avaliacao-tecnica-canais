package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.api.usuario;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "${api.version}/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Operation(summary = "Entrega uma lista com todos os usuários presentes na base de dados")
    @ApiResponse(responseCode = "200", description = "Lista de usuários existentes na base de dados")
    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> listarUsuarios(@PageableDefault(sort = {"nome"}) Pageable paginacao) {
        var usuarios = usuarioService.listarUsuarios(paginacao);
        return ResponseEntity.ok().body(usuarios);
    }

    @Operation(summary = "Retorna as informações completas de um usuário específico")
    @ApiResponse(responseCode = "200", description = "Informações completas do usuário solicitado")
    @GetMapping("/{idUsuario}")
    public ResponseEntity<UsuarioResponse> detalharUsuario(@PathVariable Long idUsuario) {
        var usuario = usuarioService.detalharUsuario(idUsuario);
        return ResponseEntity.ok().body(usuario);
    }

    @Operation(summary = "Retorna as informações simplificadas de um usuário específico")
    @ApiResponse(responseCode = "200", description = "Informações simplificadas do usuário solicitado")
    @GetMapping("/{idUsuario}/simplificado")
    public ResponseEntity<UsuarioSimplificadoResponse> detalharUsuarioSimplificado(@PathVariable Long idUsuario) {
        var usuario = usuarioService.detalharUsuarioSimplificado(idUsuario);
        return ResponseEntity.ok().body(usuario);
    }

}
