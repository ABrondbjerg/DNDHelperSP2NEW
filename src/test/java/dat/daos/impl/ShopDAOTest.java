package dat.daos.impl;

import dat.daos.BaseDAOTest;
import dat.dtos.ShopDTO;
import dat.entities.Shop;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ShopDAOTest extends BaseDAOTest {

    private ShopDAO shopDAO;

    @BeforeEach
    public void initDAO() {
        shopDAO = ShopDAO.getInstance(emf);
    }

    @Test
    @Order(1)
    public void testCreateAndReadShop() {
        ShopDTO dto = new ShopDTO("Blacksmith", Shop.ShopType.ARMORER, "Gorim");

        // Create
        ShopDTO created = shopDAO.create(dto);
        assertThat(created.getId(), is(notNullValue()));

        // Read
        ShopDTO found = shopDAO.read(created.getId());
        assertThat(found, is(notNullValue()));
        assertThat(found.getName(), is("Blacksmith"));
        assertThat(found.getShoptype(), is(Shop.ShopType.ARMORER));
        assertThat(found.getOwner(), is("Gorim"));
    }

    @Test
    @Order(2)
    public void testReadAllShops() {
        ShopDTO dto1 = new ShopDTO("Bakery", Shop.ShopType.BAKER, "Alice");
        ShopDTO dto2 = new ShopDTO("Alchemist", Shop.ShopType.ALCHEMIST, "Merlin");

        shopDAO.create(dto1);
        shopDAO.create(dto2);

        List<ShopDTO> allShops = shopDAO.readAll();
        assertThat(allShops, hasSize(greaterThanOrEqualTo(2)));
        assertThat(allShops, hasItem(hasProperty("name", is("Bakery"))));
        assertThat(allShops, hasItem(hasProperty("name", is("Alchemist"))));
    }

    @Test
    @Order(3)
    public void testUpdateShop() {
        ShopDTO dto = new ShopDTO("Old Shop", Shop.ShopType.CURIO_SHOP, "Bob");
        ShopDTO created = shopDAO.create(dto);

        created.setName("Updated Shop");
        created.setShoptype(Shop.ShopType.ARMORER);
        created.setOwner("Robert");

        ShopDTO updated = shopDAO.update(created.getId(), created);
        assertThat(updated.getName(), is("Updated Shop"));
        assertThat(updated.getShoptype(), is(Shop.ShopType.ARMORER));
        assertThat(updated.getOwner(), is("Robert"));
    }

    @Test
    @Order(4)
    public void testDeleteShop() {
        ShopDTO dto = new ShopDTO("Temporary Shop", Shop.ShopType.BOOKSELLER, "TempOwner");
        ShopDTO created = shopDAO.create(dto);

        shopDAO.delete(created.getId());
        ShopDTO deleted = shopDAO.read(created.getId());
        assertThat(deleted, is(nullValue()));
    }

    @Test
    @Order(5)
    public void testValidatePrimaryKey() {
        ShopDTO dto = new ShopDTO("Magic Shop", Shop.ShopType.ENCHANTER, "Gandalf");
        ShopDTO created = shopDAO.create(dto);

        assertThat(shopDAO.validatePrimaryKey(created.getId()), is(true));
        assertThat(shopDAO.validatePrimaryKey(-1), is(false));
    }

    @Test
    @Order(6)
    public void testPopulateFromJson() {
        shopDAO.populate();

        List<ShopDTO> allShops = shopDAO.readAll();
        assertThat(allShops, is(not(empty())));
        // Example: check that a shop from your JSON exists
        assertThat(allShops, hasItem(hasProperty("name", is("Blacksmith"))));
    }
}


