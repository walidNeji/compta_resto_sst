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
import tn.sst.compta_resto.objects.StatObject;
import tn.sst.compta_resto.pojos.FichDetailsVente;
import tn.sst.compta_resto.pojos.FichVente;
import tn.sst.compta_resto.pojos.FichProduit;
import tn.sst.grossiste.dto.emf.BaseDAO;

/**
 *
 * @author NEJI Walid
 */
public class FichDetailsVenteJpaController implements Serializable {

    public FichDetailsVenteJpaController() {
        this.emf = BaseDAO.emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FichDetailsVente fichDetailsVente) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FichVente cleFichVente = fichDetailsVente.getCleFichVente();
            if (cleFichVente != null) {
                cleFichVente = em.getReference(cleFichVente.getClass(), cleFichVente.getClevente());
                fichDetailsVente.setCleFichVente(cleFichVente);
            }
            FichProduit clePdt = fichDetailsVente.getClePdt();
            if (clePdt != null) {
                clePdt = em.getReference(clePdt.getClass(), clePdt.getClepdt());
                fichDetailsVente.setClePdt(clePdt);
            }
            em.persist(fichDetailsVente);
            if (cleFichVente != null) {
                cleFichVente.getFichDetailsVenteList().add(fichDetailsVente);
                cleFichVente = em.merge(cleFichVente);
            }
            if (clePdt != null) {
                clePdt.getFichDetailsVenteList().add(fichDetailsVente);
                clePdt = em.merge(clePdt);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FichDetailsVente fichDetailsVente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FichDetailsVente persistentFichDetailsVente = em.find(FichDetailsVente.class, fichDetailsVente.getCleDetailsVente());
            FichVente cleFichVenteOld = persistentFichDetailsVente.getCleFichVente();
            FichVente cleFichVenteNew = fichDetailsVente.getCleFichVente();
            FichProduit clePdtOld = persistentFichDetailsVente.getClePdt();
            FichProduit clePdtNew = fichDetailsVente.getClePdt();
            if (cleFichVenteNew != null) {
                cleFichVenteNew = em.getReference(cleFichVenteNew.getClass(), cleFichVenteNew.getClevente());
                fichDetailsVente.setCleFichVente(cleFichVenteNew);
            }
            if (clePdtNew != null) {
                clePdtNew = em.getReference(clePdtNew.getClass(), clePdtNew.getClepdt());
                fichDetailsVente.setClePdt(clePdtNew);
            }
            fichDetailsVente = em.merge(fichDetailsVente);
            if (cleFichVenteOld != null && !cleFichVenteOld.equals(cleFichVenteNew)) {
                cleFichVenteOld.getFichDetailsVenteList().remove(fichDetailsVente);
                cleFichVenteOld = em.merge(cleFichVenteOld);
            }
            if (cleFichVenteNew != null && !cleFichVenteNew.equals(cleFichVenteOld)) {
                cleFichVenteNew.getFichDetailsVenteList().add(fichDetailsVente);
                cleFichVenteNew = em.merge(cleFichVenteNew);
            }
            if (clePdtOld != null && !clePdtOld.equals(clePdtNew)) {
                clePdtOld.getFichDetailsVenteList().remove(fichDetailsVente);
                clePdtOld = em.merge(clePdtOld);
            }
            if (clePdtNew != null && !clePdtNew.equals(clePdtOld)) {
                clePdtNew.getFichDetailsVenteList().add(fichDetailsVente);
                clePdtNew = em.merge(clePdtNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = fichDetailsVente.getCleDetailsVente();
                if (findFichDetailsVente(id) == null) {
                    throw new NonexistentEntityException("The fichDetailsVente with id " + id + " no longer exists.");
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
            FichDetailsVente fichDetailsVente;
            try {
                fichDetailsVente = em.getReference(FichDetailsVente.class, id);
                fichDetailsVente.getCleDetailsVente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The fichDetailsVente with id " + id + " no longer exists.", enfe);
            }
            FichVente cleFichVente = fichDetailsVente.getCleFichVente();
            if (cleFichVente != null) {
                cleFichVente.getFichDetailsVenteList().remove(fichDetailsVente);
                cleFichVente = em.merge(cleFichVente);
            }
            FichProduit clePdt = fichDetailsVente.getClePdt();
            if (clePdt != null) {
                clePdt.getFichDetailsVenteList().remove(fichDetailsVente);
                clePdt = em.merge(clePdt);
            }
            em.remove(fichDetailsVente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FichDetailsVente> findFichDetailsVenteEntities() {
        return findFichDetailsVenteEntities(true, -1, -1);
    }

    public List<FichDetailsVente> findFichDetailsVenteEntities(int maxResults, int firstResult) {
        return findFichDetailsVenteEntities(false, maxResults, firstResult);
    }

    private List<FichDetailsVente> findFichDetailsVenteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FichDetailsVente.class));
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

    public FichDetailsVente findFichDetailsVente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FichDetailsVente.class, id);
        } finally {
            em.close();
        }
    }

    public int getFichDetailsVenteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FichDetailsVente> rt = cq.from(FichDetailsVente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public FichDetailsVente findByLibAndMntAndType(String lib, String type, Date d, float mnt, float qte) {
        List<FichDetailsVente> ffs = getEntityManager().createQuery("SELECT R FROM FichDetailsVente R "
                + "WHERE R.clePdt.libelle = :lib AND R.cleFichVente.typeVente = :f AND R.prixVente = :m  "
                + " AND R.quantite = :qte AND  R.cleFichVente.dateVente = :d ")
                .setParameter("lib", lib).setParameter("f", type).setParameter("m", mnt).setParameter("qte", qte).setParameter("d", d).getResultList();
        if (ffs.size() > 0) {
            return ffs.get(0);
        }
        return null;
    }

    public List<StatObject> findStatVente(Date debut, Date fin) {
        return getEntityManager().createNativeQuery("select p.libelle , sum(v.quantite)  , sum (v.prix_vente)"
                + "from fich_details_vente as v , fich_produit as p, fich_vente as fv "
                + " where p.clepdt = v.cle_pdt and v.cle_fich_vente = fv.clevente "
                + "and fv.date_vente between '" + debut + "' and '" + fin + "' "
                + "group by p.libelle"
                + "order by sum(v.quantite) desc ", StatObject.class).getResultList();
    }
    
       
     public List<FichDetailsVente> findBetweentwoDateAndProduit(Date deb , Date fin , String produit){
        
       return getEntityManager()
                .createQuery("SELECT R FROM FichDetailsVente R WHERE (R.clePdt.libelle like :t) AND (R.cleFichVente.dateVente between :d and :f)  ")
                .setParameter("d", deb, TemporalType.DATE)
                .setParameter("f", fin, TemporalType.DATE)
                .setParameter("t",  produit + "%").getResultList();
        
    }
}
