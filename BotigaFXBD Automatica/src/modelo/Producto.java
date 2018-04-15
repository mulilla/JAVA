package modelo;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;


@SuppressWarnings("serial")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Producto implements Serializable {
	
	@Id
	private String id;
	private String nom;
	private double preu;
	private int stock;
	@Column(columnDefinition="DATE")
	private LocalDate fecha_inicio;
	@Column(columnDefinition="DATE")
	private LocalDate fecha_final;
	private int id_proveedor;
	

	public Producto() {
		this("","",0.0,0,null,null,1);
			
	}
	public Producto(String id, String nom, double preu, int stock,LocalDate fecha_inicio,LocalDate fecha_final,int id_proveedor) {
		this.id = id;
		this.nom = nom;
		this.preu = preu;
		this.stock=stock;
		this.fecha_inicio=fecha_inicio;
		this.fecha_final=fecha_final;
		this.id_proveedor=id_proveedor;
	}
	

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public double getPreu() {
		return preu;
	}
	public void setPreu(double preu) {
		this.preu = preu;
	}
	
	
	public int getStock() {
		return stock;
	}
	
	public void setStock(int stock) {
		this.stock = stock;
	}
	
	
	public void ponStock(int stock) {
		
		this.stock+=stock;
		
	}
	
	public LocalDate getFecha_inicio() {
		return fecha_inicio;
	}
	public void setFecha_inicio(LocalDate fecha_inicio) {
		this.fecha_inicio = fecha_inicio;
	}
	public LocalDate getFecha_final() {
		return fecha_final;
	}
	public void setFecha_final(LocalDate fecha_final) {
		this.fecha_final = fecha_final;
	}
	
	
	public int getId_proveedor() {
		return id_proveedor;
	}
	public void setId_proveedor(int id_proveedor) {
		this.id_proveedor = id_proveedor;
	}
	public void quitarStock(int stock) throws StockInsuficientException {
		if(this.stock<stock) {
			StockInsuficientException error= new StockInsuficientException("No hay suficiente Stock");
			throw error;
		}else {
			this.stock-=stock;
		}
		
	}
	//Metodo que compara el nombre del producto instanciado con otro producto pasado por parametro
	

	
	public boolean equals(Producto producto) {
		return this.getNom().equals(producto.getNom());
		
		
	}
	
	
	
	
	
	//Imprime los parametros de la clase productos.
	public void imprimir() {
		
		System.out.println("ID: "+id+" Nombre: "+nom+" Precio: "+preu+"Stock: "+stock+" Fecha Inicio: "+fecha_inicio+" Fecha_final: "+fecha_final+" Id proveedor: "+id_proveedor);
	}
	
	
	
	

}
