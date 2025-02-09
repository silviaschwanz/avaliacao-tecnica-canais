package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.api.usuario;

import br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.BaseDatabaseTestContainer;
import br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.exception.HttpStatusCustom;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UsuarioControllerTestIT extends BaseDatabaseTestContainer {

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
        pathVersion = environment.getProperty("api.version") + "/usuarios";
    }

    @Test
    void detalharUsuarioExistente() {
        given()
                .contentType(ContentType.JSON)
                .pathParam("idUsuario", 1)
                .when()
                .get(pathVersion + "/{idUsuario}")
                .then()
                .statusCode(200)
                .body("nome", is("João Paulo Fonseca"))
                .body("email", is("joaopfonseca@gmail.com"))
                .body("dataNascimento", is("1941-01-21"))
                .body("status", is(true))
                .body("endereco.logradouro", is("Rua dos Andradas"))
                .body("endereco.numero", is(564))
                .body("endereco.cidade", is("Gravataí"))
                .body("endereco.bairro", is("Centro"))
                .body("endereco.estado", is("RS"));
    }

    @Test
    void detalherUsuarioInexistente() {
        given()
                .contentType(ContentType.JSON)
                .pathParam("idUsuario", 5)
                .when()
                .get(pathVersion + "/{idUsuario}")
                .then()
                .statusCode(404);
    }

    @Test
    void notFoundAoUtilizarRotaInexistente() {
        given()
                .contentType(ContentType.JSON)
                .pathParam("idUsuario", 5)
                .when()
                .get(pathVersion + "/{idUsuario}/simples")
                .then()
                .statusCode(404)
                .body("status", is(HttpStatusCustom.NOT_FOUND.name()))
                .body("message", is("O recurso solicitado não foi encontrado"));
    }

    @Test
    void detalharUsuarioSimplificadoExistente() {
        given()
                .contentType(ContentType.JSON)
                .pathParam("idUsuario", 1)
                .when()
                .get(pathVersion + "/{idUsuario}/simplificado")
                .then()
                .statusCode(200)
                .body("nome", is("João Paulo Fonseca"))
                .body("email", is("joaopfonseca@gmail.com"))
                .body("dataNascimento", nullValue())
                .body("endereco.logradouro", nullValue())
                .body("endereco.numero", nullValue())
                .body("endereco.cidade", nullValue())
                .body("endereco.bairro", nullValue())
                .body("endereco.estado", nullValue());
    }

    @Test
    void notFoundAoDetalharUsuarioSimplificadoInexistente() {
        given()
                .contentType(ContentType.JSON)
                .pathParam("idUsuario", 5)
                .when()
                .get(pathVersion + "/{idUsuario}/simplificado")
                .then()
                .statusCode(404);
    }

    @Test
    void listarUsuarios() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get(pathVersion)
                .then()
                .statusCode(200);
    }

    @Test
    void notFoundAolistarUsuarios() {
        jdbcTemplate.execute("DELETE FROM USUARIO");
        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get(pathVersion)
                .then()
                .statusCode(404)
                .body("status", is(HttpStatusCustom.NOT_FOUND.name()))
                .body("message", is("Não há usuários na base de dados"))
                .extract().response();
        response.prettyPrint();
        resetDatabase(jdbcTemplate);
    }

    @Test
    void erroBaseInconsisteTabelaNaoEncontrada() {
        jdbcTemplate.execute("ALTER TABLE USUARIO RENAME TO USUARIO_TEMP");
        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get(pathVersion)
                .then()
                .statusCode(500)
                .body("status", is(HttpStatusCustom.INTERNAL_SERVER_ERROR.name()))
                .body("message", is("Erro interno no servidor"))
                .extract().response();
        response.prettyPrint();
        resetDatabase(jdbcTemplate);
    }

    @Test
    void erroBaseInconsistenteColunaRemovida() {
        jdbcTemplate.execute("ALTER TABLE USUARIO DROP COLUMN nome");
        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get(pathVersion)
                .then()
                .statusCode(500)
                .body("status", is(HttpStatusCustom.INTERNAL_SERVER_ERROR.name()))
                .body("message", is("Erro interno no servidor"))
                .extract().response();
        response.prettyPrint();
        resetDatabase(jdbcTemplate);
    }

}