package modelo;
import modelo.Producto;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;




public class Productos {
	
	private Connection conexionBD;
	
	public Productos(Connection conexionBD) {
		this.conexionBD=conexionBD;
	}


	
	//Metodo que recibe un producto y lo introduce en la estructura de datos
	public void addProducto(Producto productemp) throws SQLException {
		
		
		
		//Inserto el JOC
		
		if(productemp instanceof Joc ) {
			
			Joc joc=(Joc)productemp;
			PreparedStatement sta=conexionBD.prepareStatement("INSERT INTO jocs (idproducte,nom,preu,edat,idproveidor,stock,fecha_inicio,fecha_final,tipo) VALUES(?,?,?,?,?,?,?,?,?)");
			sta.setString(1,joc.getId());
			sta.setString(2, joc.getNom());
			sta.setDouble(3, joc.getPreu());
			sta.setInt(4, joc.getEdad_minima());
			sta.setInt(5, joc.getId_proveedor());
			sta.setInt(6, joc.getStock());
			sta.setDate(7, java.sql.Date.valueOf(joc.getFecha_inicio()));
			sta.setDate(8, java.sql.Date.valueOf(joc.getFecha_final()));
			sta.setString(9,"Joc");
			
			sta.executeUpdate();
			sta.close();
			//Inserto el Pack	
		}else if(productemp instanceof Pack) {
			
			Pack pack=(Pack)productemp;
			PreparedStatement sta=conexionBD.prepareStatement("INSERT INTO packs1 (idproducte,nom,preu,porc_dto,idproveidor,jocs,stock,fecha_inicio,fecha_final,tipo) VALUES(?,?,?,?,?,?,?,?,?,?)");
			sta.setString(1,pack.getId());
			sta.setString(2, pack.getNom());
			sta.setDouble(3, pack.getPreu());
			sta.setDouble(4, pack.getDescuento());
			sta.setInt(5,pack.getId_proveedor());
			
			//Convierto el treset a array String y luego hago un array generica tipo Integer para
			//meterla en el stament.
			//https://stackoverflow.com/questions/17842211/how-to-use-an-arraylist-as-a-prepared-statement-parameter
			
			String[] lista_juegos_array=pack.getListaJuegos().toArray(new String[pack.getListaJuegos().size()]);
			Array lista = conexionBD.createArrayOf("INTEGER", lista_juegos_array);
			sta.setArray(6,lista );
			sta.setInt(7, pack.getStock());
			sta.setDate(8, java.sql.Date.valueOf(pack.getFecha_inicio()));
			sta.setDate(9, java.sql.Date.valueOf(pack.getFecha_final()));
			sta.setString(10,"Pack");
			
			sta.executeUpdate();
			sta.close();
		}
		
		
		
		
		
	}
	
	
	
	//Metodo que recibe una ID y comprueba que exista en la estructura de datos.
	//Si es asi devuelve el Producto
	@SuppressWarnings("resource")
	public Producto searchProducto(String id) {
		try {
			PreparedStatement sta=conexionBD.prepareStatement("SELECT tipo FROM productes WHERE idproducte=?");
			sta.setString(1, id);
			ResultSet rs=sta.executeQuery();
			
			if(!rs.next()) return null;
			
			else {
				String tipo=rs.getString("tipo");
				
				switch (tipo) {
					case "Joc":
						
						sta=conexionBD.prepareStatement("SELECT * FROM jocs WHERE idproducte=?");
						sta.setString(1, id);
						rs=sta.executeQuery();
						
						if(rs.next()) {
						
							Joc juego=new Joc(rs.getString("idproducte"), rs.getString("nom"), rs.getDouble("preu"), rs.getInt("stock"), 
											  rs.getDate("fecha_inicio").toLocalDate(), rs.getDate("fecha_final").toLocalDate(), 
											  rs.getInt("edat"), rs.getInt("idproveidor"));
							
							return juego;
						}
						rs.close();
						sta.close();
						
						
						break;
					
					case "Pack":
						
						sta=conexionBD.prepareStatement("SELECT * FROM packs1 WHERE idproducte=?");
						sta.setString(1, id);
						rs=sta.executeQuery();
						
						if(rs.next()) {
							
							//Convierto la array cruda de lista de juegos en un Treset<String> para poder construir el pack
							//Posdata me cago en todos sus muertos
							Array array=rs.getArray("jocs");
							Integer[] array_int = (Integer[])array.getArray();
							String string_lista =Arrays.toString(array_int);
							string_lista=string_lista.substring(1, string_lista.length()-1);
							String[] array_string = string_lista.split(","+" ");
							List<String> list = Arrays.asList(array_string);
							TreeSet<String> lista_juegos = new TreeSet<String>(list);
							
							
							
						
							Pack pack=new Pack(rs.getString("idproducte"), rs.getString("nom"), rs.getDouble("preu"), rs.getInt("stock"), 
											  rs.getDate("fecha_inicio").toLocalDate(), rs.getDate("fecha_final").toLocalDate(), 
											  lista_juegos,rs.getDouble("porc_dto"));
							
							
							return pack;
						}
						
						rs.close();
						sta.close();
						
						
						break;
	
					default:
						break;
				}
				
				
				
			}
			
			return null;
			
				
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
		
		
		
		
		
		
		
		
	}
	
	
	//Recibe un Producto previamente modificado por la clase BotigaProductes y sobreescribe en la
	//Estructura de datos el Producto
	public void updateProducto(Producto productemp) {
		
		
		
		
	}
	
	//Recibe un ID y los busca en la estructura de datos, si existe borra el producto
	public boolean deleteProducto(String id){
		
		//implementar
		return true;
		
		
	}
	
	
	
	
	public void closeDB() throws SQLException {
		if(conexionBD!=null) conexionBD.close();
		
	}
			

}
