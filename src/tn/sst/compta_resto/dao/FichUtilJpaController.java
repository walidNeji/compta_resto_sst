/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.sst.compta_resto.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import tn.sst.compta_resto.dao.exceptions.NonexistentEntityException;
import tn.sst.compta_resto.pojos.FichUtil;

/**
 *
 * @author NEJI Walid
 */
public class FichUtilJpaController implements Serializable {

    public FichUtilJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FichUtil fichUtil) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(fichUtil);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FichUtil fichUtil) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            fichUtil = em.merge(fichUtil);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = fichUtil.getCleut();
                if (findFichUtil(id) == null) {
                    throw new NonexistentEntityException("The fichUtil with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FichUtil fichUtil;
            try {
                fichUtil = em.getReference(FichUtil.class, id);
                fichUtil.getCleut();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The fichUtil with id " + id + " no longer exists.", enfe);
            }
            em.remove(fichUtil);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FichUtil> findFichUtilEntities() {
        return findFichUtilEntities(true, -1, -1);
    }

    public List<FichUtil> findFichUtilEntities(int maxResults, int firstResult) {
        return findFichUtilEntities(false, maxResults, firstResult);
    }

    private List<FichUtil> findFichUtilEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FichUtil.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public FichUtil findFichUtil(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FichUtil.class, id);
        } finally {
            em.close();
        }
    }

    public int getFichUtilCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FichUtil> rt = cq.from(FichUtil.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
