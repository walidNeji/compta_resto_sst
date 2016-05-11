package tn.sst.compta_resto.pojos;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import tn.sst.compta_resto.pojos.FichDetailsVente;
import tn.sst.compta_resto.pojos.FichFamille;

@Generated("EclipseLink-2.0.2.v20100323-r6872 @ Sun Apr 06 14:47:18 GMT+01:00 2014")
@StaticMetamodel(FichProduit.class)
public class FichProduit_ { 

	public static volatile SingularAttribute<FichProduit, Character> etat;
	public static volatile ListAttribute<FichProduit, FichDetailsVente> fichDetailsVenteList;
	public static volatile SingularAttribute<FichProduit, FichFamille> clefamille;
	public static volatile SingularAttribute<FichProduit, Integer> stock;
	public static volatile SingularAttribute<FichProduit, Float> prixAchat;
	public static volatile SingularAttribute<FichProduit, String> libelle;
	public static volatile SingularAttribute<FichProduit, Float> prixVente;
	public static volatile SingularAttribute<FichProduit, Integer> clepdt;

}