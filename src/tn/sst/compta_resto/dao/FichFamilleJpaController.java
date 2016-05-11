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
import tn.sst.compta_resto.pojos.FichProduit;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import org.eclipse.persistence.internal.jpa.querydef.OrderImpl;
import tn.sst.compta_resto.dao.exceptions.NonexistentEntityException;
import tn.sst.compta_resto.pojos.FichFamille;
import tn.sst.grossiste.dto.emf.BaseDAO;

/**
 *
 * @author NEJI Walid
 */
public class FichFamilleJpaController implements Serializable {

    public FichFamilleJpaController() {
        this.emf = BaseDAO.emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FichFamille fichFamille) {
        if (fichFamille.getFichProduitList() == null) {
            fichFamille.setFichProduitList(new ArrayList<FichProduit>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<FichProduit> attachedFichProduitList = new ArrayList<FichProduit>();
            for (FichProduit fichProduitListFichProduitToAttach : fichFamille.getFichProduitList()) {
                fichProduitListFichProduitToAttach = em.getReference(fichProduitListFichProduitToAttach.getClass(), fichProduitListFichProduitToAttach.getClepdt());
                attachedFichProduitList.add(fichProduitListFichProduitToAttach);
            }
            fichFamille.setFichProduitList(attachedFichProduitList);
            em.persist(fichFamille);
            for (FichProduit fichProduitListFichProduit : fichFamille.getFichProduitList()) {
                FichFamille oldClefamilleOfFichProduitListFichProduit = fichProduitListFichProduit.getClefamille();
                fichProduitListFichProduit.setClefamille(fichFamille);
                fichProduitListFichProduit = em.merge(fichProduitListFichProduit);
                if (oldClefamilleOfFichProduitListFichProduit != null) {
                    oldClefamilleOfFichProduitListFichProduit.getFichProduitList().remove(fichProduitListFichProduit);
                    oldClefamilleOfFichProduitListFichProduit = em.merge(oldClefamilleOfFichProduitListFichProduit);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FichFamille fichFamille) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FichFamille persistentFichFamille = em.find(FichFamille.class, fichFamille.getClefamille());
            List<FichProduit> fichProduitListOld = persistentFichFamille.getFichProduitList();
            List<FichProduit> fichProduitListNew = fichFamille.getFichProduitList();
            List<FichProduit> attachedFichProduitListNew = new ArrayList<FichProduit>();
            for (FichProduit fichProduitListNewFichProduitToAttach : fichProduitListNew) {
                fichProduitListNewFichProduitToAttach = em.getReference(fichProduitListNewFichProduitToAttach.getClass(), fichProduitListNewFichProduitToAttach.getClepdt());
                attachedFichProduitListNew.add(fichProduitListNewFichProduitToAttach);
            }
            fichProduitListNew = attachedFichProduitListNew;
            fichFamille.setFichProduitList(fichProduitListNew);
            fichFamille = em.merge(fichFamille);
            for (FichProduit fichProduitListOldFichProduit : fichProduitListOld) {
                if (!fichProduitListNew.contains(fichProduitListOldFichProduit)) {
                    fichProduitListOldFichProduit.setClefamille(null);
                    fichProduitListOldFichProduit = em.merge(fichProduitListOldFichProduit);
                }
            }
            for (FichProduit fichProduitListNewFichProduit : fichProduitListNew) {
                if (!fichProduitListOld.contains(fichProduitListNewFichProduit)) {
                    FichFamille oldClefamilleOfFichProduitListNewFichProduit = fichProduitListNewFichProduit.getClefamille();
                    fichProduitListNewFichProduit.setClefamille(fichFamille);
                    fichProduitListNewFichProduit = em.merge(fichProduitListNewFichProduit);
                    if (oldClefamilleOfFichProduitListNewFichProduit != null && !oldClefamilleOfFichProduitListNewFichProduit.equals(fichFamille)) {
                        oldClefamilleOfFichProduitListNewFichProduit.getFichProduitList().remove(fichProduitListNewFichProduit);
                        oldClefamilleOfFichProduitListNewFichProduit = em.merge(oldClefamilleOfFichProduitListNewFichProduit);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = fichFamille.getClefamille();
                if (findFichFamille(id) == null) {
                    throw new NonexistentEntityException("The fichFamille with id " + id + " no longer exists.");
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
            FichFamille fichFamille;
            try {
                fichFamille = em.getReference(FichFamille.class, id);
                fichFamille.getClefamille();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The fichFamille with id " + id + " no longer exists.", enfe);
            }
            List<FichProduit> fichProduitList = fichFamille.getFichProduitList();
            for (FichProduit fichProduitListFichProduit : fichProduitList) {
                fichProduitListFichProduit.setClefamille(null);
                fichProduitListFichProduit = em.merge(fichProduitListFichProduit);
            }
            em.remove(fichFamille);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FichFamille> findFichFamilleEntities() {
        return findFichFamilleEntities(true, -1, -1);
    }

    public List<FichFamille> findFichFamilleEntities(int maxResults, int firstResult) {
        return findFichFamilleEntities(false, maxResults, firstResult);
    }

    private List<FichFamille> findFichFamilleEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery cq = cb.createQuery();
            Root<FichFamille> r=cq.from(FichFamille.class);
            cq.select(cq.from(FichFamille.class));
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

    public FichFamille findFichFamille(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FichFamille.class, id);
        } finally {
            em.close();
        }
    }

    public int getFichFamilleCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FichFamille> rt = cq.from(FichFamille.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public FichFamille findByNom(String nomvd) {
        List<FichFamille> clients = getEntityManager().createNamedQuery("FichFamille.findByLibelle", FichFamille.class).setParameter("libelle", nomvd).getResultList();
        if (!clients.isEmpty() && clients.size() > 0) {
            return clients.get(0);
        }
        return null;
    }

    public List<FichProduit> findProduitByFamille(FichFamille famille) {
        return getEntityManager().createQuery("select p from FichProduit p where p.clefamille = :f and p.etat = 'A' order by p.libelle asc")
                .setParameter("f", famille).getResultList();
    }
}
