package dat;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dat.entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

public class Populate {
    EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

    public static void populateDatabase(EntityManagerFactory emf){

        List<Action> actions = loadJsonFile("/actions.json", new TypeReference<List<Action>>() {});
        List<Monster> monsters = loadJsonFile("/monsters.json", new TypeReference<List<Monster>>() {});
        List<NPC> npcs = loadJsonFile("/NPCs.json", new TypeReference<List<NPC>>() {});
        List<Shop> shops = loadJsonFile("/shops.json", new TypeReference<List<Shop>>() {});
        List<Town> towns = loadJsonFile("/towns.json", new TypeReference<List<Town>>() {});


        try (EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();

            //persist all actions
            actions.forEach(em::persist);


            //Map persisted Actions by name for Monster to reference
            Map<String, Action> actionMap = actions.stream()
                    .collect(Collectors.toMap(Action::getName, a -> a));

            for (Monster monster : monsters) {
                if (monster.getActions() != null && !monster.getActions().isEmpty()) {
                    Set<Action> managedActions = monster.getActions().stream()
                            .map(a -> actionMap.get(a.getName()))
                            .filter(Objects::nonNull)
                            .collect(Collectors.toSet());
                    monster.getActions().clear();
                    monster.getActions().addAll(managedActions);
                }
                em.persist(monster);
            }


            npcs.forEach(em::persist);
            shops.forEach(em::persist);

            towns.forEach(town -> town.assignRandomShops(shops));

            towns.forEach(em::persist);

            em.getTransaction().commit();
        }

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

        populateDatabase(HibernateConfig.getEntityManagerFactory());
    }
}
