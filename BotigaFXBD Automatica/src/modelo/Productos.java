package modelo;
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
	}
	

	@SuppressWarnings("resource")
	public Producto searchProducto(String id) {
		
	}
	
	
	
	public void updateProducto(Producto productemp) throws SQLException {		
		
		
	}
	
	public boolean deleteProducto(String id) throws SQLException{
		
	}
	
	
	
	
	public void closeDB() throws SQLException {
		if(conn!=null) conn.close();	
	}
}
