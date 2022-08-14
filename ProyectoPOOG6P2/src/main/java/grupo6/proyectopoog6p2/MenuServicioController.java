/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package grupo6.proyectopoog6p2;

import grupo6.proyectopoog6p2.modelo.Empleado;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import grupo6.proyectopoog6p2.modelo.Servicio;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;



public class MenuServicioController {
    @FXML
    private TableView<Servicio> tvListado;
    @FXML
    private Label lblLista;
    @FXML
    private Button btnAnadir;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnEliminar;
    
    public void initialize(){
        lblLista.setText("L I S T A  D E  S E R V I C I O S");
        lblLista.autosize();
        
        TableColumn<Servicio, String> colCedula = new TableColumn<>("Nombre");
        colCedula.setCellValueFactory(new PropertyValueFactory<>("nombreServicio"));
        
        TableColumn<Servicio, Integer> colNombre = new TableColumn<>("Duracion");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("duracion"));
        
        TableColumn<Servicio, Double> colTelefono = new TableColumn<>("Precio");
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("precio"));
        
        TableColumn<Servicio, Boolean> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        
        tvListado.getColumns().addAll(colCedula,colNombre,colTelefono,colEstado);
        llenarTabla();
    }
    
    public void llenarTabla(){
        tvListado.getItems().addAll(Servicio.cargarServicios(App.pathServicios));
    }
    
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
    
    @FXML
    private void anadirPersona() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("nuevo.fxml"));
        fxmlLoader.setController(null);
        NuevoServicioController nsc = new NuevoServicioController();
        fxmlLoader.setController(nsc);
        Parent root = (Parent) fxmlLoader.load();
        App.changeRoot(root);
    }
    
    @FXML
    private void editarPersona()throws IOException {
        Servicio s = (Servicio) tvListado.getSelectionModel().getSelectedItem();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("nuevo.fxml"));
        fxmlLoader.setController(null);
        EditarServicioController nsc = new EditarServicioController();
        fxmlLoader.setController(nsc);
        Parent root = (Parent) fxmlLoader.load();
        nsc.llenarCampos(s);
        App.changeRoot(root);
    }
    
     @FXML
    private void eliminarPersona()throws IOException {
        ArrayList<Servicio> servicios = Servicio.cargarServicios(App.pathServicios);
        Servicio servicioSeleccionado = (Servicio) tvListado.getSelectionModel().getSelectedItem();
        for(Servicio s:servicios){
            if(s.getNombreServicio().equals(servicioSeleccionado.getNombreServicio())){
                s.setEstado("N");
            }
        }
        try{
            BufferedWriter escritor = new BufferedWriter(new FileWriter("src/main/resources/grupo6/proyectopoog6p2/files/listaServicios.csv",false));
            for(Servicio s:servicios){
                escritor.write(s.toString()+"\n");
            }
            escritor.close();
        }catch(IOException e){
            System.out.println("Error eliminando servicio");
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Resultado de la operación");
        alert.setContentText("Servicio eliminado exitosamente");
        alert.showAndWait();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("menu.fxml"));//no tiene el controlador especificado
        fxmlLoader.setController(null);
        
        MenuServicioController mec = new MenuServicioController();
        fxmlLoader.setController(mec);
        Parent root = (Parent) fxmlLoader.load();
        
        //luego que el fxml ha sido cargado puedo utilizar el controlador para realizar cambios
        App.changeRoot(root);
    }
}