package autobotiga;

public class Cotxe extends Vehicle {
	
	private int portes;
	private int places;

	public Cotxe() {
		super();
		this.portes = 0;
		this.places = 0;

	}

	public Cotxe(String matricula, String marca, String model, String versio, int emisionsCO2, double preu, int portes, int places) {
		super(matricula, marca, model, versio, emisionsCO2, preu);
		this.portes = portes;
		this.places = places;

	}
	
	public int getPortes() {
		return portes;
	}

	public void setPortes(int portes) {
		this.portes = portes;
	}

	public int getPlaces() {
		return places;
	}

	public void setPlaces(int places) {
		this.places = places;
	}

	public void imprimir() {
		super.imprimir();
		System.out.println("portes: " + Integer.toString(portes));
		System.out.println("places: " + Integer.toString(places));
	}

}
