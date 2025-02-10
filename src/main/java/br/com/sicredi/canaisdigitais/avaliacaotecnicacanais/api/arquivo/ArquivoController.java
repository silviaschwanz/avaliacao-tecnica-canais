package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.api.arquivo;

import br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.api.usecases.ListarArquivos;
import br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.api.usecases.ListarArquivosDoUsuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "${api.version}/arquivos")
public class ArquivoController {

    @Autowired
    ListarArquivos listarArquivos;

    @Autowired
    ListarArquivosDoUsuario listarArquivosDoUsuario;

    @Operation(summary = "Entrega uma lista com todos os arquivos existentes no sistema")
    @ApiResponse(responseCode = "200", description = "Lista dos arquivos existentes no sistema")
    @GetMapping
    public ResponseEntity<List<ArquivoResponse>> listarArquivos(
            @PageableDefault(size = 10, sort = {"nomeArquivo"}) Pageable paginacao
    ) {
        return ResponseEntity.ok(listarArquivos.execute(paginacao));
    }

    @Operation(summary = "Entrega uma lista com todos os arquivos de um usuário existentes no sistema")
    @ApiResponse(responseCode = "200", description = "Lista dos arquivos do usuário")
    @GetMapping("/{idUsuario}")
    public ResponseEntity<List<ArquivoComConteudoResponse>> listarArquivosDoUsuario(
            @PathVariable Long idUsuario,
            @PageableDefault(size = 10, sort = {"nomeArquivo"}) Pageable paginacao
    ) {
        return ResponseEntity.ok().body(listarArquivosDoUsuario.execute(idUsuario, paginacao));
    }

}
