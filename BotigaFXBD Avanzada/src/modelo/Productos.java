package modelo;
import modelo.Producto;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;




public class Productos {
	
	private Connection conn;
	
	public Productos(Connection conexionBD) {
		this.conn=conexionBD;
	}


	public void addProducto(Producto productemp) throws SQLException {

		if(productemp instanceof Joc ) {					
			Joc joc=(Joc)productemp;
			PreparedStatement sta=conn.prepareStatement("insert into jocs (idproducte,nom,preu,idproveidor,stock,fecha_inicio,fecha_final,tipo,edat) values(?,?,?,?,?,?,?,?,?)");
			sta.setString(1,joc.getId());
			sta.setString(2, joc.getNom());
			sta.setDouble(3, joc.getPreu());
			sta.setInt(4, joc.getId_proveedor());
			sta.setInt(5, joc.getStock());
			sta.setDate(6, java.sql.Date.valueOf(joc.getFecha_inicio()));
			sta.setDate(7, java.sql.Date.valueOf(joc.getFecha_final()));
			sta.setString(8, "Joc");
			sta.setInt(9, joc.getEdad_minima());
			sta.executeUpdate();
			sta.close();
		}else if(productemp instanceof Pack) {
			
			Pack pack=(Pack)productemp;
			PreparedStatement sta=conn.prepareStatement("insert into packs1 (idproducte,nom,preu,porc_dto,idproveidor,jocs,stock,fecha_inicio,fecha_final,tipo) values(?,?,?,?,?,?,?,?,?,?)");
			sta.setString(1,pack.getId());
			sta.setString(2, pack.getNom());
			sta.setDouble(3, pack.getPreu());
			sta.setDouble(4, pack.getDescuento());
			sta.setInt(5,pack.getId_proveedor());
			
			String[] lista_juegos_array=pack.getListaJuegos().toArray(new String[pack.getListaJuegos().size()]);
			Array lista = conn.createArrayOf("INTEGER", lista_juegos_array);
			sta.setArray(6,lista );
			sta.setInt(7, pack.getStock());
			sta.setDate(8, java.sql.Date.valueOf(pack.getFecha_inicio()));
			sta.setDate(9, java.sql.Date.valueOf(pack.getFecha_final()));
			sta.setString(10,"Pack");
			
			sta.executeUpdate();
			sta.close();
		}
	}
	

	@SuppressWarnings("resource")
	public Producto searchProducto(String id) {
		try {
			PreparedStatement stament=conn.prepareStatement("select tipo from productes where idproducte=?");
			stament.setString(1, id);			
			ResultSet rs=stament.executeQuery();			
			if(!rs.next()) return null;
			
			else {
				String tipo=rs.getString("tipo");
				
				switch (tipo) {
					case "Joc":
						stament=conn.prepareStatement("select * from jocs where idproducte=?");
						stament.setString(1, id);
						rs=stament.executeQuery();
						
						if(rs.next()) {
						
							Joc juego=new Joc(rs.getString("idproducte"), rs.getString("nom"), rs.getDouble("preu"), rs.getInt("stock"), 
											  rs.getDate("fecha_inicio").toLocalDate(), rs.getDate("fecha_final").toLocalDate(), 
											  rs.getInt("edat"), rs.getInt("idproveidor"));							
							return juego;
						}
						rs.close();
						break;
					
					case "Pack":
						stament=conn.prepareStatement("select * from packs where idproducte=?");
						stament.setString(1, id);
						rs=stament.executeQuery();
						
						if(rs.next()) {						
							Array array=rs.getArray("jocs");
							String[] array_str = (String[])array.getArray();
							String string_lista =Arrays.toString(array_str);
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
	
	
	
	public void updateProducto(Producto productemp) throws SQLException {		
		String sql = null;
		if(productemp instanceof Joc) {
			Joc joc=(Joc)productemp;
			PreparedStatement stament = conn.prepareStatement("update jocs set idproducte=?, nom=?, preu=?, idproveidor=?, stock=?, fecha_inicio=?, fecha_final=?,tipo=?,edat=? where idproducte =?");
			stament.setString(1,joc.getId());
			stament.setString(2, joc.getNom());
			stament.setDouble(3, joc.getPreu());
			stament.setInt(4, joc.getId_proveedor());
			stament.setInt(5, joc.getStock());
			stament.setDate(6, java.sql.Date.valueOf(joc.getFecha_inicio()));
			stament.setDate(7, java.sql.Date.valueOf(joc.getFecha_final()));
			stament.setString(8, "Joc");
			stament.setInt(9, joc.getEdad_minima());
			stament.setString(10, joc.getId());
			stament.executeUpdate();
			stament.close();
		} else if(productemp instanceof Pack) {
			
		}
		
		
		
		
	}
	
	public boolean deleteProducto(String id) throws SQLException{
		PreparedStatement stament = conn.prepareStatement("delete from productes where idproducte=?");
		stament.setString(1, id);
		stament.executeUpdate();
		stament.close();
		return true;		
	}
	
	
	
	
	public void closeDB() throws SQLException {
		if(conn!=null) conn.close();	
	}
}
