/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.sst.compta_resto.pojos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author NEJI Walid
 */
@Entity
@Table(name = "fich_vente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FichVente.findAll", query = "SELECT f FROM FichVente f"),
    @NamedQuery(name = "FichVente.findByClevente", query = "SELECT f FROM FichVente f WHERE f.clevente = :clevente"),
    @NamedQuery(name = "FichVente.findByMontantVente", query = "SELECT f FROM FichVente f WHERE f.montantVente = :montantVente"),
    @NamedQuery(name = "FichVente.findByCleUser", query = "SELECT f FROM FichVente f WHERE f.cleUser = :cleUser"),
    @NamedQuery(name = "FichVente.findByDateVente", query = "SELECT f FROM FichVente f WHERE f.dateVente = :dateVente"),
    @NamedQuery(name = "FichVente.findByHeureVente", query = "SELECT f FROM FichVente f WHERE f.heureVente = :heureVente"),
    @NamedQuery(name = "FichVente.findByTypeVente", query = "SELECT f FROM FichVente f WHERE f.typeVente = :typeVente")})
public class FichVente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "clevente")
    private Integer clevente;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "montant_vente")
    private float montantVente;
    @Column(name = "cle_user")
    private Integer cleUser;
    @Column(name = "date_vente")
    @Temporal(TemporalType.DATE)
    private Date dateVente;
    @Column(name = "heure_vente")
    @Temporal(TemporalType.TIME)
    private Date heureVente;
    @Column(name = "type_vente")
    private String typeVente;
    @OneToMany(mappedBy = "cleFichVente", fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private List<FichDetailsVente> fichDetailsVenteList;

    public FichVente() {
    }

    public FichVente(Integer clevente) {
        this.clevente = clevente;
    }

    public Integer getClevente() {
        return clevente;
    }

    public void setClevente(Integer clevente) {
        this.clevente = clevente;
    }

    public float getMontantVente() {
        return montantVente;
    }

    public void setMontantVente(float montantVente) {
        this.montantVente = montantVente;
    }

    public Integer getCleUser() {
        return cleUser;
    }

    public void setCleUser(Integer cleUser) {
        this.cleUser = cleUser;
    }

    public Date getDateVente() {
        return dateVente;
    }

    public void setDateVente(Date dateVente) {
        this.dateVente = dateVente;
    }

    public Date getHeureVente() {
        return heureVente;
    }

    public void setHeureVente(Date heureVente) {
        this.heureVente = heureVente;
    }

    public String getTypeVente() {
        return typeVente;
    }

    public void setTypeVente(String typeVente) {
        this.typeVente = typeVente;
    }

    @XmlTransient
    public List<FichDetailsVente> getFichDetailsVenteList() {
        return fichDetailsVenteList;
    }

    public void setFichDetailsVenteList(List<FichDetailsVente> fichDetailsVenteList) {
        this.fichDetailsVenteList = fichDetailsVenteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (clevente != null ? clevente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FichVente)) {
            return false;
        }
        FichVente other = (FichVente) object;
        if ((this.clevente == null && other.clevente != null) || (this.clevente != null && !this.clevente.equals(other.clevente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tn.sst.compta_resto.pojos.FichVente[ clevente=" + clevente + " ]";
    }
    
}
