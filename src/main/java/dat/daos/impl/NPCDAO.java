package dat.daos.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import dat.daos.IDAO;
import dat.dtos.NPCDTO;
import dat.entities.NPC;
import dat.config.Populate;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class NPCDAO implements IDAO<NPCDTO, Integer> {

    private static NPCDAO instance;
    private static EntityManagerFactory emf;

    public static NPCDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new NPCDAO();
        }
        return instance;
    }

    // --- JSON Population ---
    public void populate() {
        List<NPC> npcs = Populate.loadJsonFile("/NPCs.json", new TypeReference<List<NPC>>() {});
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            npcs.forEach(em::persist);
            em.getTransaction().commit();
        }
    }

    // --- READ by ID ---
    @Override
    public NPCDTO read(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            NPC npc = em.find(NPC.class, id);
            return npc != null ? new NPCDTO(npc.getName(), npc.getDescription(), npc.getRace(), npc.getProfessions()) : null;
        }
    }

    // --- READ ALL ---
    @Override
    public List<NPCDTO> readAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<NPCDTO> query = em.createQuery(
                    "SELECT new dat.dtos.NPCDTO(n.name, n.description, n.race, n.professions) FROM NPC n",
                    NPCDTO.class
            );
            return query.getResultList();
        }
    }

    // --- CREATE ---
    @Override
    public NPCDTO create(NPCDTO dto) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            NPC npc = new NPC(dto.getName(), dto.getDescription(), dto.getRace(), dto.getProfessions());
            em.persist(npc);
            em.getTransaction().commit();
            return new NPCDTO(npc.getName(), npc.getDescription(), npc.getRace(), npc.getProfessions());
        }
    }

    // --- UPDATE ---
    @Override
    public NPCDTO update(Integer id, NPCDTO dto) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            NPC npc = em.find(NPC.class, id);
            if (npc != null) {
                npc.setName(dto.getName());
                npc.setDescription(dto.getDescription());
                npc.setRace(dto.getRace());
                npc.setProfessions(dto.getProfessions());
                em.merge(npc);
            }
            em.getTransaction().commit();
            return npc != null ? new NPCDTO(npc.getName(), npc.getDescription(), npc.getRace(), npc.getProfessions()) : null;
        }
    }

    // --- DELETE ---
    @Override
    public void delete(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            NPC npc = em.find(NPC.class, id);
            if (npc != null) em.remove(npc);
            em.getTransaction().commit();
        }
    }

    // --- VALIDATE PRIMARY KEY ---
    @Override
    public boolean validatePrimaryKey(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(NPC.class, id) != null;
        }
    }
}
