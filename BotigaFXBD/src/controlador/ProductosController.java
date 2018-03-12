package controlador;

//http://code.makery.ch/blog/javafx-8-event-handling-examples/

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import modelo.*;

public class ProductosController {

	private Stage ventana;
	@SuppressWarnings("unused")
	private Connection conexionBD;
	private Productos dao_productos=null;

	// Inyecion de los campos Producto
	@FXML
	private AnchorPane root;

	@FXML
	private TextField idTextfield;

	@FXML
	private TextField nomTexfield;

	@FXML
	private TextField stockTexfield;

	@FXML
	private TextField precioTextfield;

	@FXML
	private DatePicker I_catalogoDatePicker;

	@FXML
	private DatePicker f_catalogoDatePicker;

	@FXML
	private ComboBox<String> tipoComboBox;

	// Tab Pane elements

	@FXML
	private TabPane datosTabpane;

	@FXML
	private Tab jocTab;

	@FXML
	private TextField edadminimaTextfield;

	@FXML
	private TextField proveedorTextField;

	@FXML
	private Tab packTab;

	@FXML
	private TextField descuentoTextField;

	@FXML
	private TextField listadejuegosTextfield;

	// Botones
	@FXML
	private Button guardarButton;

	@FXML
	private Button modificarButton;
	
	@FXML
	private Button eliminarButton;

	@FXML
	private Button salirButton;

	@FXML
	private void initialize() throws IOException {
		
	
		// Inyecto en el ComboBox dos Values.
		tipoComboBox.getItems().addAll("Joc", "Pack");

		// Desabilito la interfaz menos el campo ID

		nomTexfield.setDisable(true);
		stockTexfield.setDisable(true);
		I_catalogoDatePicker.setDisable(true);
		f_catalogoDatePicker.setDisable(true);
		tipoComboBox.setDisable(true);
		precioTextfield.setDisable(true);
		datosTabpane.setDisable(true);
		guardarButton.setDisable(true);
		modificarButton.setDisable(true);
		eliminarButton.setDisable(true);
		
		
		

	}

