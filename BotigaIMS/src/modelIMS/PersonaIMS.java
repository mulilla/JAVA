package modelIMS;

import java.util.TreeSet;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class PersonaIMS {
	@Id
	private int idpersona;
	
	@ElementCollection(fetch = FetchType.EAGER)
	private TreeSet<String> Telefons=new TreeSet<String>();
	
}
