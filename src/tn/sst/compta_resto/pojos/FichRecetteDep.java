/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.sst.compta_resto.pojos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author NEJI Walid
 */
@Entity
@Table(name = "fich_recette_dep")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FichRecetteDep.findAll", query = "SELECT f FROM FichRecetteDep f"),
    @NamedQuery(name = "FichRecetteDep.findByIdRecetteRep", query = "SELECT f FROM FichRecetteDep f WHERE f.idRecetteRep = :idRecetteRep"),
    @NamedQuery(name = "FichRecetteDep.findByTypeRep", query = "SELECT f FROM FichRecetteDep f WHERE f.typeDep = :typeDep"),
    @NamedQuery(name = "FichRecetteDep.findByMontant", query = "SELECT f FROM FichRecetteDep f WHERE f.montant = :montant"),
    @NamedQuery(name = "FichRecetteDep.findByDate", query = "SELECT f FROM FichRecetteDep f WHERE f.date = :date"),
    @NamedQuery(name = "FichRecetteDep.findByHeure", query = "SELECT f FROM FichRecetteDep f WHERE f.heure = :heure")})
public class FichRecetteDep implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_recette_rep")
    private Integer idRecetteRep;
    @Column(name = "type_rep")
    private String typeDep;
    @Column(name = "quantite")
    private int quantite;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "montant")
    private float montant;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Column(name = "heure")
    @Temporal(TemporalType.TIME)
    private Date heure;
    @JoinColumn(name = "id_dep", referencedColumnName = "id_dep")
    @ManyToOne(fetch = FetchType.LAZY)
    private FichDepenses idDep;

    public FichRecetteDep() {
    }

    public FichRecetteDep(Integer idRecetteRep) {
        this.idRecetteRep = idRecetteRep;
    }

    public Integer getIdRecetteRep() {
        return idRecetteRep;
    }

    public void setIdRecetteRep(Integer idRecetteRep) {
        this.idRecetteRep = idRecetteRep;
    }

    public String getTypeDep() {
        return typeDep;
    }

    public void setTypeDep(String typeDep) {
        this.typeDep = typeDep;
    }

    public float getMontant() {
        return montant;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getHeure() {
        return heure;
    }

    public void setHeure(Date heure) {
        this.heure = heure;
    }

    public FichDepenses getIdDep() {
        return idDep;
    }

    public void setIdDep(FichDepenses idDep) {
        this.idDep = idDep;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRecetteRep != null ? idRecetteRep.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FichRecetteDep)) {
            return false;
        }
        FichRecetteDep other = (FichRecetteDep) object;
        if ((this.idRecetteRep == null && other.idRecetteRep != null) || (this.idRecetteRep != null && !this.idRecetteRep.equals(other.idRecetteRep))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tn.sst.compta_resto.pojos.FichRecetteDep[ idRecetteRep=" + idRecetteRep + " ]";
    }
}
