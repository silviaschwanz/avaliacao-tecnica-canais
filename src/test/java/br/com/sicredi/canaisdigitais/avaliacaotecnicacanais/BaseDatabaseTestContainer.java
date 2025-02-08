package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public class BaseDatabaseTestContainer {

    private static final DockerImageName customMySQLImage = DockerImageName
            .parse("base-legada-test:1.0")
            .asCompatibleSubstituteFor("mysql");

    @Container
    private static MySQLContainer<?> databaseContainer = new MySQLContainer<>(customMySQLImage)
            .withReuse(true);
    static {
        databaseContainer.start();
    }

    @DynamicPropertySource
    static void registrarPropriedades(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", databaseContainer::getJdbcUrl);
        registry.add("spring.datasource.username", databaseContainer::getUsername);
        registry.add("spring.datasource.password", databaseContainer::getPassword);
    }

}
