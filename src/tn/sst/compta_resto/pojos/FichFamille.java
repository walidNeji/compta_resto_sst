/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.sst.compta_resto.pojos;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author NEJI Walid
 */
@Entity
@Table(name = "fich_famille")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FichFamille.findAll", query = "SELECT f FROM FichFamille f"),
    @NamedQuery(name = "FichFamille.findByClefamille", query = "SELECT f FROM FichFamille f WHERE f.clefamille = :clefamille"),
    @NamedQuery(name = "FichFamille.findByLibelle", query = "SELECT f FROM FichFamille f WHERE f.libelle = :libelle")})
public class FichFamille implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "clefamille")
    private Integer clefamille;
    @Column(name = "libelle")
    private String libelle;
    @OneToMany(mappedBy = "clefamille", fetch = FetchType.LAZY)
    private List<FichProduit> fichProduitList;

    public FichFamille() {
    }

    public FichFamille(Integer clefamille) {
        this.clefamille = clefamille;
    }

    public Integer getClefamille() {
        return clefamille;
    }

    public void setClefamille(Integer clefamille) {
        this.clefamille = clefamille;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @XmlTransient
    public List<FichProduit> getFichProduitList() {
        return fichProduitList;
    }

    public void setFichProduitList(List<FichProduit> fichProduitList) {
        this.fichProduitList = fichProduitList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (clefamille != null ? clefamille.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FichFamille)) {
            return false;
        }
        FichFamille other = (FichFamille) object;
        if ((this.clefamille == null && other.clefamille != null) || (this.clefamille != null && !this.clefamille.equals(other.clefamille))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tn.sst.compta_resto.pojos.FichFamille[ clefamille=" + clefamille + " ]";
    }
    
}
