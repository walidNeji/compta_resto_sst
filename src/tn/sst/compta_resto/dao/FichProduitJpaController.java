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
import tn.sst.compta_resto.pojos.FichFamille;
import tn.sst.compta_resto.pojos.FichDetailsVente;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import tn.sst.compta_resto.dao.exceptions.NonexistentEntityException;
import tn.sst.compta_resto.pojos.FichProduit;
import tn.sst.grossiste.dto.emf.BaseDAO;

/**
 *
 * @author NEJI Walid
 */
public class FichProduitJpaController implements Serializable {

    public FichProduitJpaController() {
        this.emf = BaseDAO.emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FichProduit fichProduit) {
        if (fichProduit.getFichDetailsVenteList() == null) {
            fichProduit.setFichDetailsVenteList(new ArrayList<FichDetailsVente>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FichFamille clefamille = fichProduit.getClefamille();
            if (clefamille != null) {
                clefamille = em.getReference(clefamille.getClass(), clefamille.getClefamille());
                fichProduit.setClefamille(clefamille);
            }
            List<FichDetailsVente> attachedFichDetailsVenteList = new ArrayList<FichDetailsVente>();
            for (FichDetailsVente fichDetailsVenteListFichDetailsVenteToAttach : fichProduit.getFichDetailsVenteList()) {
                fichDetailsVenteListFichDetailsVenteToAttach = em.getReference(fichDetailsVenteListFichDetailsVenteToAttach.getClass(), fichDetailsVenteListFichDetailsVenteToAttach.getCleDetailsVente());
                attachedFichDetailsVenteList.add(fichDetailsVenteListFichDetailsVenteToAttach);
            }
            fichProduit.setFichDetailsVenteList(attachedFichDetailsVenteList);
            em.persist(fichProduit);
            if (clefamille != null) {
                clefamille.getFichProduitList().add(fichProduit);
                clefamille = em.merge(clefamille);
            }
            for (FichDetailsVente fichDetailsVenteListFichDetailsVente : fichProduit.getFichDetailsVenteList()) {
                FichProduit oldClePdtOfFichDetailsVenteListFichDetailsVente = fichDetailsVenteListFichDetailsVente.getClePdt();
                fichDetailsVenteListFichDetailsVente.setClePdt(fichProduit);
                fichDetailsVenteListFichDetailsVente = em.merge(fichDetailsVenteListFichDetailsVente);
                if (oldClePdtOfFichDetailsVenteListFichDetailsVente != null) {
                    oldClePdtOfFichDetailsVenteListFichDetailsVente.getFichDetailsVenteList().remove(fichDetailsVenteListFichDetailsVente);
                    oldClePdtOfFichDetailsVenteListFichDetailsVente = em.merge(oldClePdtOfFichDetailsVenteListFichDetailsVente);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FichProduit fichProduit) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FichProduit persistentFichProduit = em.find(FichProduit.class, fichProduit.getClepdt());
            FichFamille clefamilleOld = persistentFichProduit.getClefamille();
            FichFamille clefamilleNew = fichProduit.getClefamille();
            List<FichDetailsVente> fichDetailsVenteListOld = persistentFichProduit.getFichDetailsVenteList();
            List<FichDetailsVente> fichDetailsVenteListNew = fichProduit.getFichDetailsVenteList();
            if (clefamilleNew != null) {
                clefamilleNew = em.getReference(clefamilleNew.getClass(), clefamilleNew.getClefamille());
                fichProduit.setClefamille(clefamilleNew);
            }
            List<FichDetailsVente> attachedFichDetailsVenteListNew = new ArrayList<FichDetailsVente>();
            for (FichDetailsVente fichDetailsVenteListNewFichDetailsVenteToAttach : fichDetailsVenteListNew) {
                fichDetailsVenteListNewFichDetailsVenteToAttach = em.getReference(fichDetailsVenteListNewFichDetailsVenteToAttach.getClass(), fichDetailsVenteListNewFichDetailsVenteToAttach.getCleDetailsVente());
                attachedFichDetailsVenteListNew.add(fichDetailsVenteListNewFichDetailsVenteToAttach);
            }
            fichDetailsVenteListNew = attachedFichDetailsVenteListNew;
            fichProduit.setFichDetailsVenteList(fichDetailsVenteListNew);
            fichProduit = em.merge(fichProduit);
            if (clefamilleOld != null && !clefamilleOld.equals(clefamilleNew)) {
                clefamilleOld.getFichProduitList().remove(fichProduit);
                clefamilleOld = em.merge(clefamilleOld);
            }
            if (clefamilleNew != null && !clefamilleNew.equals(clefamilleOld)) {
                clefamilleNew.getFichProduitList().add(fichProduit);
                clefamilleNew = em.merge(clefamilleNew);
            }
            for (FichDetailsVente fichDetailsVenteListOldFichDetailsVente : fichDetailsVenteListOld) {
                if (!fichDetailsVenteListNew.contains(fichDetailsVenteListOldFichDetailsVente)) {
                    fichDetailsVenteListOldFichDetailsVente.setClePdt(null);
                    fichDetailsVenteListOldFichDetailsVente = em.merge(fichDetailsVenteListOldFichDetailsVente);
                }
            }
            for (FichDetailsVente fichDetailsVenteListNewFichDetailsVente : fichDetailsVenteListNew) {
                if (!fichDetailsVenteListOld.contains(fichDetailsVenteListNewFichDetailsVente)) {
                    FichProduit oldClePdtOfFichDetailsVenteListNewFichDetailsVente = fichDetailsVenteListNewFichDetailsVente.getClePdt();
                    fichDetailsVenteListNewFichDetailsVente.setClePdt(fichProduit);
                    fichDetailsVenteListNewFichDetailsVente = em.merge(fichDetailsVenteListNewFichDetailsVente);
                    if (oldClePdtOfFichDetailsVenteListNewFichDetailsVente != null && !oldClePdtOfFichDetailsVenteListNewFichDetailsVente.equals(fichProduit)) {
                        oldClePdtOfFichDetailsVenteListNewFichDetailsVente.getFichDetailsVenteList().remove(fichDetailsVenteListNewFichDetailsVente);
                        oldClePdtOfFichDetailsVenteListNewFichDetailsVente = em.merge(oldClePdtOfFichDetailsVenteListNewFichDetailsVente);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = fichProduit.getClepdt();
                if (findFichProduit(id) == null) {
                    throw new NonexistentEntityException("The fichProduit with id " + id + " no longer exists.");
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
            FichProduit fichProduit;
            try {
                fichProduit = em.getReference(FichProduit.class, id);
                fichProduit.getClepdt();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The fichProduit with id " + id + " no longer exists.", enfe);
            }
            FichFamille clefamille = fichProduit.getClefamille();
            if (clefamille != null) {
                clefamille.getFichProduitList().remove(fichProduit);
                clefamille = em.merge(clefamille);
            }
            List<FichDetailsVente> fichDetailsVenteList = fichProduit.getFichDetailsVenteList();
            for (FichDetailsVente fichDetailsVenteListFichDetailsVente : fichDetailsVenteList) {
                fichDetailsVenteListFichDetailsVente.setClePdt(null);
                fichDetailsVenteListFichDetailsVente = em.merge(fichDetailsVenteListFichDetailsVente);
            }
            em.remove(fichProduit);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FichProduit> findFichProduitEntities() {
        return findFichProduitEntities(true, -1, -1);
    }

    public List<FichProduit> findFichProduitEntities(int maxResults, int firstResult) {
        return findFichProduitEntities(false, maxResults, firstResult);
    }

    private List<FichProduit> findFichProduitEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FichProduit.class));
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

    public FichProduit findFichProduit(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FichProduit.class, id);
        } finally {
            em.close();
        }
    }

    public int getFichProduitCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FichProduit> rt = cq.from(FichProduit.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public FichProduit findByNom(String nom) {
        List<FichProduit> ffs = getEntityManager().createNamedQuery("FichProduit.findByLibelle", FichProduit.class).setParameter("libelle", nom).getResultList();
        if (ffs.size() > 0) {
            return ffs.get(0);
        }
        return null;
    }

    public List<FichProduit> findAll() {
        return getEntityManager().createQuery("select p from FichProduit p where p.etat = :e order by p.libelle asc").setParameter("e", 'A').getResultList();
    }
}
