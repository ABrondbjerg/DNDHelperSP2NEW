package dat.daos.impl;

import dat.daos.IDAO;
import dat.entities.NPC;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

public class NPCDAO implements IDAO<NPC, Integer> {

    private final EntityManagerFactory emf;

    public NPCDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public NPC read(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(NPC.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<NPC> readAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT n FROM NPC n", NPC.class).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public NPC create(NPC npc) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(npc);
            em.getTransaction().commit();
            return npc;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public NPC update(Integer id, NPC updatedNPC) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            NPC existingNPC = em.find(NPC.class, id);
            if (existingNPC != null) {
                // 🔹 Only merge the entity (no manual field setting needed)
                em.merge(updatedNPC);
            }
            em.getTransaction().commit();
            return existingNPC;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            NPC npc = em.find(NPC.class, id);
            if (npc != null) {
                em.remove(npc);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public boolean validatePrimaryKey(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(NPC.class, id) != null;
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
}
