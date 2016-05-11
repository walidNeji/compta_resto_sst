/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.sst.compta_resto.pojos;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author NEJI Walid
 */
@Entity
@Table(name = "fich_util")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FichUtil.findAll", query = "SELECT f FROM FichUtil f"),
    @NamedQuery(name = "FichUtil.findByCleut", query = "SELECT f FROM FichUtil f WHERE f.cleut = :cleut"),
    @NamedQuery(name = "FichUtil.findByCodut", query = "SELECT f FROM FichUtil f WHERE f.codut = :codut"),
    @NamedQuery(name = "FichUtil.findByPassut", query = "SELECT f FROM FichUtil f WHERE f.passut = :passut"),
    @NamedQuery(name = "FichUtil.findByProfil", query = "SELECT f FROM FichUtil f WHERE f.profil = :profil")})
public class FichUtil implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cleut")
    private Integer cleut;
    @Column(name = "codut")
    private String codut;
    @Column(name = "passut")
    private String passut;
    @Column(name = "profil")
    private String profil;

    public FichUtil() {
    }

    public FichUtil(Integer cleut) {
        this.cleut = cleut;
    }

    public Integer getCleut() {
        return cleut;
    }

    public void setCleut(Integer cleut) {
        this.cleut = cleut;
    }

    public String getCodut() {
        return codut;
    }

    public void setCodut(String codut) {
        this.codut = codut;
    }

    public String getPassut() {
        return passut;
    }

    public void setPassut(String passut) {
        this.passut = passut;
    }

    public String getProfil() {
        return profil;
    }

    public void setProfil(String profil) {
        this.profil = profil;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cleut != null ? cleut.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FichUtil)) {
            return false;
        }
        FichUtil other = (FichUtil) object;
        if ((this.cleut == null && other.cleut != null) || (this.cleut != null && !this.cleut.equals(other.cleut))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tn.sst.compta_resto.pojos.FichUtil[ cleut=" + cleut + " ]";
    }
    
}
