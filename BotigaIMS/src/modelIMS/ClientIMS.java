package modelIMS;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

@Entity
public class ClientIMS extends PersonaIMS{
	private boolean enviar_publicitat;
	
	@OneToMany
	@JoinTable(name="client_adreça")
	private List<AdrecaIMS> adreces;
}
