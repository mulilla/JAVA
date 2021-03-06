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
			Statement stament=conn.createStatement();
			Joc joc=(Joc)productemp;
			
			LocalDate localDateInicio = joc.getFecha_inicio();
			LocalDate localDateFin = joc.getFecha_final();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String formattedStringInicio = localDateInicio.format(formatter);
			String formattedStringFin = localDateFin.format(formatter);
			String sql = "insert into jocs (idproducte,nom,preu,edat,idproveidor,stock,fecha_inicio,fecha_final,tipo) values('"+joc.getId()+"','"+joc.getNom()+"',"+joc.getPreu()+","+joc.getEdad_minima()+","+joc.getId_proveedor()+","+joc.getStock()+",'"+formattedStringInicio+"','"+formattedStringFin+"', 'Joc');";
			stament.executeUpdate(sql);
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
			Statement stament=conn.createStatement();
			String sql ="select tipo from productes where idproducte='"+id+"';";
			ResultSet rs=stament.executeQuery(sql);
			
			if(!rs.next()) return null;
			
			else {
				String tipo=rs.getString("tipo");
				
				switch (tipo) {
					case "Joc":
						sql = "select * from jocs where idproducte='"+id+"';";
						rs=stament.executeQuery(sql);
						
						if(rs.next()) {
						
							Joc juego=new Joc(rs.getString("idproducte"), rs.getString("nom"), rs.getDouble("preu"), rs.getInt("stock"), 
											  rs.getDate("fecha_inicio").toLocalDate(), rs.getDate("fecha_final").toLocalDate(), 
											  rs.getInt("edat"), rs.getInt("idproveidor"));							
							return juego;
						}
						rs.close();
						break;
					
					case "Pack":
						sql = "select * from packs1 where idproducte='"+id+"';";
						rs=stament.executeQuery(sql);
						
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
		Statement stament = conn.createStatement();
		String sql = null;
		if(productemp instanceof Joc) {
			Joc joc=(Joc)productemp;
			LocalDate localDateInicio = joc.getFecha_inicio();
			LocalDate localDateFin = joc.getFecha_final();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String formattedStringInicio = localDateInicio.format(formatter);
			String formattedStringFin = localDateFin.format(formatter);
			sql = ("update jocs set idproducte='"+joc.getId()+"', nom='"+joc.getNom()+"', preu="+joc.getPreu()+", edat="+joc.getEdad_minima()+", idproveidor="+joc.getId_proveedor()+", stock="+joc.getStock()+", fecha_inicio='"+formattedStringInicio+"', fecha_final='"+formattedStringFin+"',tipo='Joc' where idproducte = '"+joc.getId_proveedor()+"';");
			stament.executeUpdate(sql);
		} else if(productemp instanceof Pack) {
			
		}
		
		
		
		
	}
	
	public boolean deleteProducto(String id) throws SQLException{
		Statement stament=conn.createStatement();
		String sql ="delete from productes where idproducte='"+id+"';";
		stament.executeUpdate(sql);
		return true;
		
	}
	
	
	
	
	public void closeDB() throws SQLException {
		if(conn!=null) conn.close();	
	}
}
