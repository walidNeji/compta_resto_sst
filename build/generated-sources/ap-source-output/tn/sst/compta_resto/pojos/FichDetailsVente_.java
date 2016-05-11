package tn.sst.compta_resto.pojos;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import tn.sst.compta_resto.pojos.FichProduit;
import tn.sst.compta_resto.pojos.FichVente;

@Generated("EclipseLink-2.0.2.v20100323-r6872 @ Sun Apr 06 14:47:18 GMT+01:00 2014")
@StaticMetamodel(FichDetailsVente.class)
public class FichDetailsVente_ { 

	public static volatile SingularAttribute<FichDetailsVente, FichProduit> clePdt;
	public static volatile SingularAttribute<FichDetailsVente, Integer> cleDetailsVente;
	public static volatile SingularAttribute<FichDetailsVente, Float> prixAchat;
	public static volatile SingularAttribute<FichDetailsVente, Float> prixVente;
	public static volatile SingularAttribute<FichDetailsVente, String> typeVente;
	public static volatile SingularAttribute<FichDetailsVente, Float> quantite;
	public static volatile SingularAttribute<FichDetailsVente, FichVente> cleFichVente;

}