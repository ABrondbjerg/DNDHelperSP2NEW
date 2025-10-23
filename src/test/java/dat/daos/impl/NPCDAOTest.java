package dat.daos.impl;

import dat.daos.BaseDAOTest;
import dat.dtos.NPCDTO;
import dat.entities.NPC;
import org.junit.jupiter.api.*;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NPCDAOTest extends BaseDAOTest {

    private NPCDAO npcDAO;

    @BeforeEach
    public void initDAO() {
        npcDAO = NPCDAO.getInstance(emf);
    }

    @Test
    @Order(1)
    public void testCreateAndReadNPC() {
        NPCDTO dto = new NPCDTO("John", "Friendly villager", NPC.RaceType.HUMAN, "Farmer");

        // Create
        NPCDTO created = npcDAO.create(dto);
        assertThat(created, is(notNullValue()));
        assertThat(created.getName(), is("John"));
        assertThat(created.getRace(), is(NPC.RaceType.HUMAN));

        // Read
        NPCDTO found = npcDAO.read(created.getId());
        assertThat(found, is(notNullValue()));
        assertThat(found.getName(), is("John"));
        assertThat(found.getProfessions(), is("Farmer"));
    }

    @Test
    @Order(2)
    public void testReadAllNPCs() {
        NPCDTO dto1 = new NPCDTO("Alice", "Merchant", NPC.RaceType.ELF, "Trader");
        NPCDTO dto2 = new NPCDTO("Bob", "Guard", NPC.RaceType.DWARF, "Soldier");

        npcDAO.create(dto1);
        npcDAO.create(dto2);

        List<NPCDTO> allNPCs = npcDAO.readAll();
        assertThat(allNPCs, hasSize(greaterThanOrEqualTo(2)));
        assertThat(allNPCs, hasItem(hasProperty("name", is("Alice"))));
        assertThat(allNPCs, hasItem(hasProperty("name", is("Bob"))));
    }

    @Test
    @Order(3)
    public void testUpdateNPC() {
        NPCDTO dto = new NPCDTO("Charlie", "Traveler", NPC.RaceType.HALFLING, "Scout");
        NPCDTO created = npcDAO.create(dto);

        created.setDescription("Updated Traveler");
        created.setProfessions("Explorer");
        created.setRace(NPC.RaceType.HALFELF);

        NPCDTO updated = npcDAO.update(created.getId(), created);
        assertThat(updated.getDescription(), is("Updated Traveler"));
        assertThat(updated.getProfessions(), is("Explorer"));
        assertThat(updated.getRace(), is(NPC.RaceType.HALFELF));
    }

    @Test
    @Order(4)
    public void testDeleteNPC() {
        NPCDTO dto = new NPCDTO("David", "Guard", NPC.RaceType.HUMAN, "Soldier");
        NPCDTO created = npcDAO.create(dto);

        npcDAO.delete(created.getId());
        NPCDTO deleted = npcDAO.read(created.getId());
        assertThat(deleted, is(nullValue()));
    }

    @Test
    @Order(5)
    public void testValidatePrimaryKey() {
        NPCDTO dto = new NPCDTO("Eve", "Wizard", NPC.RaceType.ELF, "Mage");
        NPCDTO created = npcDAO.create(dto);

        assertThat(npcDAO.validatePrimaryKey(created.getId()), is(true));
        assertThat(npcDAO.validatePrimaryKey(-1), is(false));
    }

    @Test
    @Order(6)
    public void testSaveAllNPCs() {
        NPCDTO npc1 = new NPCDTO("Fay", "Baker", NPC.RaceType.HALFLING, "Cook");
        NPCDTO npc2 = new NPCDTO("Gus", "Fisherman", NPC.RaceType.HUMAN, "Fisher");

        npcDAO.saveAll(List.of(npc1, npc2));

        List<NPCDTO> allNPCs = npcDAO.readAll();
        assertThat(allNPCs, hasItem(hasProperty("name", is("Fay"))));
        assertThat(allNPCs, hasItem(hasProperty("name", is("Gus"))));
    }
}
