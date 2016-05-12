/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.sst.compta_resto.dao;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import tn.sst.compta_resto.pojos.FichDetailsVente;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TemporalType;
import tn.sst.compta_resto.dao.exceptions.NonexistentEntityException;
import tn.sst.compta_resto.pojos.FichVente;
import tn.sst.grossiste.dto.emf.BaseDAO;

/**
 *
 * @author NEJI Walid
 */
public class FichVenteJpaController implements Serializable {

    public FichVenteJpaController() {
        this.emf = BaseDAO.emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FichVente fichVente) {
        if (fichVente.getFichDetailsVenteList() == null) {
            fichVente.setFichDetailsVenteList(new ArrayList<FichDetailsVente>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();

            em.persist(fichVente);

            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FichVente fichVente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FichVente persistentFichVente = em.find(FichVente.class, fichVente.getClevente());
            List<FichDetailsVente> fichDetailsVenteListOld = persistentFichVente.getFichDetailsVenteList();
            List<FichDetailsVente> fichDetailsVenteListNew = fichVente.getFichDetailsVenteList();
            List<FichDetailsVente> attachedFichDetailsVenteListNew = new ArrayList<FichDetailsVente>();
            for (FichDetailsVente fichDetailsVenteListNewFichDetailsVenteToAttach : fichDetailsVenteListNew) {
                fichDetailsVenteListNewFichDetailsVenteToAttach = em.getReference(fichDetailsVenteListNewFichDetailsVenteToAttach.getClass(), fichDetailsVenteListNewFichDetailsVenteToAttach.getCleDetailsVente());
                attachedFichDetailsVenteListNew.add(fichDetailsVenteListNewFichDetailsVenteToAttach);
            }
            fichDetailsVenteListNew = attachedFichDetailsVenteListNew;
            fichVente.setFichDetailsVenteList(fichDetailsVenteListNew);
            fichVente = em.merge(fichVente);
            for (FichDetailsVente fichDetailsVenteListOldFichDetailsVente : fichDetailsVenteListOld) {
                if (!fichDetailsVenteListNew.contains(fichDetailsVenteListOldFichDetailsVente)) {
                    fichDetailsVenteListOldFichDetailsVente.setCleFichVente(null);
                    fichDetailsVenteListOldFichDetailsVente = em.merge(fichDetailsVenteListOldFichDetailsVente);
                }
            }
            for (FichDetailsVente fichDetailsVenteListNewFichDetailsVente : fichDetailsVenteListNew) {
                if (!fichDetailsVenteListOld.contains(fichDetailsVenteListNewFichDetailsVente)) {
                    FichVente oldCleFichVenteOfFichDetailsVenteListNewFichDetailsVente = fichDetailsVenteListNewFichDetailsVente.getCleFichVente();
                    fichDetailsVenteListNewFichDetailsVente.setCleFichVente(fichVente);
                    fichDetailsVenteListNewFichDetailsVente = em.merge(fichDetailsVenteListNewFichDetailsVente);
                    if (oldCleFichVenteOfFichDetailsVenteListNewFichDetailsVente != null && !oldCleFichVenteOfFichDetailsVenteListNewFichDetailsVente.equals(fichVente)) {
                        oldCleFichVenteOfFichDetailsVenteListNewFichDetailsVente.getFichDetailsVenteList().remove(fichDetailsVenteListNewFichDetailsVente);
                        oldCleFichVenteOfFichDetailsVenteListNewFichDetailsVente = em.merge(oldCleFichVenteOfFichDetailsVenteListNewFichDetailsVente);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = fichVente.getClevente();
                if (findFichVente(id) == null) {
                    throw new NonexistentEntityException("The fichVente with id " + id + " no longer exists.");
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
            FichVente fichVente;
            try {
                fichVente = em.getReference(FichVente.class, id);
                fichVente.getClevente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The fichVente with id " + id + " no longer exists.", enfe);
            }
            List<FichDetailsVente> fichDetailsVenteList = fichVente.getFichDetailsVenteList();
            for (FichDetailsVente fichDetailsVenteListFichDetailsVente : fichDetailsVenteList) {
                fichDetailsVenteListFichDetailsVente.setCleFichVente(null);
                fichDetailsVenteListFichDetailsVente = em.merge(fichDetailsVenteListFichDetailsVente);
            }
            em.remove(fichVente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FichVente> findFichVenteEntities() {
        return findFichVenteEntities(true, -1, -1);
    }

    public List<FichVente> findFichVenteEntities(int maxResults, int firstResult) {
        return findFichVenteEntities(false, maxResults, firstResult);
    }

    private List<FichVente> findFichVenteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FichVente.class));
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

    public FichVente findFichVente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FichVente.class, id);
        } finally {
            em.close();
        }
    }

    public int getFichVenteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FichVente> rt = cq.from(FichVente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public float getVentesTotalByDate(Date date, String type) {
        List deps = getEntityManager()
                .createQuery("SELECT SUM(R.montantVente) FROM FichVente R WHERE R.dateVente = :d and R.typeVente like :t GROUP BY R.dateVente ")
                .setParameter("d", date, TemporalType.DATE)
                .setParameter("t",  type + "%").getResultList();
        if (deps.size() > 0) {
            return Float.parseFloat(deps.get(0).toString());
        } else {
            return 0;
        }
    }
    
    public List<FichVente> getFichVenteBetweentwoDate(Date deb , Date fin , String type){
        
       return getEntityManager()
                .createQuery("SELECT R FROM FichVente R WHERE (R.typeVente like :t) AND (R.dateVente between :d and :f)  ")
                .setParameter("d", deb, TemporalType.DATE)
                .setParameter("f", fin, TemporalType.DATE)
                .setParameter("t",  type + "%").getResultList();
        
    }
 
}
