package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.api.arquivo;

import br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.api.usecases.ListarArquivos;
import br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.api.usecases.ListarArquivosDoUsuario;
import br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.exception.ApiExceptionHandler;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ArquivoController.class)
@Import(ApiExceptionHandler.class)
class ArquivoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    Environment environment;

    @MockitoBean
    ListarArquivos listarArquivos;

    @MockitoBean
    ListarArquivosDoUsuario listarArquivosDoUsuario;

    String pathVersion;

    @BeforeEach
    public void setUp() {
        pathVersion = environment.getProperty("api.version") + "/arquivos";
    }

    @Test
    public void notFoundQuandoServicoLancarEntityNotFoundExceptionAoListarArquivos() throws Exception {
        when(listarArquivos.execute(any(Pageable.class)))
                .thenThrow(new EntityNotFoundException("Não há arquivos na base de dados"));
        mockMvc.perform(get(pathVersion)
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.name()))
                .andExpect(jsonPath("$.message").value("Não há arquivos na base de dados"))
                .andExpect(jsonPath("$.path").value(pathVersion));
    }

    @Test
    public void sucessoQuandoExistemArquivosNaBase() throws Exception {
        List<ArquivoResponse> listaArquivos = new ArrayList<>();
        listaArquivos.add(new ArquivoResponse("arquivo1.txt"));
        listaArquivos.add(new ArquivoResponse("arquivo2.txt"));
        listaArquivos.add(new ArquivoResponse("arquivo3.txt"));
        when(listarArquivos.execute(any(Pageable.class))).thenReturn(listaArquivos);
        mockMvc.perform(get(pathVersion)
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nomeArquivo").value("arquivo1.txt"))
                .andExpect(jsonPath("$[1].nomeArquivo").value("arquivo2.txt"))
                .andExpect(jsonPath("$[2].nomeArquivo").value("arquivo3.txt"));
    }

    @Test
    public void erroInternoQuandoServicoLancarExcecaoNaoTratada() throws Exception {
        when(listarArquivos.execute(any(Pageable.class))).thenThrow(new RuntimeException());
        mockMvc.perform(get(pathVersion)
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(HttpStatus.INTERNAL_SERVER_ERROR.name()))
                .andExpect(jsonPath("$.message").value("Erro interno no servidor"));
    }

    @Test
    public void sucessoComPaginacaoCorreta() throws Exception {
        List<ArquivoResponse> listaArquivos = new ArrayList<>();
        listaArquivos.add(new ArquivoResponse("arquivo1.txt"));
        listaArquivos.add(new ArquivoResponse("arquivo2.txt"));
        when(listarArquivos.execute(any(Pageable.class))).thenReturn(listaArquivos);
        mockMvc.perform(get(pathVersion)
                        .param("page", "0")
                        .param("size", "2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nomeArquivo").value("arquivo1.txt"))
                .andExpect(jsonPath("$[1].nomeArquivo").value("arquivo2.txt"));
    }

}
