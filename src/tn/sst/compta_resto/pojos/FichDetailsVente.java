/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.sst.compta_resto.pojos;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author NEJI Walid
 */
@Entity
@Table(name = "fich_details_vente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FichDetailsVente.findAll", query = "SELECT f FROM FichDetailsVente f"),
    @NamedQuery(name = "FichDetailsVente.findByCleDetailsVente", query = "SELECT f FROM FichDetailsVente f WHERE f.cleDetailsVente = :cleDetailsVente"),
    @NamedQuery(name = "FichDetailsVente.findByQuantite", query = "SELECT f FROM FichDetailsVente f WHERE f.quantite = :quantite"),
    @NamedQuery(name = "FichDetailsVente.findByPrixVente", query = "SELECT f FROM FichDetailsVente f WHERE f.prixVente = :prixVente")})
public class FichDetailsVente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cle_details_vente")
    private Integer cleDetailsVente;
    @Column(name = "quantite")
    private float quantite;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "prix_vente")
    private float prixVente;
    @Column(name = "prix_achat")
    private float prixAchat;
    @Column(name="type_vente")
    private String typeVente;
    @JoinColumn(name = "cle_fich_vente", referencedColumnName = "clevente")
    @ManyToOne(fetch = FetchType.LAZY)
    private FichVente cleFichVente;
    @JoinColumn(name = "cle_pdt", referencedColumnName = "clepdt")
    @ManyToOne(fetch = FetchType.LAZY)
    private FichProduit clePdt;

    public FichDetailsVente() {
    }

    public FichDetailsVente(Integer cleDetailsVente) {
        this.cleDetailsVente = cleDetailsVente;
    }

    public Integer getCleDetailsVente() {
        return cleDetailsVente;
    }

    public void setCleDetailsVente(Integer cleDetailsVente) {
        this.cleDetailsVente = cleDetailsVente;
    }

    public float getQuantite() {
        return quantite;
    }

    public void setQuantite(float quantite) {
        this.quantite = quantite;
    }

    public float getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(float prixVente) {
        this.prixVente = prixVente;
    }

    public float getPrixAchat() {
        return prixAchat;
    }

    public void setPrixAchat(float prixAchat) {
        this.prixAchat = prixAchat;
    }

    
    public FichVente getCleFichVente() {
        return cleFichVente;
    }

    public void setCleFichVente(FichVente cleFichVente) {
        this.cleFichVente = cleFichVente;
    }

    public FichProduit getClePdt() {
        return clePdt;
    }

    public void setClePdt(FichProduit clePdt) {
        this.clePdt = clePdt;
    }

    public String getTypeVente() {
        return typeVente;
    }

    public void setTypeVente(String typeVente) {
        this.typeVente = typeVente;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cleDetailsVente != null ? cleDetailsVente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FichDetailsVente)) {
            return false;
        }
        FichDetailsVente other = (FichDetailsVente) object;
        if ((this.cleDetailsVente == null && other.cleDetailsVente != null) || (this.cleDetailsVente != null && !this.cleDetailsVente.equals(other.cleDetailsVente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tn.sst.compta_resto.pojos.FichDetailsVente[ cleDetailsVente=" + cleDetailsVente + " ]";
    }
}
