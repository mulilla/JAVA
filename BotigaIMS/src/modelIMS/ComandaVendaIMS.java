package modelIMS;

import java.awt.List;
import java.time.LocalDate;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class ComandaVendaIMS {

	@Id
	private int idcomanda;
	
	@OneToOne
	private ClientIMS client;
	
	@ElementCollection(fetch = FetchType.EAGER)
	private TreeSet<String> ListaComandes=new TreeSet<String>();
	
	@Column(columnDefinition="DATE")
	private LocalDate dataEntrega;
	
	private double importe;

}
