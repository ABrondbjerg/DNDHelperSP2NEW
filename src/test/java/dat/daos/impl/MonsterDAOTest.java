package dat.daos.impl;

import dat.daos.BaseDAOTest;
import dat.dtos.MonsterDTO;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MonsterDAOTest extends BaseDAOTest {

    private MonsterDAO monsterDAO;

    @BeforeEach
    public void initDAO() {
        monsterDAO = MonsterDAO.getInstance(emf);
    }

    @Test
    @Order(1)
    public void testCreateAndReadMonster() {
        MonsterDTO dto = new MonsterDTO(null, "Goblin", 13, 7, "Small");

        // Create
        MonsterDTO created = monsterDAO.create(dto);
        assertThat(created, is(notNullValue()));
        assertThat(created.getId(), is(notNullValue()));
        assertThat(created.getName(), is("Goblin"));
        assertThat(created.getArmorClass(), is(13));
        assertThat(created.getSize(), is("Small"));

        // Read
        MonsterDTO found = monsterDAO.read(created.getId());
        assertThat(found, is(notNullValue()));
        assertThat(found.getName(), is("Goblin"));
        assertThat(found.getHitPoints(), is(7));
    }

    @Test
    @Order(2)
    public void testReadAllMonsters() {
        MonsterDTO m1 = new MonsterDTO(null, "Orc", 15, 30, "Medium");
        MonsterDTO m2 = new MonsterDTO(null, "Troll", 14, 84, "Large");

        monsterDAO.create(m1);
        monsterDAO.create(m2);

        List<MonsterDTO> allMonsters = monsterDAO.readAll();
        assertThat(allMonsters, hasSize(greaterThanOrEqualTo(2)));
        assertThat(allMonsters, hasItem(hasProperty("name", is("Orc"))));
        assertThat(allMonsters, hasItem(hasProperty("name", is("Troll"))));
    }

    @Test
    @Order(3)
    public void testUpdateMonster() {
        MonsterDTO dto = new MonsterDTO(null, "Dragon Whelp", 17, 50, "Medium");
        MonsterDTO created = monsterDAO.create(dto);

        created.setName("Young Dragon");
        created.setArmorClass(18);
        created.setHitPoints(85);
        created.setSize("Large");

        MonsterDTO updated = monsterDAO.update(created.getId(), created);
        assertThat(updated.getName(), is("Young Dragon"));
        assertThat(updated.getArmorClass(), is(18));
        assertThat(updated.getHitPoints(), is(85));
        assertThat(updated.getSize(), is("Large"));
    }

    @Test
    @Order(4)
    public void testDeleteMonster() {
        MonsterDTO dto = new MonsterDTO(null, "Skeleton", 13, 13, "Medium");
        MonsterDTO created = monsterDAO.create(dto);

        monsterDAO.delete(created.getId());
        MonsterDTO deleted = monsterDAO.read(created.getId());
        assertThat(deleted, is(nullValue()));
    }

    @Test
    @Order(5)
    public void testValidatePrimaryKey() {
        MonsterDTO dto = new MonsterDTO(null, "Ogre", 11, 59, "Large");
        MonsterDTO created = monsterDAO.create(dto);

        assertThat(monsterDAO.validatePrimaryKey(created.getId()), is(true));
        assertThat(monsterDAO.validatePrimaryKey(-1L), is(false));
    }

    @Test
    @Order(6)
    public void testPopulateFromJson() {
        monsterDAO.populate();
        List<MonsterDTO> allMonsters = monsterDAO.readAll();
        assertThat(allMonsters, is(not(empty())));
        assertThat(allMonsters, hasItem(hasProperty("name", notNullValue())));
    }
}
