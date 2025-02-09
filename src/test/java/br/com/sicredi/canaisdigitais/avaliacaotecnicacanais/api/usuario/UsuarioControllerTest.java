package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.api.usuario;

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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UsuarioController.class)
@Import(ApiExceptionHandler.class)
class UsuarioControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    Environment environment;

    @MockitoBean
    UsuarioService usuarioService;

    String pathVersion;

    @BeforeEach
    public void setUp() {
        pathVersion = environment.getProperty("api.version") + "/usuarios";
    }

    @Test
    public void notFoundQuandoServicoLancarEntityNotFoundExceptionAoListarUsuarios() throws Exception {
        when(usuarioService.listarUsuarios(any(Pageable.class)))
                .thenThrow(new EntityNotFoundException("Não há usuários na base de dados"));

        mockMvc.perform(get(pathVersion)
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.name()))
                .andExpect(jsonPath("$.message").value("Não há usuários na base de dados"))
                .andExpect(jsonPath("$.path").value(pathVersion));
    }

    @Test
    public void internalServerErrorQuandoServicoLancarRuntimeExceptionAoListarUsuarios() throws Exception {
        when(usuarioService.listarUsuarios(any(Pageable.class)))
                .thenThrow(new RuntimeException("Erro na base de dados legada"));

        mockMvc.perform(get(pathVersion)
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(HttpStatus.INTERNAL_SERVER_ERROR.name()))
                .andExpect(jsonPath("$.message").value("Erro interno no servidor"))
                .andExpect(jsonPath("$.path").value(pathVersion));
    }

    @Test
    public void internalServerErrorQuandoServicoLancarRuntimeExceptionAoBuscarUsuario() throws Exception {
        when(usuarioService.detalharUsuario(any(Long.class)))
                .thenThrow(new RuntimeException("Erro na base de dados legada"));

        mockMvc.perform(get(pathVersion + "/123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(HttpStatus.INTERNAL_SERVER_ERROR.name()))
                .andExpect(jsonPath("$.message").value("Erro interno no servidor"))
                .andExpect(jsonPath("$.path").value(pathVersion + "/123"));
    }

}