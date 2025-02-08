package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais;

import br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.infra.controller.arquivo.ArquivoController;
import br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.infra.gateways.ArquivoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class ArquivoControllerTest {

    @InjectMocks
    private ArquivoController target;

    @Mock
    private ArquivoService arquivoService;

    @Test
    void listaVaziaAolistarTodosArquivosComSucesso() {
        doReturn(List.of()).when(arquivoService).listarTodosArquivos();
        var arquivos = target.listarArquivos(null);

        assertNotNull(arquivos.getBody());
        assertTrue(arquivos.getBody().isEmpty());
    }
}
