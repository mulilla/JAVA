package modelo;

import java.time.LocalDate;
import javax.persistence.Entity;

@SuppressWarnings("serial")
@Entity
public  class Joc extends Producto{
	
	//Parametros de clase
	
	/**
	 * 
	 */
	private int edad_minima;
	
	
	//Constructores
	public Joc(String id, String nom, double preu, int stock,LocalDate fecha_inicio,LocalDate fecha_final,int edad_minima, int id_proveedor) {
		super(id, nom, preu, stock,fecha_inicio,fecha_final,id_proveedor);
		this.edad_minima=edad_minima;
		
		
	}
	
	
	public Joc() {
		this("","",0.0,0,null,null,0,0);
	}

	
	
	//Getters And Setters
	public int getEdad_minima() {
		return edad_minima;
	}

	public void setEdad_minima(int edad_minima) {
		this.edad_minima = edad_minima;
	}

	

	//Metodo que imprime los parametros de la clase
	@Override
	public void imprimir() {
		System.out.println("---------------------------------------------");
		System.out.println("Joc");
		super.imprimir();
		System.out.println("Edad Minima "+edad_minima);
		System.out.println("---------------------------------------------");
	}
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	
}
