package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2018-04-16T16:33:40.880+0200")
@StaticMetamodel(Pack.class)
public class Pack_ extends Producto_ {
	public static volatile SetAttribute<Pack, String> ListaJuegos;
	public static volatile SingularAttribute<Pack, Double> descuento;
}
