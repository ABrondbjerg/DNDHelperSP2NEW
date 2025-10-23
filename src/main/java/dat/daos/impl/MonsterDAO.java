package dat.daos.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import dat.daos.IDAO;
import dat.dtos.MonsterDTO;
import dat.entities.Monster;
import dat.config.Populate;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class MonsterDAO implements IDAO<MonsterDTO, Long> {

    private static MonsterDAO instance;
    private static EntityManagerFactory emf;

    public static MonsterDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new MonsterDAO();
        }
        return instance;
    }

    // --- JSON Population ---
    public void populate() {
        List<Monster> monsters = Populate.loadJsonFile("/monsters.json", new TypeReference<List<Monster>>() {});
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            monsters.forEach(em::persist);
            em.getTransaction().commit();
        }
    }

    @Override
    public MonsterDTO read(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            Monster monster = em.find(Monster.class, id);
            return monster != null ? new MonsterDTO(monster) : null;
        }
    }

    @Override
    public List<MonsterDTO> readAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<MonsterDTO> query = em.createQuery(
                    "SELECT new dat.dtos.MonsterDTO(m) FROM Monster m", MonsterDTO.class);
            return query.getResultList();
        }
    }

    @Override
    public MonsterDTO create(MonsterDTO dto) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Monster monster = new Monster(dto.getName(), dto.getArmorClass(), dto.getHitPoints(), dto.getSize());
            em.persist(monster);
            em.getTransaction().commit();
            return new MonsterDTO(monster);
        }
    }

    @Override
    public MonsterDTO update(Long id, MonsterDTO dto) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Monster monster = em.find(Monster.class, id);
            if (monster != null) {
                monster.setName(dto.getName());
                monster.setArmorClass(dto.getArmorClass());
                monster.setHitPoints(dto.getHitPoints());
                monster.setSize(dto.getSize());
                em.merge(monster);
            }
            em.getTransaction().commit();
            return new MonsterDTO(monster);
        }
    }

    @Override
    public void delete(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Monster monster = em.find(Monster.class, id);
            if (monster != null) em.remove(monster);
            em.getTransaction().commit();
        }
    }

    @Override
    public boolean validatePrimaryKey(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Monster.class, id) != null;
        }
    }
}
