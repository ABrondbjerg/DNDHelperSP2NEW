package dat.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PopulateIntegrationTest {

    @Test
    void testPopulateDatabaseWithJsonData() {
        // 1️⃣ Create an EntityManagerFactory using your Testcontainers setup
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();

        // 2️⃣ Populate the database using your existing logic
        Populate.populateDatabase(emf);

        // 3️⃣ Verify the data was inserted correctly
        EntityManager em = emf.createEntityManager();

        Long townCount = em.createQuery("SELECT COUNT(t) FROM Town t", Long.class).getSingleResult();
        Long shopCount = em.createQuery("SELECT COUNT(s) FROM Shop s", Long.class).getSingleResult();
        Long npcCount = em.createQuery("SELECT COUNT(n) FROM NPC n", Long.class).getSingleResult();
        Long monsterCount = em.createQuery("SELECT COUNT(m) FROM Monster m", Long.class).getSingleResult();
        Long actionCount = em.createQuery("SELECT COUNT(a) FROM Action a", Long.class).getSingleResult();

        System.out.printf("Towns: %d | Shops: %d | NPCs: %d | Monsters: %d | Actions: %d%n",
                townCount, shopCount, npcCount, monsterCount, actionCount);

        // 4️⃣ Assert that data exists in all tables
        assertTrue(townCount > 0, "No towns found in DB!");
        assertTrue(shopCount > 0, "No shops found in DB!");
        assertTrue(npcCount > 0, "No NPCs found in DB!");
        assertTrue(monsterCount > 0, "No monsters found in DB!");
        assertTrue(actionCount > 0, "No actions found in DB!");

        em.close();
    }
}

