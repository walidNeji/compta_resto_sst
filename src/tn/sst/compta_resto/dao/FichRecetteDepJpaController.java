/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.sst.compta_resto.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TemporalType;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import tn.sst.compta_resto.dao.exceptions.NonexistentEntityException;
import tn.sst.compta_resto.pojos.FichDepenses;
import tn.sst.compta_resto.pojos.FichRecetteDep;
import tn.sst.grossiste.dto.emf.BaseDAO;

/**
 *
 * @author NEJI Walid
 */
public class FichRecetteDepJpaController implements Serializable {
    
    public FichRecetteDepJpaController() {
        this.emf = BaseDAO.emf;
    }
    private EntityManagerFactory emf = null;
    
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public void create(FichRecetteDep fichRecetteDep) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FichDepenses idDep = fichRecetteDep.getIdDep();
            if (idDep != null) {
                idDep = em.getReference(idDep.getClass(), idDep.getIdDep());
                fichRecetteDep.setIdDep(idDep);
            }
            em.persist(fichRecetteDep);
            if (idDep != null) {
                idDep.getFichRecetteDepList().add(fichRecetteDep);
                idDep = em.merge(idDep);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public void edit(FichRecetteDep fichRecetteDep) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FichRecetteDep persistentFichRecetteDep = em.find(FichRecetteDep.class, fichRecetteDep.getIdRecetteRep());
            FichDepenses idDepOld = persistentFichRecetteDep.getIdDep();
            FichDepenses idDepNew = fichRecetteDep.getIdDep();
            if (idDepNew != null) {
                idDepNew = em.getReference(idDepNew.getClass(), idDepNew.getIdDep());
                fichRecetteDep.setIdDep(idDepNew);
            }
            fichRecetteDep = em.merge(fichRecetteDep);
            if (idDepOld != null && !idDepOld.equals(idDepNew)) {
                idDepOld.getFichRecetteDepList().remove(fichRecetteDep);
                idDepOld = em.merge(idDepOld);
            }
            if (idDepNew != null && !idDepNew.equals(idDepOld)) {
                idDepNew.getFichRecetteDepList().add(fichRecetteDep);
                idDepNew = em.merge(idDepNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = fichRecetteDep.getIdRecetteRep();
                if (findFichRecetteDep(id) == null) {
                    throw new NonexistentEntityException("The fichRecetteDep with id " + id + " no longer exists.");
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
            FichRecetteDep fichRecetteDep;
            try {
                fichRecetteDep = em.getReference(FichRecetteDep.class, id);
                fichRecetteDep.getIdRecetteRep();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The fichRecetteDep with id " + id + " no longer exists.", enfe);
            }
            FichDepenses idDep = fichRecetteDep.getIdDep();
            if (idDep != null) {
                idDep.getFichRecetteDepList().remove(fichRecetteDep);
                idDep = em.merge(idDep);
            }
            em.remove(fichRecetteDep);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public List<FichRecetteDep> findFichRecetteDepEntities() {
        return findFichRecetteDepEntities(true, -1, -1);
    }
    
    public List<FichRecetteDep> findFichRecetteDepEntities(int maxResults, int firstResult) {
        return findFichRecetteDepEntities(false, maxResults, firstResult);
    }
    
    private List<FichRecetteDep> findFichRecetteDepEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FichRecetteDep.class));
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
    
    public FichRecetteDep findFichRecetteDep(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FichRecetteDep.class, id);
        } finally {
            em.close();
        }
    }
    
    public int getFichRecetteDepCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FichRecetteDep> rt = cq.from(FichRecetteDep.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public float getDepenseTotalByDate(Date date, String type) {
        List deps = getEntityManager()
                .createQuery("SELECT SUM(R.montant) FROM FichRecetteDep R WHERE R.date = :d and R.typeDep like :t GROUP BY R.date ")
                .setParameter("d", date, TemporalType.DATE)
                .setParameter("t", type + "%").getResultList();
        if (deps.size() > 0) {
            return Float.parseFloat(deps.get(0).toString());
        } else {
            return 0;
        }
    }
    
    public List<FichRecetteDep> getDepensesBetweenTwoDate(Date deb, Date fin, String type) {
        
        return getEntityManager()
                .createQuery("SELECT R FROM FichRecetteDep R WHERE (R.typeDep like :t) AND (R.date between :d and :f)  ")
                .setParameter("d", deb, TemporalType.DATE)
                .setParameter("f", fin, TemporalType.DATE)
                .setParameter("t", type + "%").getResultList();
    }
    
    public FichRecetteDep findByLibAndTypeAndDate(String lib, String type, Date d, float mnt) {
        List<FichRecetteDep> ffs = getEntityManager().createQuery("SELECT D FROM FichRecetteDep D "
                + "WHERE D.idDep.libelle = :lib AND  D.typeDep = :f AND  D.montant = :m AND  D.date= :d  ")
                .setParameter("lib", lib).setParameter("f", type).setParameter("m", mnt).setParameter("d", d).getResultList();
        if (ffs.size() > 0) {
            return ffs.get(0);
        }
        return null;
    }
}
