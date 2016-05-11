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
import tn.sst.compta_resto.pojos.FichDepenses;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import tn.sst.compta_resto.dao.exceptions.NonexistentEntityException;
import tn.sst.compta_resto.pojos.FichFamilleDep;
import tn.sst.grossiste.dto.emf.BaseDAO;

/**
 *
 * @author NEJI Walid
 */
public class FichFamilleDepJpaController implements Serializable {

    public FichFamilleDepJpaController() {
        this.emf = BaseDAO.emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FichFamilleDep fichFamilleDep) {
        if (fichFamilleDep.getFichDepensesList() == null) {
            fichFamilleDep.setFichDepensesList(new ArrayList<FichDepenses>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<FichDepenses> attachedFichDepensesList = new ArrayList<FichDepenses>();
            for (FichDepenses fichDepensesListFichDepensesToAttach : fichFamilleDep.getFichDepensesList()) {
                fichDepensesListFichDepensesToAttach = em.getReference(fichDepensesListFichDepensesToAttach.getClass(), fichDepensesListFichDepensesToAttach.getIdDep());
                attachedFichDepensesList.add(fichDepensesListFichDepensesToAttach);
            }
            fichFamilleDep.setFichDepensesList(attachedFichDepensesList);
            em.persist(fichFamilleDep);
            for (FichDepenses fichDepensesListFichDepenses : fichFamilleDep.getFichDepensesList()) {
                FichFamilleDep oldIdFamilleDepOfFichDepensesListFichDepenses = fichDepensesListFichDepenses.getIdFamilleDep();
                fichDepensesListFichDepenses.setIdFamilleDep(fichFamilleDep);
                fichDepensesListFichDepenses = em.merge(fichDepensesListFichDepenses);
                if (oldIdFamilleDepOfFichDepensesListFichDepenses != null) {
                    oldIdFamilleDepOfFichDepensesListFichDepenses.getFichDepensesList().remove(fichDepensesListFichDepenses);
                    oldIdFamilleDepOfFichDepensesListFichDepenses = em.merge(oldIdFamilleDepOfFichDepensesListFichDepenses);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FichFamilleDep fichFamilleDep) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FichFamilleDep persistentFichFamilleDep = em.find(FichFamilleDep.class, fichFamilleDep.getIdFamilleDep());
            List<FichDepenses> fichDepensesListOld = persistentFichFamilleDep.getFichDepensesList();
            List<FichDepenses> fichDepensesListNew = fichFamilleDep.getFichDepensesList();
            List<FichDepenses> attachedFichDepensesListNew = new ArrayList<FichDepenses>();
            for (FichDepenses fichDepensesListNewFichDepensesToAttach : fichDepensesListNew) {
                fichDepensesListNewFichDepensesToAttach = em.getReference(fichDepensesListNewFichDepensesToAttach.getClass(), fichDepensesListNewFichDepensesToAttach.getIdDep());
                attachedFichDepensesListNew.add(fichDepensesListNewFichDepensesToAttach);
            }
            fichDepensesListNew = attachedFichDepensesListNew;
            fichFamilleDep.setFichDepensesList(fichDepensesListNew);
            fichFamilleDep = em.merge(fichFamilleDep);
            for (FichDepenses fichDepensesListOldFichDepenses : fichDepensesListOld) {
                if (!fichDepensesListNew.contains(fichDepensesListOldFichDepenses)) {
                    fichDepensesListOldFichDepenses.setIdFamilleDep(null);
                    fichDepensesListOldFichDepenses = em.merge(fichDepensesListOldFichDepenses);
                }
            }
            for (FichDepenses fichDepensesListNewFichDepenses : fichDepensesListNew) {
                if (!fichDepensesListOld.contains(fichDepensesListNewFichDepenses)) {
                    FichFamilleDep oldIdFamilleDepOfFichDepensesListNewFichDepenses = fichDepensesListNewFichDepenses.getIdFamilleDep();
                    fichDepensesListNewFichDepenses.setIdFamilleDep(fichFamilleDep);
                    fichDepensesListNewFichDepenses = em.merge(fichDepensesListNewFichDepenses);
                    if (oldIdFamilleDepOfFichDepensesListNewFichDepenses != null && !oldIdFamilleDepOfFichDepensesListNewFichDepenses.equals(fichFamilleDep)) {
                        oldIdFamilleDepOfFichDepensesListNewFichDepenses.getFichDepensesList().remove(fichDepensesListNewFichDepenses);
                        oldIdFamilleDepOfFichDepensesListNewFichDepenses = em.merge(oldIdFamilleDepOfFichDepensesListNewFichDepenses);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = fichFamilleDep.getIdFamilleDep();
                if (findFichFamilleDep(id) == null) {
                    throw new NonexistentEntityException("The fichFamilleDep with id " + id + " no longer exists.");
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
            FichFamilleDep fichFamilleDep;
            try {
                fichFamilleDep = em.getReference(FichFamilleDep.class, id);
                fichFamilleDep.getIdFamilleDep();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The fichFamilleDep with id " + id + " no longer exists.", enfe);
            }
            List<FichDepenses> fichDepensesList = fichFamilleDep.getFichDepensesList();
            for (FichDepenses fichDepensesListFichDepenses : fichDepensesList) {
                fichDepensesListFichDepenses.setIdFamilleDep(null);
                fichDepensesListFichDepenses = em.merge(fichDepensesListFichDepenses);
            }
            em.remove(fichFamilleDep);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FichFamilleDep> findFichFamilleDepEntities() {
        return findFichFamilleDepEntities(true, -1, -1);
    }

    public List<FichFamilleDep> findFichFamilleDepEntities(int maxResults, int firstResult) {
        return findFichFamilleDepEntities(false, maxResults, firstResult);
    }

    private List<FichFamilleDep> findFichFamilleDepEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb=em.getCriteriaBuilder();
            CriteriaQuery cq = cb.createQuery();
            Root<FichFamilleDep> r=cq.from(FichFamilleDep.class);
            cq.select(cq.from(FichFamilleDep.class));
            cq.orderBy(cb.asc(r.get("libelle")));
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

    public FichFamilleDep findFichFamilleDep(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FichFamilleDep.class, id);
        } finally {
            em.close();
        }
    }

    public int getFichFamilleDepCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FichFamilleDep> rt = cq.from(FichFamilleDep.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public FichFamilleDep findByNom(String nom) {
        List<FichFamilleDep> ffs = getEntityManager().createNamedQuery("FichFamilleDep.findByLibelle", FichFamilleDep.class).setParameter("libelle", nom).getResultList();
        if (ffs.size() > 0) {
            return ffs.get(0);
        }
        return null;

    }
}
