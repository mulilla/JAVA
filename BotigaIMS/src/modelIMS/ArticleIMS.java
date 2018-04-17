package modelIMS;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ArticleIMS {
	@Id
	private int idarticle;
	
	private String nom;
	
	private double preu_venda;
	
	private int stock;
}
