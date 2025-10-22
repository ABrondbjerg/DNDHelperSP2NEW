package dat;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class PostgresJDBCTest {

    public static void main(String[] args) {
        // Start a Postgres container
        try (PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:16")) {
            postgresContainer.start();

            // Get JDBC info directly from the container
            String url = postgresContainer.getJdbcUrl();
            String user = postgresContainer.getUsername();
            String password = postgresContainer.getPassword();

            System.out.println("JDBC URL: " + url);
            System.out.println("User: " + user);
            System.out.println("Password: " + password);

            // Connect via JDBC
            try (Connection conn = DriverManager.getConnection(url, user, password);
                 Statement stmt = conn.createStatement()) {

                System.out.println("Connected to Postgres!");

                // Create table
                stmt.execute("CREATE TABLE IF NOT EXISTS person(id SERIAL PRIMARY KEY, name VARCHAR(50))");

                // Insert sample data
                stmt.execute("INSERT INTO person(name) VALUES ('Alice'), ('Bob')");

                // Query data
                ResultSet rs = stmt.executeQuery("SELECT * FROM person");
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id") + ", Name: " + rs.getString("name"));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            // Container stops automatically when try-with-resources block exits
        }
    }
}

