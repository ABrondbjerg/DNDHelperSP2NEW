package dat.daos.impl;

import dat.daos.IDAO;
import dat.dtos.MonsterDTO;
import dat.entities.Monster;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class MonsterDAO implements IDAO<MonsterDTO, Integer> {

    private static MonsterDAO instance;
    private static EntityManagerFactory emf;

    public static MonsterDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new MonsterDAO();
        }
        return instance;
    }

    @Override
    public MonsterDTO read(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            Monster entity = em.find(Monster.class, id);
            return entity != null ? new MonsterDTO(entity) : null;
        }
    }

    @Override
    public List<MonsterDTO> readAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<MonsterDTO> query = em.createQuery(
                    "SELECT new dat.dtos.MonsterDTO(m) FROM Monster m", MonsterDTO.class
            );
            return query.getResultList();
        }
    }

    @Override
    public MonsterDTO create(MonsterDTO dto) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Monster entity = dto.toEntity();
            em.persist(entity);
            em.getTransaction().commit();
            return new MonsterDTO(entity);
        }
    }

    @Override
    public MonsterDTO update(Integer id, MonsterDTO monsterDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            Monster monster = em.find(Monster.class, id);
            if (monster == null) {
                em.getTransaction().rollback();
                return null;
            }

            // Opdater felterne — brug evt. null-checks, hvis ikke alle felter skal opdateres
            monster.setName(monsterDTO.getName());
            monster.setType(monsterDTO.getType());
            monster.setAlignment(monsterDTO.getAlignment());
            monster.setHitPoints(monsterDTO.getHitPoints());
            monster.setArmorClass(monsterDTO.getArmorClass());
            monster.setStrength(monsterDTO.getStrength());
            monster.setDexterity(monsterDTO.getDexterity());
            monster.setConstitution(monsterDTO.getConstitution());
            monster.setIntelligence(monsterDTO.getIntelligence());
            monster.setWisdom(monsterDTO.getWisdom());
            monster.setCharisma(monsterDTO.getCharisma());
            monster.setChallengeRating(monsterDTO.getChallengeRating());
            monster.setSize(monsterDTO.getSize());
            monster.setSpeed(monsterDTO.getSpeed());
            monster.setImage(monsterDTO.getImage());
            monster.setUrl(monsterDTO.getUrl());

            Monster merged = em.merge(monster);
            em.getTransaction().commit();

            return merged != null ? new MonsterDTO(merged) : null;
        }
    }

    @Override
    public void delete(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Monster entity = em.find(Monster.class, id);
            if (entity != null) em.remove(entity);
            em.getTransaction().commit();
        }
    }

    @Override
    public boolean validatePrimaryKey(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Monster.class, id) != null;
        }
    }
}