	@FXML
	private void onKeyPressedId(KeyEvent e) throws IOException {

		// Evento principal
		if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.TAB) {

			// Habilito la interfaz para intereactuar con ella (Insert,Update,Show)

			nomTexfield.setDisable(false);
			I_catalogoDatePicker.setDisable(false);
			f_catalogoDatePicker.setDisable(false);
			tipoComboBox.setDisable(false);
			precioTextfield.setDisable(false);
			datosTabpane.setDisable(false);
			modificarButton.setDisable(false);
			guardarButton.setDisable(false);

			// Si ID existe cargo los datos del producto (Desabilito el boton gaurdar)
			if (dao_productos.searchProducto(idTextfield.getText()) != null) {

				guardarButton.setDisable(true);
				
				//Habilito el boton borrar
				eliminarButton.setDisable(false);

				if (dao_productos.searchProducto(idTextfield.getText()) instanceof Joc) {
					Joc juego = (Joc) dao_productos.searchProducto(idTextfield.getText());
					nomTexfield.setText(juego.getNom());
					stockTexfield.setText(String.valueOf(juego.getStock()));
					precioTextfield.setText(String.valueOf(juego.getPreu()));
					I_catalogoDatePicker.setValue(juego.getFecha_inicio());
					f_catalogoDatePicker.setValue(juego.getFecha_final());
					tipoComboBox.setValue("Joc");
					edadminimaTextfield.setText(String.valueOf(juego.getEdad_minima()));
					proveedorTextField.setText(String.valueOf(juego.getId_proveedor()));

					// Desabilito el campo combo para que al modificar el producto no se elija
					// otro tipo de producto (Eso seria un nuevo producto no una modificacion)
					tipoComboBox.setDisable(true);
					// Habilito la Tab correspondiente al producto
					packTab.setDisable(true);
					jocTab.setDisable(false);
					// Abro el Tab que toca
					datosTabpane.getSelectionModel().select(jocTab);
				} else {
					Pack pack = (Pack) dao_productos.searchProducto(idTextfield.getText());
					nomTexfield.setText(pack.getNom());
					stockTexfield.setText(String.valueOf(pack.getStock()));
					precioTextfield.setText(String.valueOf(pack.getPreu()));
					I_catalogoDatePicker.setValue(pack.getFecha_inicio());
					f_catalogoDatePicker.setValue(pack.getFecha_final());
					descuentoTextField.setText(String.valueOf(pack.getDescuento()));
					listadejuegosTextfield.setText(pack.listajuegosToString());
					tipoComboBox.setValue("Pack");

					// Desabilito el campo combo para que al modificar el producto no se elija
					// otro tipo de producto (Eso seria un nuevo producto no una modificacion)
					tipoComboBox.setDisable(true);
					// Habilito la Tab correspondiente al producto
					jocTab.setDisable(true);
					packTab.setDisable(false);
					// Abro el Tab que toca
					datosTabpane.getSelectionModel().select(packTab);

				}

			} else {
				// Si no existe Nuevo registro limpio formulario menos ID y habilito el campo
				// stock
				nomTexfield.clear();
				stockTexfield.setEditable(true);
				stockTexfield.setDisable(false);
				stockTexfield.clear();
				I_catalogoDatePicker.getEditor().clear();
				f_catalogoDatePicker.getEditor().clear();
				// Vuelvo a habilitar el campo combo y lo seteo por defecto
				tipoComboBox.setDisable(false);
				tipoComboBox.setValue("Elije");

				precioTextfield.clear();
				edadminimaTextfield.clear();
				proveedorTextField.clear();
				descuentoTextField.clear();
				listadejuegosTextfield.clear();
				// Habilito el boton guardar para el insert y quito el modificar y borrar
				guardarButton.setDisable(false);
				modificarButton.setDisable(true);
				eliminarButton.setDisable(true);

				// quito el tab pane a la espera del evento del combobox en un nuevo registro
				// Para evitar insertar datos de un pack en un joc
				datosTabpane.setDisable(true);

			}
		}

	}

	@FXML
	private void OnActionguardarButton(ActionEvent event) {

		// Inserta un nuevo producto al clicar en guardar

		

		if (tipoComboBox.getValue().equals("Joc")) {
			
			if(validator("Joc")) {
				
				
				Joc juego = new Joc(idTextfield.getText(), nomTexfield.getText(),
						Double.parseDouble(precioTextfield.getText()), Integer.parseInt(stockTexfield.getText()),
						I_catalogoDatePicker.getValue(), f_catalogoDatePicker.getValue(),
						Integer.parseInt(edadminimaTextfield.getText()), Integer.parseInt(proveedorTextField.getText()));
				try {
					
					dao_productos.addProducto(juego);
				} catch (SQLException e) {
					
					Alert alert = new Alert(AlertType.ERROR);
					alert.initOwner(ventana);
					alert.setTitle("Error al guardar en la BD");
					alert.setHeaderText("Error en el registro del juego");
					alert.setContentText(e.getMessage());

					alert.showAndWait();
				}
				
			}

			

		} else if (tipoComboBox.getValue().equals("Pack")) {

			if(validator("Pack")) {
				
				Pack pack = new Pack(idTextfield.getText(), nomTexfield.getText(),
						Double.parseDouble(precioTextfield.getText()), Integer.parseInt(stockTexfield.getText()),
						I_catalogoDatePicker.getValue(), f_catalogoDatePicker.getValue(),
						generateTreeSetfromString(listadejuegosTextfield.getText()),
						Double.parseDouble(descuentoTextField.getText()));
				try {
					dao_productos.addProducto(pack);
				} catch (SQLException e) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.initOwner(ventana);
					alert.setTitle("Error al guardar en la BD");
					alert.setHeaderText("Error en el registro del Pack");
					alert.setContentText(e.getMessage());

					alert.showAndWait();
				
				}
				
				
				
				
			}
			

		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(ventana);
			alert.setTitle("Error al guardar");
			alert.setHeaderText("Tienes que indicar el tipo de Producto");
			alert.setContentText("Melon");

			alert.showAndWait();

		}

		limpiarFormulario();
		// AL insertar un nuevo producto quito la interfaz de nuevo
		nomTexfield.setDisable(true);
		stockTexfield.setDisable(true);
		I_catalogoDatePicker.setDisable(true);
		f_catalogoDatePicker.setDisable(true);
		tipoComboBox.setDisable(true);
		precioTextfield.setDisable(true);
		datosTabpane.setDisable(true);
		guardarButton.setDisable(true);
		modificarButton.setDisable(true);
		eliminarButton.setDisable(true);

	}

	@FXML
	private void OnActiontipo(ActionEvent event) {
		// Evento sobre el Combobox, habilita el TabPane que toca al insertar un
		// producto

		String value = tipoComboBox.getSelectionModel().getSelectedItem();

		if (value.equals("Joc")) {
			datosTabpane.setDisable(false);
			packTab.setDisable(true);
			jocTab.setDisable(false);
			datosTabpane.getSelectionModel().select(jocTab);
		} else if (value.equals("Pack")) {
			datosTabpane.setDisable(false);
			packTab.setDisable(false);
			jocTab.setDisable(true);
			datosTabpane.getSelectionModel().select(packTab);

		}

	}

	@FXML
	private void OnActionmodificarButton(ActionEvent event) {
		// Recojo el tipo de producto
		String value = tipoComboBox.getSelectionModel().getSelectedItem();

		// Envio los datos al DAO y este sobreescribira los productos
		if (value.equals("Joc")) {
			
			
			if(validator("Joc")) {
				Joc juego = new Joc(idTextfield.getText(), nomTexfield.getText(),
						Double.parseDouble(precioTextfield.getText()), Integer.parseInt(stockTexfield.getText()),
						I_catalogoDatePicker.getValue(), f_catalogoDatePicker.getValue(),
						Integer.parseInt(edadminimaTextfield.getText()), Integer.parseInt(proveedorTextField.getText()));
				dao_productos.updateProducto(juego);
				
			}

			

		} else if (value.equals("Pack")) {

			if(validator("Pack")) {
				Pack pack = new Pack(idTextfield.getText(), nomTexfield.getText(),
						Double.parseDouble(precioTextfield.getText()), Integer.parseInt(stockTexfield.getText()),
						I_catalogoDatePicker.getValue(), f_catalogoDatePicker.getValue(),
						generateTreeSetfromString(listadejuegosTextfield.getText()),
						Double.parseDouble(descuentoTextField.getText()));
				dao_productos.updateProducto(pack);
				guardarButton.setDisable(false);
				
			}
			

		}

		limpiarFormulario();
		// AL modificar un producto quito la interfaz de nuevo
		nomTexfield.setDisable(true);
		stockTexfield.setDisable(true);
		I_catalogoDatePicker.setDisable(true);
		f_catalogoDatePicker.setDisable(true);
		tipoComboBox.setDisable(true);
		precioTextfield.setDisable(true);
		datosTabpane.setDisable(true);
		guardarButton.setDisable(true);
		modificarButton.setDisable(true);

	}
	
	
	
	@FXML
	private void OnActioneliminarButton(ActionEvent event) {
		
		//Implementar 
		//cojer la ID de un registro existente y mandarselo al DAO 
		
		//dao_productos.deleteProducto(id)
		
		
		
	}

	@FXML
	private void OnActionsalirButton(ActionEvent event) throws IOException {
		// Evento al clicar sobre el boton salir
		salir();

	}

	//CONSTRUCT 
	
	
	public Stage getVentana() {
		return ventana;
	}

	public void setVentana(Stage ventana) {
		this.ventana = ventana;
	}
	
	public void setConnection(Connection conexionBD) throws Exception {
		this.conexionBD=conexionBD;
		
		if(conexionBD!=null) {
			dao_productos=new Productos(conexionBD);
		}else {
			Exception error= new Exception("La conexion a la BD no se ha podido establecer desde el controlador");
			throw error;
		}
		
		
	}

	// Metodo que se ejecuta al salir de la aplicacion
	public void salir() throws IOException {

		// Cierro la conexion de la BD y cierro la ventana
		try {
			dao_productos.closeDB();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		ventana.close();

	}

	private TreeSet<String> generateTreeSetfromString(String data) {

		// http://java-tweets.blogspot.com.es/2012/07/convert-array-to-treeset-in-java.html

		String[] vector = data.split(",");
		List<String> list = Arrays.asList(vector);
		TreeSet<String> set = new TreeSet<String>(list);

		return set;

	}

	private void limpiarFormulario() {
		idTextfield.clear();
		nomTexfield.clear();
		stockTexfield.clear();
		I_catalogoDatePicker.getEditor().clear();
		f_catalogoDatePicker.getEditor().clear();
		tipoComboBox.setValue("Elige");
		precioTextfield.clear();
		edadminimaTextfield.clear();
		proveedorTextField.clear();
		descuentoTextField.clear();
		listadejuegosTextfield.clear();
		packTab.setDisable(false);
		jocTab.setDisable(false);

	}

	private boolean validator(String tipo) {
		
	
		StringBuffer error=new StringBuffer();
		//Compruebo los campos genericos de un producto
		
		if(nomTexfield.getText()==null||nomTexfield.getText().length()==0) error.append("Campo Nombre incorrecto debe tener almenos un caracter");
		
		if(stockTexfield.getText()==null||stockTexfield.getText().length()==0) error.append("\nCampo Stock incorrecto debe tener como minimo una unidad");
		else {
			try {
				Integer.parseInt(stockTexfield.getText());
				
			}catch (NumberFormatException e) {
				error.append("\nEl Campo Stock debe ser un numero Entero");
			}
		}
		
		
		if(I_catalogoDatePicker.getValue()==null||f_catalogoDatePicker.getValue()==null) error.append("\nLa fecha de entrada y salida son necesarias");
		
		else {
			if(f_catalogoDatePicker.getValue().compareTo(I_catalogoDatePicker.getValue())<1) error.append("\nLa fecha final debe ser superior a la fecha incial");
			
		}
		
		if(precioTextfield.getText()==null||precioTextfield.getText().length()==0) error.append("\nEl campo precio no puede estar en blanco");
		else {
			try {
				Double.parseDouble(precioTextfield.getText());
			}catch (NumberFormatException e) {
				error.append("\nEl campo precio debe ser un numero real");
			}
		}
		
		//Compruebo los campos especificos de cada producto
		
		
		switch(tipo) {
		
			case "Joc":
				
				if(edadminimaTextfield.getText()==null||edadminimaTextfield.getText().length()==0) error.append("\nEl campo Edad no puede ser vacio");
				else {
					try {
						Integer.parseInt(edadminimaTextfield.getText());
					}catch (NumberFormatException e) {
						error.append("\nEl campo edad debe ser un numero entero");
						
					}
				}
				
				
				if(proveedorTextField.getText()==null||proveedorTextField.getText().length()==0) error.append("\nEl campo Proveedor no puede ser vacio");
				else {
					try {
						Integer.parseInt(proveedorTextField.getText());
					}catch (NumberFormatException e) {
						error.append("\nEl campo proveedor debe ser un numero entero");
						
					}
				}
				
				break;
			
			case "Pack":
				
				//Creo un patron para verificar que el campo listajuegos es correcto
				
				
				 if(listadejuegosTextfield.getText()==null||listadejuegosTextfield.getText().length()==0) error.append("\nEl campo listajuegos no puede estar vacio");
				 else {
					 Pattern p = Pattern.compile("^[0-9],[0-9]");
				     Matcher m = p.matcher(listadejuegosTextfield.getText());
				     if(!m.find()) error.append("\nEl campo listajuegos no cumple el patron{0-9,}");
				 
				 }
				 
				 if(descuentoTextField.getText()==null||descuentoTextField.getText().length()==0) error.append("\nEl campo descuento no puede estar vacio");
				 else {
					 try {
						 Double.parseDouble(descuentoTextField.getText());
					 }catch (NumberFormatException e) {
						error.append("\nEl campo descuento debe ser un numero Real");
					}
				 }
				break;
			
			
			default:
				break;
		
		}//Fin Case
		
		
		if(error.toString().length()==0) return true;
		else {
			
			
				Alert alert = new Alert(AlertType.ERROR);
				alert.initOwner(ventana);
				alert.setTitle("Campos incorrectos");
				alert.setHeaderText("Corrije los campos");
				alert.setContentText(error.toString());

				alert.showAndWait();

				return false;
		}


	

	}

	
}
