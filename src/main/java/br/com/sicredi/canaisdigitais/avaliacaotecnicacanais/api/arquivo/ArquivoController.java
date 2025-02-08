package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.api.arquivo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/${api.version}/arquivos")
@RequiredArgsConstructor
public class ArquivoController {

    private final ArquivoService arquivoService;

    @Operation(summary = "Entrega uma lista com todos os arquivos existentes no sistema")
    @ApiResponse(responseCode = "200", description = "Lista dos arquivos existentes no sistema")
    @GetMapping
    public ResponseEntity<List<ArquivoDTO>> listarArquivos(@RequestParam(value = "idUsuario", required = false) Long idUsuario) {
        if (idUsuario == null) {
            return ResponseEntity.ok(arquivoService.listarTodosArquivos());
        }

        return ResponseEntity.ok(arquivoService.listarArquivosUsuario(idUsuario));
    }

}
