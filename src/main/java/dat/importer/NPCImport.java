package dat.importer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dat.config.HibernateConfig;
import dat.daos.impl.NPCDAO;
import dat.entities.NPC;

import jakarta.persistence.EntityManagerFactory;
import java.io.File;
import java.util.List;

public class NPCImport {

    public static void main(String[] args) {
        try {

            ObjectMapper mapper = new ObjectMapper();
            List<NPC> npcList = mapper.readValue(
                    new File("src/main/resources/NPCs.json"),
                    new TypeReference<List<NPC>>() {}
            );


            EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();


            NPCDAO dao = new NPCDAO(emf);
            dao.saveAll(npcList);

            for (NPC npc : dao.getAll()) {
                System.out.println("ID: " + npc.getId()
                        + " | Name: " + npc.getName()
                        + " | Race: " + npc.getRace()
                        + " | Professions: " + npc.getProfessions()
                        + " | Description: " + npc.getDescription());
            }


            emf.close();

            System.out.println( npcList.size() + " NPC'er er gemt i databasen");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(" Der opstod en fejl under importen");
        }
    }
}
