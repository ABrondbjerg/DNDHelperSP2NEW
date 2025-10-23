package dat.daos;

import dat.config.HibernateConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import jakarta.persistence.EntityManagerFactory;
import org.testcontainers.containers.PostgreSQLContainer;

public abstract class BaseDAOTest {

    protected static PostgreSQLContainer<?> postgresContainer;
    protected static EntityManagerFactory emf;

    @BeforeAll
    public static void setup() {
        postgresContainer = new PostgreSQLContainer<>("postgres:15.3-alpine3.18")
                .withDatabaseName("test_db")
                .withUsername("postgres")
                .withPassword("postgres");
        postgresContainer.start();

        // Configure Hibernate to use this container
        HibernateConfig.setTest(true); // make sure your HibernateConfig picks up test mode
        emf = HibernateConfig.getEntityManagerFactoryForTest();
    }


    @AfterAll
    public static void teardown() {
        if (emf != null) emf.close();
        if (postgresContainer != null) postgresContainer.stop();
    }

}

