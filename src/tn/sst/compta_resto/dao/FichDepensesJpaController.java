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
import tn.sst.compta_resto.pojos.FichFamilleDep;
import tn.sst.compta_resto.pojos.FichRecetteDep;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import tn.sst.compta_resto.dao.exceptions.NonexistentEntityException;
import tn.sst.compta_resto.pojos.FichDepenses;
import tn.sst.grossiste.dto.emf.BaseDAO;

/**
 *
 * @author NEJI Walid
 */
public class FichDepensesJpaController implements Serializable {

    public FichDepensesJpaController() {
        this.emf = BaseDAO.emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FichDepenses fichDepenses) {
        if (fichDepenses.getFichRecetteDepList() == null) {
            fichDepenses.setFichRecetteDepList(new ArrayList<FichRecetteDep>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FichFamilleDep idFamilleDep = fichDepenses.getIdFamilleDep();
            if (idFamilleDep != null) {
                idFamilleDep = em.getReference(idFamilleDep.getClass(), idFamilleDep.getIdFamilleDep());
                fichDepenses.setIdFamilleDep(idFamilleDep);
            }
            List<FichRecetteDep> attachedFichRecetteDepList = new ArrayList<FichRecetteDep>();
            for (FichRecetteDep fichRecetteDepListFichRecetteDepToAttach : fichDepenses.getFichRecetteDepList()) {
                fichRecetteDepListFichRecetteDepToAttach = em.getReference(fichRecetteDepListFichRecetteDepToAttach.getClass(), fichRecetteDepListFichRecetteDepToAttach.getIdRecetteRep());
                attachedFichRecetteDepList.add(fichRecetteDepListFichRecetteDepToAttach);
            }
            fichDepenses.setFichRecetteDepList(attachedFichRecetteDepList);
            em.persist(fichDepenses);
            if (idFamilleDep != null) {
                idFamilleDep.getFichDepensesList().add(fichDepenses);
                idFamilleDep = em.merge(idFamilleDep);
            }
            for (FichRecetteDep fichRecetteDepListFichRecetteDep : fichDepenses.getFichRecetteDepList()) {
                FichDepenses oldIdDepOfFichRecetteDepListFichRecetteDep = fichRecetteDepListFichRecetteDep.getIdDep();
                fichRecetteDepListFichRecetteDep.setIdDep(fichDepenses);
                fichRecetteDepListFichRecetteDep = em.merge(fichRecetteDepListFichRecetteDep);
                if (oldIdDepOfFichRecetteDepListFichRecetteDep != null) {
                    oldIdDepOfFichRecetteDepListFichRecetteDep.getFichRecetteDepList().remove(fichRecetteDepListFichRecetteDep);
                    oldIdDepOfFichRecetteDepListFichRecetteDep = em.merge(oldIdDepOfFichRecetteDepListFichRecetteDep);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FichDepenses fichDepenses) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FichDepenses persistentFichDepenses = em.find(FichDepenses.class, fichDepenses.getIdDep());
            FichFamilleDep idFamilleDepOld = persistentFichDepenses.getIdFamilleDep();
            FichFamilleDep idFamilleDepNew = fichDepenses.getIdFamilleDep();
            List<FichRecetteDep> fichRecetteDepListOld = persistentFichDepenses.getFichRecetteDepList();
            List<FichRecetteDep> fichRecetteDepListNew = fichDepenses.getFichRecetteDepList();
            if (idFamilleDepNew != null) {
                idFamilleDepNew = em.getReference(idFamilleDepNew.getClass(), idFamilleDepNew.getIdFamilleDep());
                fichDepenses.setIdFamilleDep(idFamilleDepNew);
            }
            List<FichRecetteDep> attachedFichRecetteDepListNew = new ArrayList<FichRecetteDep>();
            for (FichRecetteDep fichRecetteDepListNewFichRecetteDepToAttach : fichRecetteDepListNew) {
                fichRecetteDepListNewFichRecetteDepToAttach = em.getReference(fichRecetteDepListNewFichRecetteDepToAttach.getClass(), fichRecetteDepListNewFichRecetteDepToAttach.getIdRecetteRep());
                attachedFichRecetteDepListNew.add(fichRecetteDepListNewFichRecetteDepToAttach);
            }
            fichRecetteDepListNew = attachedFichRecetteDepListNew;
            fichDepenses.setFichRecetteDepList(fichRecetteDepListNew);
            fichDepenses = em.merge(fichDepenses);
            if (idFamilleDepOld != null && !idFamilleDepOld.equals(idFamilleDepNew)) {
                idFamilleDepOld.getFichDepensesList().remove(fichDepenses);
                idFamilleDepOld = em.merge(idFamilleDepOld);
            }
            if (idFamilleDepNew != null && !idFamilleDepNew.equals(idFamilleDepOld)) {
                idFamilleDepNew.getFichDepensesList().add(fichDepenses);
                idFamilleDepNew = em.merge(idFamilleDepNew);
            }
            for (FichRecetteDep fichRecetteDepListOldFichRecetteDep : fichRecetteDepListOld) {
                if (!fichRecetteDepListNew.contains(fichRecetteDepListOldFichRecetteDep)) {
                    fichRecetteDepListOldFichRecetteDep.setIdDep(null);
                    fichRecetteDepListOldFichRecetteDep = em.merge(fichRecetteDepListOldFichRecetteDep);
                }
            }
            for (FichRecetteDep fichRecetteDepListNewFichRecetteDep : fichRecetteDepListNew) {
                if (!fichRecetteDepListOld.contains(fichRecetteDepListNewFichRecetteDep)) {
                    FichDepenses oldIdDepOfFichRecetteDepListNewFichRecetteDep = fichRecetteDepListNewFichRecetteDep.getIdDep();
                    fichRecetteDepListNewFichRecetteDep.setIdDep(fichDepenses);
                    fichRecetteDepListNewFichRecetteDep = em.merge(fichRecetteDepListNewFichRecetteDep);
                    if (oldIdDepOfFichRecetteDepListNewFichRecetteDep != null && !oldIdDepOfFichRecetteDepListNewFichRecetteDep.equals(fichDepenses)) {
                        oldIdDepOfFichRecetteDepListNewFichRecetteDep.getFichRecetteDepList().remove(fichRecetteDepListNewFichRecetteDep);
                        oldIdDepOfFichRecetteDepListNewFichRecetteDep = em.merge(oldIdDepOfFichRecetteDepListNewFichRecetteDep);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = fichDepenses.getIdDep();
                if (findFichDepenses(id) == null) {
                    throw new NonexistentEntityException("The fichDepenses with id " + id + " no longer exists.");
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
            FichDepenses fichDepenses;
            try {
                fichDepenses = em.getReference(FichDepenses.class, id);
                fichDepenses.getIdDep();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The fichDepenses with id " + id + " no longer exists.", enfe);
            }
            FichFamilleDep idFamilleDep = fichDepenses.getIdFamilleDep();
            if (idFamilleDep != null) {
                idFamilleDep.getFichDepensesList().remove(fichDepenses);
                idFamilleDep = em.merge(idFamilleDep);
            }
            List<FichRecetteDep> fichRecetteDepList = fichDepenses.getFichRecetteDepList();
            for (FichRecetteDep fichRecetteDepListFichRecetteDep : fichRecetteDepList) {
                fichRecetteDepListFichRecetteDep.setIdDep(null);
                fichRecetteDepListFichRecetteDep = em.merge(fichRecetteDepListFichRecetteDep);
            }
            em.remove(fichDepenses);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FichDepenses> findFichDepensesEntities() {
        return findFichDepensesEntities(true, -1, -1);
    }

    public List<FichDepenses> findFichDepensesEntities(int maxResults, int firstResult) {
        return findFichDepensesEntities(false, maxResults, firstResult);
    }

    private List<FichDepenses> findFichDepensesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FichDepenses.class));
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

    public FichDepenses findFichDepenses(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FichDepenses.class, id);
        } finally {
            em.close();
        }
    }

    public int getFichDepensesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FichDepenses> rt = cq.from(FichDepenses.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public FichDepenses findByNom(String nom) {
        List<FichDepenses> ffs = getEntityManager().createNamedQuery("FichDepenses.findByLibelle", FichDepenses.class).setParameter("libelle", nom).getResultList();
        if (ffs.size() > 0) {
            return ffs.get(0);
        }
        return null;
    }

    public List<FichDepenses> findAll() {
        return getEntityManager().createQuery("select p from FichDepenses p where p.etat = :e order by p.libelle asc").setParameter("e", 'A').getResultList();
    }
    
     public List<FichDepenses> findDepByFamille(FichFamilleDep  dep) {
        return getEntityManager().createQuery("select p from FichDepenses p where p.etat = :e and p.idFamilleDep = :f order by p.libelle asc").setParameter("e", 'A').setParameter("f", dep).getResultList();
    }
    
   
    
    
}
