package dat.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class HibernateConfigTest {

    private static EntityManagerFactory emf;

    @BeforeAll
    static void setup() {
        // Tell HibernateConfig to use test mode
        HibernateConfig.setTest(true);
        emf = HibernateConfig.getEntityManagerFactoryForTest();
    }

    @Test
    void canConnectAndPersistEntity() {
        assertNotNull(emf, "EntityManagerFactory should not be null");

        EntityManager em = emf.createEntityManager();
        assertNotNull(em, "EntityManager should be created");

        em.getTransaction().begin();
        // You can test with one of your entities, e.g. Town
        dat.entities.Town town = new dat.entities.Town();
        town.setName("TestTown");
        em.persist(town);
        em.getTransaction().commit();

        em.close();
        emf.close();
    }
}

