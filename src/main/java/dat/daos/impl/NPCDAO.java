package dat.daos.impl;

import dat.entities.NPC;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

public class NPCDAO {

    private final EntityManagerFactory emf;

    public NPCDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void save(NPC npc) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(npc);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    public void saveAll(List<NPC> npcs) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            for (NPC npc : npcs) {
                em.persist(npc);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
    public List<NPC> getAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT n FROM NPC n", NPC.class).getResultList();
        } finally {
            em.close();
        }
    }

    public NPC getById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(NPC.class, id);
        } finally {
            em.close();
        }
    }

    public void delete(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            NPC npc = em.find(NPC.class, id);
            if (npc != null) {
                em.remove(npc);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
