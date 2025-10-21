package dat.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dat.entities.Shop;
import dat.entities.Town;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Collections;

public class Populate {

    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

        // Load shops and towns from JSON
        List<Shop> shops = loadJsonFile("/shops.json", new TypeReference<List<Shop>>() {});
        List<Town> towns = loadJsonFile("/towns.json", new TypeReference<List<Town>>() {});

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            // 1️⃣ Persist all shops first
            shops.forEach(em::persist);

            // 2️⃣ Assign 3 random shops to each town
            towns.forEach(town -> town.assignRandomShops(shops));

            // 3️⃣ Persist towns
            towns.forEach(em::persist);

            em.getTransaction().commit();
        }

        // Print towns to verify
        towns.forEach(System.out::println);
        System.out.println("--- Database populated successfully! ---");
    }

    private static <T> List<T> loadJsonFile(String path, TypeReference<List<T>> typeRef) {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream input = Populate.class.getResourceAsStream(path)) {
            if (input == null) throw new IOException("Could not find " + path + " in resources folder.");
            return mapper.readValue(input, typeRef);
        } catch (IOException e) {
            throw new RuntimeException("Error reading " + path, e);
        }
    }
}
