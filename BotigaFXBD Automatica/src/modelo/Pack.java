package modelo;

import java.time.LocalDate;
import java.util.TreeSet;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;

@SuppressWarnings("serial")
@Entity
public class Pack extends Producto {
	
	
	
	/**
	 * 
	 */
	@ElementCollection(fetch = FetchType.EAGER)
	private TreeSet<String> ListaJuegos=new TreeSet<String>();
	private double descuento;

	
	//Constructores
	public Pack(String id, String nom, double preu,int stock,LocalDate fecha_inicio,LocalDate fecha_final,TreeSet<String> ListaJuegos,double descuento) {
		super(id, nom, preu, stock,fecha_inicio,fecha_final,1);
		this.ListaJuegos=ListaJuegos;
		this.descuento=descuento;
		
		
	}
	public Pack() {
		this("","",0.0,0,null,null,null,0.0);
		
	}
	
	
	//Getters and Setters
	public TreeSet<String> getListaJuegos() {
		return ListaJuegos;
	}
	public void setListaJuegos(TreeSet<String> listaJuegos) {
		ListaJuegos = listaJuegos;
	}
	public double getDescuento() {
		return descuento;
	}
	public void setDescuento(double descuento) {
		this.descuento = descuento;
	}
	
	//Metodo para imprimir los parametros de clase
	
	@Override
	public void imprimir() {
		System.out.println("---------------------------------------------");
		System.out.println("Pack");
		super.imprimir();
		System.out.println("Juegos incluidos en el pack ");
		
		for(String e:ListaJuegos) {
			System.out.print(e+" ");
		}
		
		System.out.print("\n");
		System.out.println("Descuento: "+descuento);
		System.out.println("---------------------------------------------");
	}
	
	
	//Metodo que compara el Pack instanciado con otro Pack pasado por parametro
	public boolean equals(Pack pack) {
		// TODO Auto-generated method stub
		return false;
	}
	
	//Metodo que permite añadir juegos al ArrayList ListaJuegos del Pack
	public boolean addGame(String id) {
		if(ListaJuegos.contains(id)) {
			
			return false;
			
		}else {
			ListaJuegos.add(id);
			
			return true;
			
		}
		
		
		
		
		
	}
	
	
	
	////Metodo que permite borrar juegos al ArrayList ListaJuegos del Pack
	public boolean deleteGame(String id) {
		
		if(!ListaJuegos.contains(id)) {
			
			
			return false;
		}else {
			ListaJuegos.remove(id);
			return true;
		}
		
		
	}
	

	public String listajuegosToString() {
		
		
		return String.join(",", ListaJuegos);
	}
	
	
	
	
	
	
}
