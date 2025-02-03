package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;

    @Operation(summary = "Entrega uma lista com todos os usuários presentes na base de dados")
    @ApiResponse(responseCode = "200", description = "Lista de usuários existentes na base de dados")
    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        var usuarios = usuarioRepository.findAll();
        return ResponseEntity.ok(usuarios);
    }

    @Operation(summary = "Retorna as informações completas de um usuário específico")
    @ApiResponse(responseCode = "200", description = "Informações completas do usuário solicitado")
    @GetMapping("/{idUsuario}")
    public ResponseEntity<UsuarioDTO> detalharUsuario(@PathVariable Long idUsuario) {
        var usuario = usuarioService.detalharUsuario(idUsuario);
        if(usuario == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(usuario);
    }

    @Operation(summary = "Retorna as informações simplificadas de um usuário específico")
    @ApiResponse(responseCode = "200", description = "Informações simplificadas do usuário solicitado")
    @GetMapping("/{idUsuario}/simplificado")
    public ResponseEntity<UsuarioDTO> detalharUsuarioSimplificado(@PathVariable Long idUsuario) {
        var usuario = usuarioService.detalharUsuarioSimplificado(idUsuario);
        if(usuario == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(usuario);
    }

}
