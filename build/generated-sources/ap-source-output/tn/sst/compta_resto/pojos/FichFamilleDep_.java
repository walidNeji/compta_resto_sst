package tn.sst.compta_resto.pojos;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import tn.sst.compta_resto.pojos.FichDepenses;

@Generated("EclipseLink-2.0.2.v20100323-r6872 @ Sun Apr 06 14:47:18 GMT+01:00 2014")
@StaticMetamodel(FichFamilleDep.class)
public class FichFamilleDep_ { 

	public static volatile SingularAttribute<FichFamilleDep, String> libelle;
	public static volatile SingularAttribute<FichFamilleDep, Integer> idFamilleDep;
	public static volatile ListAttribute<FichFamilleDep, FichDepenses> fichDepensesList;

}