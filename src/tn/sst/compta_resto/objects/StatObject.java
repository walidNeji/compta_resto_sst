/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.sst.compta_resto.objects;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.SqlResultSetMapping;

/**
 *
 * @author Neji Med Walid <medwalid.neji@bitaka.com.tn>
 */

public class StatObject {

    private String libelle;
    private int quantite;
    private float montanttotal;

    public StatObject(String libelle, int qunatite, float montanttotal) {
        this.libelle = libelle;
        this.quantite = qunatite;
        this.montanttotal = montanttotal;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

   

    public float getMontanttotal() {
        return montanttotal;
    }

    public void setMontanttotal(float montanttotal) {
        this.montanttotal = montanttotal;
    }

}
