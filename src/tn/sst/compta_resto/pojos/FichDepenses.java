/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.sst.compta_resto.pojos;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
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
@Table(name = "fich_depenses")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FichDepenses.findAll", query = "SELECT f FROM FichDepenses f"),
    @NamedQuery(name = "FichDepenses.findByIdDep", query = "SELECT f FROM FichDepenses f WHERE f.idDep = :idDep"),
    @NamedQuery(name = "FichDepenses.findByLibelle", query = "SELECT f FROM FichDepenses f WHERE f.libelle = :libelle AND f.etat = 'A' ")})
public class FichDepenses implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_dep")
    private Integer idDep;
    @Column(name = "libelle")
    private String libelle;
    @Column(name = "etat")
    private char etat;
    @OneToMany(mappedBy = "idDep", fetch = FetchType.LAZY)
    private List<FichRecetteDep> fichRecetteDepList;
    @JoinColumn(name = "id_famille_dep", referencedColumnName = "id_famille_dep")
    @ManyToOne(fetch = FetchType.LAZY)
    private FichFamilleDep idFamilleDep;

    public FichDepenses() {
    }

    public FichDepenses(Integer idDep) {
        this.idDep = idDep;
    }

    public Integer getIdDep() {
        return idDep;
    }

    public void setIdDep(Integer idDep) {
        Integer oldIdDep = this.idDep;
        this.idDep = idDep;
        changeSupport.firePropertyChange("idDep", oldIdDep, idDep);
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        String oldLibelle = this.libelle;
        this.libelle = libelle;
        changeSupport.firePropertyChange("libelle", oldLibelle, libelle);
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
    public List<FichRecetteDep> getFichRecetteDepList() {
        return fichRecetteDepList;
    }

    public void setFichRecetteDepList(List<FichRecetteDep> fichRecetteDepList) {
        this.fichRecetteDepList = fichRecetteDepList;
    }

    public FichFamilleDep getIdFamilleDep() {
        return idFamilleDep;
    }

    public void setIdFamilleDep(FichFamilleDep idFamilleDep) {
        FichFamilleDep oldIdFamilleDep = this.idFamilleDep;
        this.idFamilleDep = idFamilleDep;
        changeSupport.firePropertyChange("idFamilleDep", oldIdFamilleDep, idFamilleDep);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDep != null ? idDep.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FichDepenses)) {
            return false;
        }
        FichDepenses other = (FichDepenses) object;
        if ((this.idDep == null && other.idDep != null) || (this.idDep != null && !this.idDep.equals(other.idDep))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tn.sst.compta_resto.pojos.FichDepenses[ idDep=" + idDep + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
