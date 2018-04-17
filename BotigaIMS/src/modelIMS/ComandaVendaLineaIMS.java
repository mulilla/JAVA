package modelIMS;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class ComandaVendaLineaIMS {

	@Id
	private int idlinia;
	
	@OneToOne
	@JoinColumn(name = "idarticle")
	private ArticleIMS article;
	
	private String descripcio;
	
	private double preu;
	
	private int quantitat;
	
	private double importe;
	
}
