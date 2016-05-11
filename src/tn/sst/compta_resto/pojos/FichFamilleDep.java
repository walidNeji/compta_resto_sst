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
@Table(name = "fich_famille_dep")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FichFamilleDep.findAll", query = "SELECT f FROM FichFamilleDep f"),
    @NamedQuery(name = "FichFamilleDep.findByIdFamilleDep", query = "SELECT f FROM FichFamilleDep f WHERE f.idFamilleDep = :idFamilleDep"),
    @NamedQuery(name = "FichFamilleDep.findByLibelle", query = "SELECT f FROM FichFamilleDep f WHERE f.libelle = :libelle")})
public class FichFamilleDep implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_famille_dep")
    private Integer idFamilleDep;
    @Column(name = "libelle")
    private String libelle;
    @OneToMany(mappedBy = "idFamilleDep", fetch = FetchType.LAZY)
    private List<FichDepenses> fichDepensesList;

    public FichFamilleDep() {
    }

    public FichFamilleDep(Integer idFamilleDep) {
        this.idFamilleDep = idFamilleDep;
    }

    public Integer getIdFamilleDep() {
        return idFamilleDep;
    }

    public void setIdFamilleDep(Integer idFamilleDep) {
        this.idFamilleDep = idFamilleDep;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @XmlTransient
    public List<FichDepenses> getFichDepensesList() {
        return fichDepensesList;
    }

    public void setFichDepensesList(List<FichDepenses> fichDepensesList) {
        this.fichDepensesList = fichDepensesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFamilleDep != null ? idFamilleDep.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FichFamilleDep)) {
            return false;
        }
        FichFamilleDep other = (FichFamilleDep) object;
        if ((this.idFamilleDep == null && other.idFamilleDep != null) || (this.idFamilleDep != null && !this.idFamilleDep.equals(other.idFamilleDep))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tn.sst.compta_resto.pojos.FichFamilleDep[ idFamilleDep=" + idFamilleDep + " ]";
    }
    
}
