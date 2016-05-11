/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.sst.compta_resto.objects;

/**
 *
 * @author NEJI Walid
 */
public class GrandLivre {
    
    private String date , 
            soldeVentes,
            soldeDepense,
            BenficeCumules;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSoldeVentes() {
        return soldeVentes;
    }

    public void setSoldeVentes(String soldeVentes) {
        this.soldeVentes = soldeVentes;
    }

    public String getSoldeDepense() {
        return soldeDepense;
    }

    public void setSoldeDepense(String soldeDepense) {
        this.soldeDepense = soldeDepense;
    }

    public String getBenficeCumules() {
        return BenficeCumules;
    }

    public void setBenficeCumules(String BenficeCumules) {
        this.BenficeCumules = BenficeCumules;
    }
    
    
    
    
}
