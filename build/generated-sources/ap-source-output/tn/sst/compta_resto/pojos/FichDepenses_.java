package tn.sst.compta_resto.pojos;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import tn.sst.compta_resto.pojos.FichFamilleDep;
import tn.sst.compta_resto.pojos.FichRecetteDep;

@Generated("EclipseLink-2.0.2.v20100323-r6872 @ Sun Apr 06 14:47:18 GMT+01:00 2014")
@StaticMetamodel(FichDepenses.class)
public class FichDepenses_ { 

	public static volatile SingularAttribute<FichDepenses, Character> etat;
	public static volatile SingularAttribute<FichDepenses, Integer> idDep;
	public static volatile SingularAttribute<FichDepenses, String> libelle;
	public static volatile ListAttribute<FichDepenses, FichRecetteDep> fichRecetteDepList;
	public static volatile SingularAttribute<FichDepenses, FichFamilleDep> idFamilleDep;

}