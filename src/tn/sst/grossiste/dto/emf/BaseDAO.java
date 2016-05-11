/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.sst.grossiste.dto.emf;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author NEJI Walid
 */
public abstract class BaseDAO {

    public static EntityManagerFactory emf;

    public static void createEMF() {
        emf= Persistence.createEntityManagerFactory("ComptaResto_SSTPU");
    }
}
