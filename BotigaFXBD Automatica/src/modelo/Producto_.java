package modelo;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2018-04-16T16:33:40.938+0200")
@StaticMetamodel(Producto.class)
public class Producto_ {
	public static volatile SingularAttribute<Producto, String> id;
	public static volatile SingularAttribute<Producto, String> nom;
	public static volatile SingularAttribute<Producto, Double> preu;
	public static volatile SingularAttribute<Producto, Integer> stock;
	public static volatile SingularAttribute<Producto, LocalDate> fecha_inicio;
	public static volatile SingularAttribute<Producto, LocalDate> fecha_final;
	public static volatile SingularAttribute<Producto, Integer> id_proveedor;
}
