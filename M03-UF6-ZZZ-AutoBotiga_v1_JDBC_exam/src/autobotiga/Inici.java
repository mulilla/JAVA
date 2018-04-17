package autobotiga;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;

public class Inici {

	public static void main(String[] args) throws Exception {

		Vehicles vehicles = null;
		Vehicle vehicle = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int option = 0;
		String matricula;
		Connection conexionBD = null;

		try{
			//Carregar el controlador per la BD PostgreSQL
			Class.forName("org.postgresql.Driver");

			//Establir la connexió amb la BD
			String urlBaseDades = "jdbc:postgresql://192.168.123.35/examenvehicles";
			String usuari = "postgres";
			String contrasenya = "Badia123";
			conexionBD = DriverManager.getConnection(urlBaseDades , usuari, contrasenya);
			vehicles = new Vehicles(conexionBD);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		do{
			System.out.println("0. Sortir");
			System.out.println("1. Afegir vehicle");
			System.out.println("2. Eliminar vehicle");
			System.out.println("3. Buscar vehicle");
			System.out.println("4. Imprimir tots els vehicles");
			System.out.println("5. Imprimir per model");
			System.out.println("6. Actualitzar preu final");
			System.out.println("7. Imprimir per marca");
			System.out.print("Opció: ");
			option = Integer.parseInt(br.readLine());

			switch(option){
			
			case 1: 
				
				System.out.print("1 = Cotxe		2 = Moto");
				int option2 = Integer.parseInt(br.readLine());
				
				System.out.print("Matrícula: ");
				matricula = br.readLine();
				System.out.print("Marca: ");
				String marca = br.readLine();
				System.out.print("Model: ");
				String model = br.readLine();
				System.out.print("Versió: ");
				String versio = br.readLine();
				System.out.print("Emisions: ");
				int emisionsCO2 = Integer.parseInt(br.readLine());
				System.out.print("Preu: ");
				long preu = Long.parseLong(br.readLine());

				if (option2 == 1){				
					System.out.print("Portes: ");
					int portes = Integer.parseInt(br.readLine());
					System.out.print("Places: ");
					int places = Integer.parseInt(br.readLine());
					
					vehicle = new Cotxe(matricula, marca, model, versio, emisionsCO2, preu, portes, places);

					
				}else if (option2 == 2){

					System.out.print("Categoria: ");
					String categoria = br.readLine();
					System.out.print("Cilindrada: ");
					int cilindrada = Integer.parseInt(br.readLine());

					vehicle = new Moto(matricula, marca, model, versio, emisionsCO2, preu, categoria, cilindrada);
				}

				if(vehicles.guardar(vehicle)){
					System.out.println("El vehicle s'ha afegit correctament"); 
				}
				else{
					System.out.println("El vehicle no s'ha afegit");
				}

				break;

				
			case 2:
				
				System.out.println("Matrícula a eliminar:");
				matricula=br.readLine();
				if(vehicles.eliminar(matricula)){
					System.out.println("El vehicle s'ha eliminat correctament"); 
				}
				else{
					System.out.println("El vehicle no s'ha eliminat");
				}
				
				break;
				
			case 3:
				System.out.println("Matrícula a buscar:");
				matricula=br.readLine();
				vehicle = vehicles.buscar(matricula);
				if(vehicle != null){
					vehicle.imprimir();
				}
				break;
				
			case 4:
				vehicles.imprimirTots();
				break;

			case 5:
				
				System.out.println("Model a buscar:");
				model=br.readLine();
				//vehicles.imprimirPerModel(model);
				break;
				
			case 6:
				vehicles.actualitzarPreuFinal();
				break;
			case 7:
				System.out.println("Marca a buscar:");
				marca=br.readLine();
				vehicles.imprimirPerMarca(marca);
			}
		}while(option != 0);
	}

}
