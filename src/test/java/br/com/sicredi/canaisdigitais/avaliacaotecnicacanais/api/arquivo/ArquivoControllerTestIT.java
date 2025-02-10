package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.api.arquivo;

import br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.BaseDatabaseTestContainer;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ArquivoControllerTestIT extends BaseDatabaseTestContainer {

    @LocalServerPort
    int port;

    @Autowired
    Environment environment;

    String pathVersion;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        pathVersion = environment.getProperty("api.version") + "/arquivos";
    }

    @Test
    public void deveRetornarListaDeArquivos() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get(pathVersion)
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .body("[0].nomeArquivo", notNullValue());
    }

    @Test
    public void deveRetornarListaDeArquivosDoUsuario() {
        given()
                .contentType(ContentType.JSON)
                .pathParam("idUsuario", 1)
                .when()
                .get(pathVersion + "/{idUsuario}")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .body("[0].nomeArquivo", notNullValue())
                .body("[0].titulo", notNullValue())
                .body("[0].dataPublicacao", notNullValue())
                .body("[0].tags", notNullValue());
    }


    @Test
    public void deveRetornarNotFoundQuandoNaoHaArquivos() {
        jdbcTemplate.execute("DELETE FROM ARQUIVO");
        given()
                .contentType(ContentType.JSON)
                .when()
                .get(pathVersion)
                .then()
                .statusCode(404)
                .body("message", containsString("Não há arquivos na base de dados"));
        resetDatabase(jdbcTemplate);
    }

    @Test
    public void deveRetornarNotFoundQuandoNaoHaArquivosDoUsuario() {
        given()
                .contentType(ContentType.JSON)
                .pathParam("idUsuario", 999)
                .when()
                .get(pathVersion + "/{idUsuario}")
                .then()
                .statusCode(404)
                .body("message", containsString("Não há arquivos na base de dados"));
    }

}