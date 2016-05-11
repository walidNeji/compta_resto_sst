package tn.sst.compta_resto.pojos;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import tn.sst.compta_resto.pojos.FichDepenses;

@Generated("EclipseLink-2.0.2.v20100323-r6872 @ Sun Apr 06 14:47:18 GMT+01:00 2014")
@StaticMetamodel(FichRecetteDep.class)
public class FichRecetteDep_ { 

	public static volatile SingularAttribute<FichRecetteDep, Integer> idRecetteRep;
	public static volatile SingularAttribute<FichRecetteDep, FichDepenses> idDep;
	public static volatile SingularAttribute<FichRecetteDep, Float> montant;
	public static volatile SingularAttribute<FichRecetteDep, String> typeDep;
	public static volatile SingularAttribute<FichRecetteDep, Date> heure;
	public static volatile SingularAttribute<FichRecetteDep, Integer> quantite;
	public static volatile SingularAttribute<FichRecetteDep, Date> date;

}