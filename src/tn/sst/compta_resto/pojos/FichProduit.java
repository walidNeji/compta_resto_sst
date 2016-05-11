/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.sst.compta_resto.pojos;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author NEJI Walid
 */
@Entity
@Table(name = "fich_produit")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FichProduit.findAll", query = "SELECT f FROM FichProduit f"),
    @NamedQuery(name = "FichProduit.findByClepdt", query = "SELECT f FROM FichProduit f WHERE f.clepdt = :clepdt"),
    @NamedQuery(name = "FichProduit.findByLibelle", query = "SELECT f FROM FichProduit f WHERE f.libelle = :libelle AND f.etat = 'A'"),
    @NamedQuery(name = "FichProduit.findByPrixVente", query = "SELECT f FROM FichProduit f WHERE f.prixVente = :prixVente"),
    @NamedQuery(name = "FichProduit.findByPrixAchat", query = "SELECT f FROM FichProduit f WHERE f.prixAchat = :prixAchat"),
    @NamedQuery(name = "FichProduit.findByStock", query = "SELECT f FROM FichProduit f WHERE f.stock = :stock")})
public class FichProduit implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "clepdt")
    private Integer clepdt;
    @Column(name = "libelle")
    private String libelle;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "prix_vente")
    private float prixVente;
    @Column(name = "prix_achat")
    private float prixAchat;
    @Column(name = "stock")
    private Integer stock;
    @Column(name = "etat")
    private char etat;
    @OneToMany(mappedBy = "clePdt", fetch = FetchType.LAZY)
    private List<FichDetailsVente> fichDetailsVenteList;
    @JoinColumn(name = "clefamille", referencedColumnName = "clefamille")
    @ManyToOne(fetch = FetchType.LAZY)
    private FichFamille clefamille;

    public FichProduit() {
    }

    public FichProduit(Integer clepdt) {
        this.clepdt = clepdt;
    }

    public Integer getClepdt() {
        return clepdt;
    }

    public void setClepdt(Integer clepdt) {
        Integer oldClepdt = this.clepdt;
        this.clepdt = clepdt;
        changeSupport.firePropertyChange("clepdt", oldClepdt, clepdt);
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        String oldLibelle = this.libelle;
        this.libelle = libelle;
        changeSupport.firePropertyChange("libelle", oldLibelle, libelle);
    }

    public float getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(float prixVente) {
        float oldPrixVente = this.prixVente;
        this.prixVente = prixVente;
        changeSupport.firePropertyChange("prixVente", oldPrixVente, prixVente);
    }

    public float getPrixAchat() {
        return prixAchat;
    }

    public void setPrixAchat(float prixAchat) {
        float oldPrixAchat = this.prixAchat;
        this.prixAchat = prixAchat;
        changeSupport.firePropertyChange("prixAchat", oldPrixAchat, prixAchat);
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        Integer oldStock = this.stock;
        this.stock = stock;
        changeSupport.firePropertyChange("stock", oldStock, stock);
    }

    public char getEtat() {
        return etat;
    }

    public void setEtat(char etat) {
        char oldEtat = this.etat;
        this.etat = etat;
        changeSupport.firePropertyChange("etat", oldEtat, etat);
    }
    
    

    @XmlTransient
    public List<FichDetailsVente> getFichDetailsVenteList() {
        return fichDetailsVenteList;
    }

    public void setFichDetailsVenteList(List<FichDetailsVente> fichDetailsVenteList) {
        this.fichDetailsVenteList = fichDetailsVenteList;
    }

    public FichFamille getClefamille() {
        return clefamille;
    }

    public void setClefamille(FichFamille clefamille) {
        FichFamille oldClefamille = this.clefamille;
        this.clefamille = clefamille;
        changeSupport.firePropertyChange("clefamille", oldClefamille, clefamille);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (clepdt != null ? clepdt.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FichProduit)) {
            return false;
        }
        FichProduit other = (FichProduit) object;
        if ((this.clepdt == null && other.clepdt != null) || (this.clepdt != null && !this.clepdt.equals(other.clepdt))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tn.sst.compta_resto.pojos.FichProduit[ clepdt=" + clepdt + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
}
