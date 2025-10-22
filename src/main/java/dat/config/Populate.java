package dat.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dat.daos.impl.NPCDAO;
import dat.entities.Shop;
import dat.entities.Town;
import dat.entities.NPC;
import dat.daos.IDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

public class Populate {
    EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

    /**
     * Public method for DAOs/controllers to populate the database

     */
    public static void populateTownsAndShops(EntityManagerFactory emf) {
        NPCDAO npcDAO = new NPCDAO(emf);
        // Load JSON
        List<Shop> shops = loadJsonFile("/shops.json", new TypeReference<List<Shop>>() {});
        List<Town> towns = loadJsonFile("/towns.json", new TypeReference<List<Town>>() {});
        List<NPC> npcs = loadJsonFile("/NPCs.json", new TypeReference<List<NPC>>() {});

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            // Clear existing data (optional)
            em.createQuery("DELETE FROM Town").executeUpdate();
            em.createQuery("DELETE FROM Shop").executeUpdate();

            // Persist shops first
            shops.forEach(em::persist);

            // persist all npcs first
            npcDAO.saveAll(npcs);

            // 2️⃣ Assign 3 random shops to each town
            towns.forEach(town -> town.assignRandomShops(shops));

            // Persist towns
            towns.forEach(em::persist);

            em.getTransaction().commit();
        }

        // Print towns to verify
        towns.forEach(System.out::println);
        System.out.println("--- Database populated successfully! ---");



    }

    /**
     * Generic method to load any JSON list into a list of objects
     */
    public static <T> List<T> loadJsonFile(String path, TypeReference<List<T>> typeRef) {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream input = Populate.class.getResourceAsStream(path)) {
            if (input == null) {
                throw new IOException("Could not find " + path + " in resources folder.");
            }
            return mapper.readValue(input, typeRef);
        } catch (IOException e) {
            throw new RuntimeException("Error reading " + path, e);
        }
    }

    // Optional main for testing
    public static void main(String[] args) {
        populateTownsAndShops(HibernateConfig.getEntityManagerFactory());
    }
}
