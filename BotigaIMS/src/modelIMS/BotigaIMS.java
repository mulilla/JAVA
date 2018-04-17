package modelIMS;

import java.util.List;
import java.time.LocalDate;
import java.util.TreeSet;
import java.util.*;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;

@SuppressWarnings("serial")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class BotigaIMS{
	
	@Id
	@Column(name="idbotiga")
	private int idbotiga;
	
	@OneToOne
	@JoinColumn(name = "idadreça")
	private AdrecaIMS adreca;
	
	@OneToMany
	@JoinTable(name="botiga_article")
	private List<ArticleIMS> articles;
	
	@OneToMany
	@JoinTable(name="botiga_comandavenda")
	private List<ComandaVendaIMS> comandesvenda;
	
	private String nom;
	
	private int num_treballadors;
 
	public BotigaIMS() {
		
	}
	
	
}
