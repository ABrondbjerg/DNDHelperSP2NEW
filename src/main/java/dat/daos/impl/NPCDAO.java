package dat.daos.impl;

import dat.daos.IDAO;
import dat.dtos.NPCDTO;
import dat.entities.NPC;
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

    @Override
    public NPCDTO read(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            NPC npc = em.find(NPC.class, id);
            return npc != null ? new NPCDTO(npc) : null;
        }
    }

    @Override
    public List<NPCDTO> readAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<NPCDTO> query = em.createQuery(
                    "SELECT new dat.dtos.NPCDTO(n) FROM NPC n", NPCDTO.class
            );
            return query.getResultList();
        }
    }

    @Override
    public NPCDTO create(NPCDTO dto) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            NPC npc = new NPC(dto.getName(), dto.getDescription(), dto.getRace(), dto.getProfessions());
            em.persist(npc);
            em.getTransaction().commit();
            return new NPCDTO(npc);
        }
    }

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
            return new NPCDTO(npc);
        }
    }

    @Override
    public void delete(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            NPC npc = em.find(NPC.class, id);
            if (npc != null) {
                em.remove(npc);
            }
            em.getTransaction().commit();
        }
    }

    @Override
    public boolean validatePrimaryKey(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(NPC.class, id) != null;
        }
    }

    public void saveAll(List<NPCDTO> dtos) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            for (NPCDTO dto : dtos) {
                NPC npc = new NPC(dto.getName(), dto.getDescription(), dto.getRace(), dto.getProfessions());
                em.persist(npc);
            }
            em.getTransaction().commit();
        }
    }
}
