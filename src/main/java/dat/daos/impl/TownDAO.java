package dat.daos.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import dat.daos.IDAO;
import dat.dtos.TownDTO;
import dat.entities.Town;
import dat.Populate;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class TownDAO implements IDAO<TownDTO, Long> {

    private static TownDAO instance;
    private static EntityManagerFactory emf;

    public static TownDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new TownDAO();
        }
        return instance;
    }

    // --- JSON Population ---
    public void populate() {
        List<Town> towns = Populate.loadJsonFile("/towns.json", new TypeReference<List<Town>>() {});
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            towns.forEach(em::persist);
            em.getTransaction().commit();
        }
    }

    @Override
    public TownDTO read(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            Town town = em.find(Town.class, id);
            return town != null ? new TownDTO(town) : null;
        }
    }

    @Override
    public List<TownDTO> readAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<TownDTO> query = em.createQuery("SELECT new dat.dtos.TownDTO(t) FROM Town t", TownDTO.class);
            return query.getResultList();
        }
    }

    @Override
    public TownDTO create(TownDTO dto) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Town town = new Town(dto.getName(), dto.getDescription(), dto.getFeatures());
            em.persist(town);
            em.getTransaction().commit();
            return new TownDTO(town);
        }
    }

    @Override
    public TownDTO update(Long id, TownDTO dto) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Town town = em.find(Town.class, id);
            if (town != null) {
                town.setName(dto.getName());
                town.setDescription(dto.getDescription());
                town.setFeatures(dto.getFeatures());
                em.merge(town);
            }
            em.getTransaction().commit();
            return new TownDTO(town);
        }
    }

    @Override
    public void delete(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Town town = em.find(Town.class, id);
            if (town != null) em.remove(town);
            em.getTransaction().commit();
        }
    }

    @Override
    public boolean validatePrimaryKey(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Town.class, id) != null;
        }
    }
}
