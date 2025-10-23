package dat.daos.impl;

import dat.daos.BaseDAOTest;
import dat.dtos.ShopDTO;
import dat.dtos.TownDTO;
import org.junit.jupiter.api.*;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TownDAOTest extends BaseDAOTest {

    private TownDAO townDAO;

    @BeforeEach
    public void initDAO() {
        townDAO = TownDAO.getInstance(emf);
    }

    @Test
    @Order(1)
    public void testCreateAndReadTown() {
        ShopDTO shop = new ShopDTO("Blacksmith", dat.entities.Shop.ShopType.ARMORER, "Gorim");
        TownDTO dto = new TownDTO("Riverwood", "A quiet village by the river", "Forests and lumber mills", Collections.singletonList(shop));

        // Create
        TownDTO created = townDAO.create(dto);
        assertThat(created, is(notNullValue()));
        assertThat(created.getId(), is(notNullValue()));
        assertThat(created.getName(), is("Riverwood"));
        assertThat(created.getDescription(), containsString("quiet village"));

        // Read
        TownDTO found = townDAO.read(created.getId());
        assertThat(found, is(notNullValue()));
        assertThat(found.getName(), is("Riverwood"));
        assertThat(found.getFeatures(), is("Forests and lumber mills"));
    }

    @Test
    @Order(2)
    public void testReadAllTowns() {
        TownDTO dto1 = new TownDTO("Whiterun", "Trading city", "Open plains", null);
        TownDTO dto2 = new TownDTO("Solitude", "Capital of the region", "Castle and port", null);

        townDAO.create(dto1);
        townDAO.create(dto2);

        List<TownDTO> allTowns = townDAO.readAll();
        assertThat(allTowns, hasSize(greaterThanOrEqualTo(2)));
        assertThat(allTowns, hasItem(hasProperty("name", is("Whiterun"))));
        assertThat(allTowns, hasItem(hasProperty("name", is("Solitude"))));
    }

    @Test
    @Order(3)
    public void testUpdateTown() {
        TownDTO dto = new TownDTO("Oldtown", "Ancient ruins", "Collapsed walls", null);
        TownDTO created = townDAO.create(dto);

        created.setName("Newtown");
        created.setDescription("Rebuilt city with new walls");
        created.setFeatures("Harbor and market");

        TownDTO updated = townDAO.update(created.getId(), created);
        assertThat(updated.getName(), is("Newtown"));
        assertThat(updated.getDescription(), containsString("Rebuilt"));
        assertThat(updated.getFeatures(), is("Harbor and market"));
    }

    @Test
    @Order(4)
    public void testDeleteTown() {
        TownDTO dto = new TownDTO("TemporaryTown", "Short-lived", "Dusty roads", null);
        TownDTO created = townDAO.create(dto);

        townDAO.delete(created.getId());
        TownDTO deleted = townDAO.read(created.getId());
        assertThat(deleted, is(nullValue()));
    }

    @Test
    @Order(5)
    public void testValidatePrimaryKey() {
        TownDTO dto = new TownDTO("Winterhold", "Home of mages", "Snowy cliffs", null);
        TownDTO created = townDAO.create(dto);

        assertThat(townDAO.validatePrimaryKey(created.getId()), is(true));
        assertThat(townDAO.validatePrimaryKey(-1L), is(false));
    }

    @Test
    @Order(6)
    public void testPopulateFromJson() {
        // Populate towns from JSON file
        townDAO.populate();

        List<TownDTO> allTowns = townDAO.readAll();
        assertThat(allTowns, is(not(empty())));

        // Optional: verify one of the known JSON towns (depends on your /towns.json)
        assertThat(allTowns, hasItem(hasProperty("name", notNullValue())));
    }
}

