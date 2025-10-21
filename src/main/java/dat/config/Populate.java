package dat.config;


import dat.entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.math.BigDecimal;
import java.util.Set;

public class Populate {
    public static void main(String[] args) {

            EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

            Set<Monster> monsters = getMonsters();
            Set<Town> towns = getTowns();
            Set<NPC> npcs = getNpcs();
            Set<Shop> shops = getShops();
            Set<Action> actions = getActions();
            //Set<User> users = getUsers();

            try (EntityManager em = emf.createEntityManager()) {
                em.getTransaction().begin();

                // persist alle entities
                //users.forEach(em::persist);
                monsters.forEach(em::persist);
                towns.forEach(em::persist);
                npcs.forEach(em::persist);
                shops.forEach(em::persist);
                actions.forEach(em::persist);

                em.getTransaction().commit();
            }
        }

      /*  private static Set<User> getUsers() {
            User u1 = new User("Alice", "alice@example.com", "1234");
            User u2 = new User("Bob", "bob@example.com", "abcd");
            return Set.of(u1, u2);
        }
*/
        private static Set<Monster> getMonsters() {
            //Name, AC,HP,Description,Size
            Monster m1 = new Monster("Aboleth", 17, 135, "Ancient water creature", "Large");
            Monster m2 = new Monster("Goblin", 15, 7, "Sneaky and weak", "Small");
            return Set.of(m1, m2);
        }

        private static Set<Town> getTowns() {
            //Name, description, feature
            Town t1 = new Town("Riverville", "Small riverside town", "Mill, Dock, Market");
            Town t2 = new Town("Dragon's Rest", "Mountain town", "Inn, Blacksmith, Tavern");
            return Set.of(t1, t2);
        }

        private static Set<NPC> getNpcs() {
            //Name, description, RaceType, profession
            NPC n1 = new NPC("Thorin", "Stout dwarf warrior", NPC.RaceType.DWARF, "Warrior");
            NPC n2 = new NPC("Elara", "Mystical elf mage", NPC.RaceType.ELF, "Mage");
            return Set.of(n1, n2);
        }

        private static Set<Shop> getShops() {
            //Name, type, owner
            Shop s1 = new Shop("Berrys Shop", Shop.ShopType.BAKER,"Berry");
            Shop s2 = new Shop("Blacksmith's Forge", Shop.ShopType.BLACKSMITH, "Weapons");
            return Set.of(s1, s2);
        }

        private static Set<Action> getActions() {
            Action a1 = new Action("Fireball", "Explosive fire magic", "1d10+50");
            Action a2 = new Action("Slash", "Quick sword attack", "1d4+5");
            return Set.of(a1, a2);
        }
    }

