package dat.daos.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import dat.daos.IDAO;
import dat.dtos.ShopDTO;
import dat.entities.Shop;
import dat.config.HibernateConfig;
import dat.config.Populate;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class ShopDAO implements IDAO<ShopDTO, Integer> {

    private static ShopDAO instance;
    private static EntityManagerFactory emf;

    public static ShopDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new ShopDAO();
        }
        return instance;
    }

    // --- JSON Population ---
    public void populate() {
        List<Shop> shops = Populate.loadJsonFile("/shops.json", new TypeReference<List<Shop>>() {});
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            shops.forEach(em::persist);
            em.getTransaction().commit();
        }
    }


    @Override
    public ShopDTO read(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            Shop shop = em.find(Shop.class, id);
            return shop != null ? new ShopDTO(shop) : null;
        }
    }

    @Override
    public List<ShopDTO> readAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<ShopDTO> query = em.createQuery("SELECT new dat.dtos.ShopDTO(s) FROM Shop s", ShopDTO.class);
            return query.getResultList();
        }
    }

    @Override
    public ShopDTO create(ShopDTO dto) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Shop shop = new Shop(dto.getName(), dto.getShoptype(), dto.getOwner());
            em.persist(shop);
            em.getTransaction().commit();
            return new ShopDTO(shop);
        }
    }

    @Override
    public ShopDTO update(Integer id, ShopDTO dto) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Shop shop = em.find(Shop.class, id);
            if (shop != null) {
                shop.setName(dto.getName());
                shop.setShoptype(dto.getShoptype());
                shop.setOwner(dto.getOwner());
                em.merge(shop);
            }
            em.getTransaction().commit();
            return new ShopDTO(shop);
        }
    }

    @Override
    public void delete(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Shop shop = em.find(Shop.class, id);
            if (shop != null) em.remove(shop);
            em.getTransaction().commit();
        }
    }

    @Override
    public boolean validatePrimaryKey(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Shop.class, id) != null;
        }
    }
}
