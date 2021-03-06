package controlador;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.Locale.Category;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class IniciBotonsController extends Application {
	
	
	


	//Cargo la traduccion en el controlador
	private Locale localitzacioDisplay = Locale.getDefault(Category.DISPLAY);
	private ResourceBundle texts = ResourceBundle.getBundle("vista.Texts", localitzacioDisplay);
	
	
	
	@FXML
    private AnchorPane root;

    @FXML
    private Button btnProductos;

    @FXML
    private Button btnSalir;
    
    private EntityManager em;
    
    
	//COnstruct
	public IniciBotonsController() {
		super();
		
		try{
			EntityManagerFactory emf=Persistence.createEntityManagerFactory("BotigaFXBD Automatica");
			em = emf.createEntityManager();
			
			if(em!=null) {
				System.out.println("Conexion DB establecida");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
	}

	@Override
	public void start(Stage escenarioPrincipal) throws Exception {
		
		//Cargo la vista asociada al controlador Principal
		FXMLLoader loaderview = new FXMLLoader(getClass().getResource("/vista/IniciBotonsView.fxml"));
		
		//Cargo la traducion a la vista
		loaderview.setResources(texts);
		
		//Creo una Scena a partir de la vista
		Scene escenaPrincipal = new Scene(loaderview.load());
		
		//Asigno la escena al Escenario principal y abro el telon
		escenarioPrincipal.setScene(escenaPrincipal);
		escenarioPrincipal.setTitle(texts.getString("Inicio.title.botiga"));
		escenarioPrincipal.show();
		
	
	}
	
	
	 @FXML
	 public void OnActionbtnProductos(ActionEvent event) throws IOException {
		 
		
		 //Cargo en memoria la vista ProductosView y la cargo dentro de un Panel 
			FXMLLoader loaderview = new FXMLLoader(getClass().getResource("/vista/ProductosView.fxml"));
			AnchorPane Panel = (AnchorPane)loaderview.load();
	
			
			//Creo una nueva Ventana
			Stage escenarioProductos = new Stage();
			//Creo una escena y meto dentro el panel con la vista
			Scene escenaProductos = new Scene(Panel);
			
			escenarioProductos.setTitle("Productos");
			
			//Cargo la escena en el escenario y muestro el escenario
			escenarioProductos.setScene(escenaProductos);
			escenarioProductos.show();
			
			//Le entrego el escenario a su controlador y le doy la conexion a la BD
		
			ProductosController productoControler = (ProductosController)loaderview.getController();
			try {
				productoControler.setConnection(em);
			} catch (Exception e1) {
				System.out.println(e1.getMessage());
			}
			productoControler.setVentana(escenarioProductos);
			
		 
			//Handle para el evento de cierre del escenario
			escenarioProductos.setOnCloseRequest(new EventHandler<WindowEvent>() {
				public void handle(WindowEvent we) {
					try {
						productoControler.salir();
					} catch (IOException e) {
						
						e.printStackTrace();
					}
				}
			});   
		 

	 }

	 @FXML
	 public void OnActionbtnSalir(ActionEvent event) {
		 
		 Platform.exit();

	 }
	 
	 @Override
		public void stop() throws Exception {
		
			super.stop();
			
			try {
				if (em != null) em.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

}
