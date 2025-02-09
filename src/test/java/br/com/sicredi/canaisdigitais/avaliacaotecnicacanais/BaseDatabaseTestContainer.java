package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;
import java.util.Objects;

@Testcontainers
public class BaseDatabaseTestContainer {

    private static final DockerImageName customMySQLImage = DockerImageName
            .parse("base-legada-test:1.0")
            .asCompatibleSubstituteFor("mysql");

    @Container
    protected static MySQLContainer<?> databaseContainer = new MySQLContainer<>(customMySQLImage)
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

    public static void resetDatabase(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 0");
        List<String> tables = jdbcTemplate.queryForList("SHOW TABLES", String.class);
        for (String table : tables) {
            jdbcTemplate.execute("DROP TABLE IF EXISTS " + table);
        }
        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 1");
        try {
            Resource resource = new ClassPathResource("init-base-legada-test.sql");
            ScriptUtils.executeSqlScript(Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection(), resource);
        } catch (Exception exception) {
            throw new RuntimeException("Erro ao tentar resetar o database", exception);
        }
    }

}
