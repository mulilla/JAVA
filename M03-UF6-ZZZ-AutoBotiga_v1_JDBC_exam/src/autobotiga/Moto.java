package autobotiga;

public class Moto extends Vehicle {
	
	private String categoria;
	private int cilindrada;

	public Moto() {
		super();
		this.categoria = "";
		this.cilindrada = 0;	}

	public Moto(String matricula, String marca, String model, String versio, int emisionsCO2, double preu, String categoria, int cilindrada) {
		super(matricula, marca, model, versio, emisionsCO2, preu);
		this.categoria = categoria;
		this.cilindrada = cilindrada;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public int getCilindrada() {
		return cilindrada;
	}

	public void setCilindrada(int cilindrada) {
		this.cilindrada = cilindrada;
	}

	@Override
	public double getImpostMatriculacio() {
		if (cilindrada < 250){
			return 0.0;
		}else{
			return super.getImpostMatriculacio();
		}
	}

	@Override
	public void imprimir() {
		super.imprimir();
		System.out.println("categoria: " + categoria);
		System.out.println("cilindrada: " + Integer.toString(cilindrada));
	}

}
