package autobotiga;

public abstract class Vehicle {
	
	private String matricula;
	private String marca;
	private String model;
	private String versio;
	private int emisionsCO2;
	private double preu;
	private double preu_final;
	private final int IVA = 21;

	public Vehicle() {
		this.matricula = "";
		this.marca = "";
		this.model = "";
		this.versio = "";
		this.emisionsCO2 = 0;
		this.preu = 0;
		this.preu_final = 0;
	}
	
	public Vehicle(String matricula, String marca, String model, String versio, int emisionsCO2, double preu) {
		this.matricula = matricula;
		this.marca = marca;
		this.model = model;
		this.versio = versio;
		this.emisionsCO2 = emisionsCO2;
		this.preu = preu;
		this.preu_final = 0;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getVersio() {
		return versio;
	}

	public void setVersio(String versio) {
		this.versio = versio;
	}

	public int getEmisionsCO2() {
		return emisionsCO2;
	}

	public void setEmisionsCO2(int emisionsCO2) {
		this.emisionsCO2 = emisionsCO2;
	}

	public double getPreu() {
		return preu;
	}

	public void setPreu(double preu) {
		this.preu = preu;
	}

	public double getPreu_final() {
		return preu_final;
	}

	public void setPreu_final(double preu_final) {
		this.preu_final = preu_final;
	}

	public int getIVA() {
		return IVA;
	}
	
	public double getImpostMatriculacio(){
		
		double impost=0.0;
		if (emisionsCO2 <= 80){
			impost=0.0;
		}else if(emisionsCO2 > 80 && emisionsCO2 <= 120){
			impost=0.0475;
		}else if(emisionsCO2 > 120 && emisionsCO2 <= 140){
			impost=0.0975;
		}else if(emisionsCO2 > 140){
			impost=0.1475;
		}
		
		return impost;
	}
	
	public static double getImpostMatriculacio(int emisionsCO2){
		
		double impost=0.0;
		if (emisionsCO2 < 80){
			impost=0.0;
		}else if(emisionsCO2 > 80 && emisionsCO2 <= 120){
			impost=0.0475;
		}else if(emisionsCO2 > 120 && emisionsCO2 <= 140){
			impost=0.0975;
		}else if(emisionsCO2 > 140){
			impost=0.1475;
		}
		
		return impost;
	}
	
	public void imprimir(){
		System.out.println("************************");
		System.out.println("MATRÍCULA: " + matricula);
		System.out.println("marca: " + marca);
		System.out.println("model: " + model);
		System.out.println("versio: " + versio);
		System.out.println("emisions CO2: " + Integer.toString(emisionsCO2));
		System.out.println("preu: " + Double.toString(preu));
		System.out.println("impost matriculació: " + getImpostMatriculacio() + "%");
	}

	
}
