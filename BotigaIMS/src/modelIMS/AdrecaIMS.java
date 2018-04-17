package modelIMS;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class AdrecaIMS {
	@Id
	@GeneratedValue 
	private int idadreca;
	
	private String poblacio;
	
	private String provincia;
	
	private String cp;
	
	private String domicili;
}
