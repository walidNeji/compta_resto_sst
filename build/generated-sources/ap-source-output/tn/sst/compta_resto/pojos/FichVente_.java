package tn.sst.compta_resto.pojos;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import tn.sst.compta_resto.pojos.FichDetailsVente;

@Generated("EclipseLink-2.0.2.v20100323-r6872 @ Sun Apr 06 14:47:18 GMT+01:00 2014")
@StaticMetamodel(FichVente.class)
public class FichVente_ { 

	public static volatile SingularAttribute<FichVente, Date> heureVente;
	public static volatile SingularAttribute<FichVente, Integer> cleUser;
	public static volatile ListAttribute<FichVente, FichDetailsVente> fichDetailsVenteList;
	public static volatile SingularAttribute<FichVente, Integer> clevente;
	public static volatile SingularAttribute<FichVente, Float> montantVente;
	public static volatile SingularAttribute<FichVente, String> typeVente;
	public static volatile SingularAttribute<FichVente, Date> dateVente;

}