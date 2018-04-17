package autobotiga;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;


public class Vehicles implements Persistent {


	private Connection conexionBD;

	public Vehicles(Connection conexionBD) {
		this.conexionBD = conexionBD;	
	}

	@Override
	public boolean guardar(Vehicle vehicle) {
		try {
			String sql = "";
			PreparedStatement stmt = null;

			if (this.buscar(vehicle.getMatricula()) == null){
				if (vehicle instanceof Cotxe){
					sql = "INSERT INTO cotxes VALUES(?,?,?,?,?,?,?,?,?)";
					stmt = conexionBD.prepareStatement(sql);
					int i = 1;
					stmt.setString(i++, vehicle.getMatricula());
					stmt.setString(i++, vehicle.getMarca());
					stmt.setString(i++, vehicle.getModel());
					stmt.setString(i++, vehicle.getVersio());
					stmt.setDouble(i++, vehicle.getEmisionsCO2());
					stmt.setDouble(i++, vehicle.getPreu());
					stmt.setDouble(i++, 0);
					stmt.setInt(i++, Integer.valueOf(((Cotxe)vehicle).getPortes()));
					stmt.setInt(i++, Integer.valueOf(((Cotxe)vehicle).getPlaces()));
				}else{
					sql = "INSERT INTO motos VALUES(?,?,?,?,?,?,?,?,?)";
					stmt = conexionBD.prepareStatement(sql);
					int i = 1;
					stmt.setString(i++, vehicle.getMatricula());
					stmt.setString(i++, vehicle.getMarca());
					stmt.setString(i++, vehicle.getModel());
					stmt.setString(i++, vehicle.getVersio());
					stmt.setDouble(i++, vehicle.getEmisionsCO2());
					stmt.setDouble(i++, vehicle.getPreu());
					stmt.setDouble(i++, 0);
					stmt.setString(i++, ((Moto)vehicle).getCategoria());
					stmt.setInt(i++, Integer.valueOf(((Moto)vehicle).getCilindrada()));

				}
			} else{
				if (vehicle instanceof Cotxe){
					sql = "UPDATE cotxes SET marca=?,model=?,versio=?,emisionsco2=?,preu=?,portes=?,places=? WHERE matricula = ?";
					stmt = conexionBD.prepareStatement(sql);
					int i = 1;
					stmt.setString(i++, vehicle.getMarca());
					stmt.setString(i++, vehicle.getModel());
					stmt.setString(i++, vehicle.getVersio());
					stmt.setDouble(i++, vehicle.getEmisionsCO2());
					stmt.setDouble(i++, vehicle.getPreu());
					stmt.setInt(i++, Integer.valueOf(((Cotxe)vehicle).getPortes()));
					stmt.setInt(i++, Integer.valueOf(((Cotxe)vehicle).getPlaces()));
					stmt.setString(i++, vehicle.getMatricula());
				}else{
					sql = "UPDATE motos SET marca=?,model=?,versio=?,emisionsco2=?,preu=?,categoria=?,cilindrada=? WHERE matricula = ?";
					stmt = conexionBD.prepareStatement(sql);
					int i = 1;
					stmt.setString(i++, vehicle.getMarca());
					stmt.setString(i++, vehicle.getModel());
					stmt.setString(i++, vehicle.getVersio());
					stmt.setDouble(i++, vehicle.getEmisionsCO2());
					stmt.setDouble(i++, vehicle.getPreu());
					stmt.setString(i++, ((Moto)vehicle).getCategoria());
					stmt.setInt(i++, Integer.valueOf(((Moto)vehicle).getCilindrada()));
					stmt.setString(i++, vehicle.getMatricula());
				}
			}
			stmt.executeUpdate();
			return true;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	@Override
	public Vehicle buscar(String matricula) {
		if (matricula == null || matricula.equals("")){
			return null;
		}

		Vehicle v = null;
		try {
			//https://www.postgresql.org/docs/9.5/static/ddl-inherit.html
			PreparedStatement stmt = conexionBD.prepareStatement("SELECT v.tableoid::regclass as regclass,* "
					+ "FROM vehicles v "
					+ "LEFT OUTER JOIN cotxes c ON c.matricula = v.matricula "
					+ "LEFT OUTER JOIN motos m ON m.matricula = v.matricula "
					+ "WHERE v.matricula = ?");
			stmt.setString(1, matricula);
			ResultSet result = stmt.executeQuery();
			if (result.next()) {
				if (result.getString("regclass").equals("cotxes")){
					v = new Cotxe(result.getString("matricula"), result.getString("marca"), result.getString("model"),result.getString("versio"),result.getInt("emisionsco2"), result.getLong("preu"),result.getInt("portes"),result.getInt("places"));
				}else{

					v = new Moto(result.getString("matricula"), result.getString("marca"), result.getString("model"),result.getString("versio"),result.getInt("emisionsco2"), result.getLong("preu"),result.getString("categoria"),result.getInt("cilindrada"));
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return v;
	}

	@Override
	public boolean eliminar(String matricula) throws Exception {
		PreparedStatement stament = conexionBD.prepareStatement("delete from vehicles where matricula=?");
		stament.setString(1, matricula);
		stament.executeUpdate();
		stament.close();
		return true;		
	}

	@Override
	public void imprimirTots() {

		System.out.println("-------------------");
		System.out.println("Tots els vehicles");
		System.out.println("-------------------");

		try {
			PreparedStatement stmt = conexionBD.prepareStatement("SELECT v.tableoid::regclass as regclass,* "
					+ "FROM vehicles v "
					+ "LEFT OUTER JOIN cotxes c ON c.matricula = v.matricula "
					+ "LEFT OUTER JOIN motos m ON m.matricula = v.matricula");
			ResultSet result = stmt.executeQuery();
			while (result.next()) {
				Vehicle v = null;
				if (result.getString("regclass").equals("cotxes")){
					v = new Cotxe(result.getString("matricula"), result.getString("marca"), result.getString("model"),result.getString("versio"),result.getInt("emisionsco2"), result.getLong("preu"),result.getInt("portes"),result.getInt("places"));
				}else{

					v = new Moto(result.getString("matricula"), result.getString("marca"), result.getString("model"),result.getString("versio"),result.getInt("emisionsco2"), result.getLong("preu"),result.getString("categoria"),result.getInt("cilindrada"));
				}
				v.imprimir();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void imprimirPerMarca(String marca) throws SQLException {
		PreparedStatement stament = conexionBD.prepareStatement("select * from vehicles where marca=?");
		stament.setString(1, marca);
		ResultSet rs=stament.executeQuery();
		ResultSetMetaData rsmd = rs.getMetaData();
		   int columnsNumber = rsmd.getColumnCount();
		   while (rs.next()) {
		       for (int i = 1; i <= columnsNumber; i++) {
		           String columnValue = rs.getString("marca");
		           System.out.print(columnValue);
		       }
		       System.out.println("");
		   }
		
		rs.close();	
	}

	public void actualitzarPreuFinal() {
		//TODO
	}
}
