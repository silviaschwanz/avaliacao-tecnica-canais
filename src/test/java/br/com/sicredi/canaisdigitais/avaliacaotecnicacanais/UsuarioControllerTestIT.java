package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class UsuarioControllerTestIT {

    @LocalServerPort
    int port;

    @Autowired
    Flyway flyway;

    @Autowired
    Environment environment;

    String pathVersion;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        pathVersion = environment.getProperty("api.version") + "/usuarios";
        flyway.clean();
        flyway.migrate();
    }

    @Test
    @DisplayName("Dado um id de usuário existente ao detalhar usuário deve retornar todas as informações com " +
            "usuário exceto seu id")
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
                .body("status", is("ATIVO"))
                .body("endereco.logradouro", is("Rua dos Andradas"))
                .body("endereco.numero", is(564))
                .body("endereco.cidade", is("Gravataí"))
                .body("endereco.bairro", is("Centro"))
                .body("endereco.estado", is("RS"));
    }

    @Test
    @DisplayName("Dado um id de usuário inexistente ao detalhar usuário deve retornar status 404 - Not Found")
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
    @DisplayName("Dado um id de usuário existente ao detalhar usuário de forma simplificada deve retornar somente " +
            "nome e email desse usuário ")
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
    @DisplayName("Dado um id de usuário inexistente ao detalhar usuário de forma simplificada deve retornar " +
            "status 404 - Not Found")
    void detalharUsuarioSimplificadoInexistente() {
        given()
                .contentType(ContentType.JSON)
                .pathParam("idUsuario", 5)
                .when()
                .get(pathVersion + "/{idUsuario}/simplificado")
                .then()
                .statusCode(404);
    }

}