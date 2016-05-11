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
@Table(name = "fich_user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FichUser.findAll", query = "SELECT f FROM FichUser f"),
    @NamedQuery(name = "FichUser.findByCleuser", query = "SELECT f FROM FichUser f WHERE f.cleuser = :cleuser"),
    @NamedQuery(name = "FichUser.findByNomuser", query = "SELECT f FROM FichUser f WHERE f.nomuser = :nomuser"),
    @NamedQuery(name = "FichUser.findByLogin", query = "SELECT f FROM FichUser f WHERE f.login = :login"),
    @NamedQuery(name = "FichUser.findByPassword", query = "SELECT f FROM FichUser f WHERE f.password = :password")})
public class FichUser implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cleuser")
    private Integer cleuser;
    @Column(name = "nomuser")
    private String nomuser;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;

    public FichUser() {
    }

    public FichUser(Integer cleuser) {
        this.cleuser = cleuser;
    }

    public Integer getCleuser() {
        return cleuser;
    }

    public void setCleuser(Integer cleuser) {
        this.cleuser = cleuser;
    }

    public String getNomuser() {
        return nomuser;
    }

    public void setNomuser(String nomuser) {
        this.nomuser = nomuser;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cleuser != null ? cleuser.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FichUser)) {
            return false;
        }
        FichUser other = (FichUser) object;
        if ((this.cleuser == null && other.cleuser != null) || (this.cleuser != null && !this.cleuser.equals(other.cleuser))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tn.sst.compta_resto.pojos.FichUser[ cleuser=" + cleuser + " ]";
    }
    
}
