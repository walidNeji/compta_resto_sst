package tn.sst.compta_resto.pojos;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import tn.sst.compta_resto.pojos.FichProduit;

@Generated("EclipseLink-2.0.2.v20100323-r6872 @ Sun Apr 06 14:47:18 GMT+01:00 2014")
@StaticMetamodel(FichFamille.class)
public class FichFamille_ { 

	public static volatile SingularAttribute<FichFamille, Integer> clefamille;
	public static volatile SingularAttribute<FichFamille, String> libelle;
	public static volatile ListAttribute<FichFamille, FichProduit> fichProduitList;

}