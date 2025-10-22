package dat;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@Testcontainers
public class PostgresContainerTest {

    // A static container starts before any tests in this class and stops after all tests.
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    @Test
    void containerStartsAndCanRunSimpleJdbc() throws Exception {
        // get JDBC connection details from Testcontainers
        String jdbcUrl = postgres.getJdbcUrl();
        String username = postgres.getUsername();
        String password = postgres.getPassword();

        System.out.println("JDBC URL: " + jdbcUrl);
        System.out.println("User: " + username);

        // connect using standard JDBC -- this proves the DB is reachable
        try (Connection conn = java.sql.DriverManager.getConnection(jdbcUrl, username, password);
             Statement stmt = conn.createStatement()) {

            stmt.execute("CREATE TABLE IF NOT EXISTS hero (id SERIAL PRIMARY KEY, name TEXT)");
            stmt.execute("INSERT INTO hero (name) VALUES ('Gandalf')");
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM hero");

            rs.next();
            int count = rs.getInt(1);
            assert count >= 1 : "expected at least 1 row in hero table";
        }
    }
}

