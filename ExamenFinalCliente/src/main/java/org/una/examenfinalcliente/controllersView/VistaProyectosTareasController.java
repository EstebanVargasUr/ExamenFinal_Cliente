package org.una.examenfinalcliente.controllersView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javax.swing.JOptionPane;
import org.una.examenfinalcliente.DTOs.ProyectoDTO;
import org.una.examenfinalcliente.DTOs.TareaDTO;
import org.una.examenfinalcliente.WebService.ProyectoWebService;
import org.una.examenfinalcliente.WebService.TareaWebService;
import org.una.examenfinalcliente.utility.FlowController;

/**
 * FXML Controller class
 *
 * @author Esteban Vargas
 */
public class VistaProyectosTareasController extends Controller implements Initializable {

    @FXML
    private VBox root;
    @FXML
    private TreeTableView<Modelo> tabla;
    @FXML
    private TreeTableColumn<Modelo, String> proyectos;
    @FXML
    private TreeTableColumn<Modelo, String> id;
    @FXML
    private TreeTableColumn<Modelo, String> fechaInicio;
    @FXML
    private TreeTableColumn<Modelo, String> fechaFinal;
    @FXML
    private TreeTableColumn<Modelo, String> importancia;
    @FXML
    private TreeTableColumn<Modelo, String> urgencia;
    @FXML
    private TreeTableColumn<Modelo, String> prioridad;
    @FXML
    private TreeTableColumn<Modelo, String> avance;
    @FXML
    private HBox selectorColor;
    @FXML
    private ColorPicker cp_color;
    @FXML
    private Label lbl_rangoInicial;
    @FXML
    private Label lbl_rangoFinal;
    @FXML
    private Label lbl_escojerColor;
    @FXML
    private JFXTextField txt_rangoInicial;
    @FXML
    private JFXTextField txt_rangoFinal;

     
    TreeItem<Modelo> ModeloProyecto = new TreeItem<>(new Modelo("Proyecto","-","-","-","-","-","-","-"));
    TreeItem<Modelo> ModeloTarea = new TreeItem<>(new Modelo("Tarea","-","-","-","-","-","-","-"));
    TreeItem<Modelo> treeRoot = new TreeItem<>(new Modelo("Proyectos","-","-","-","-","-","-","-"));
    
    TreeItem<Modelo> proyecto;
    TreeItem<Modelo> tarea;
       
    void CargarDatosIniciales() throws InterruptedException, ExecutionException, IOException
    {
        List<ProyectoDTO> proyectosModel = ProyectoWebService.getAllProyectos();
        for (int i = 0; i < proyectosModel.toArray().length; i++)
        {
            proyecto = new TreeItem<>(new Modelo(proyectosModel.get(i).getNombreProyecto(),
              proyectosModel.get(i).getId()+"", proyectosModel.get(i).getFechaInicio()+"",
               proyectosModel.get(i).getFechaFinalizacion()+"","-","-","-",proyectosModel.get(i).getPorcentajeAvance()+""));
            
            
            List<TareaDTO> tareas = TareaWebService.getTareasByProyecto(proyectosModel.get(i).getId()+"");
            for(int j = 0; j < tareas.toArray().length; j++){
                tarea = new TreeItem<>(new Modelo(tareas.get(j).getDescripcion(),tareas.get(j).getId()+"",
                tareas.get(j).getFechaInicio()+"",tareas.get(j).getFechaFinalizacion()+"",
            tareas.get(j).getImportancia()+"",tareas.get(j).getUrgencia()+"",tareas.get(j).getPrioridad()+"",tareas.get(j).getPorcentajeAvance()+""));
            proyecto.getChildren().add(tarea);
            }
            ModeloProyecto.getChildren().add(proyecto);
        }
    }
    @Override
    public void initialize() {
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) { 
        iniciar();
    }     
    
    void iniciar(){
        try {
            CargarDatosIniciales();
            
            proyectos.setCellValueFactory((TreeTableColumn.CellDataFeatures<Modelo, String> param) -> param.getValue().getValue().getDescripcionProperty());
            id.setCellValueFactory((TreeTableColumn.CellDataFeatures<Modelo, String> param) -> param.getValue().getValue().getIdProperty());
            fechaInicio.setCellValueFactory((TreeTableColumn.CellDataFeatures<Modelo, String> param) -> param.getValue().getValue().getFechaInicioProperty());
            fechaFinal.setCellValueFactory((TreeTableColumn.CellDataFeatures<Modelo, String> param) -> param.getValue().getValue().getFechaFinalProperty());
            importancia.setCellValueFactory((TreeTableColumn.CellDataFeatures<Modelo, String> param) -> param.getValue().getValue().getImportanciaProperty());
            urgencia.setCellValueFactory((TreeTableColumn.CellDataFeatures<Modelo, String> param) -> param.getValue().getValue().getUrgenciaProperty());
            prioridad.setCellValueFactory((TreeTableColumn.CellDataFeatures<Modelo, String> param) -> param.getValue().getValue().getPrioridadProperty());
            avance.setCellValueFactory((TreeTableColumn.CellDataFeatures<Modelo, String> param) -> param.getValue().getValue().getPorcentajeProperty());
            
            treeRoot.getChildren().setAll(ModeloProyecto);

            tabla.setRoot(ModeloProyecto);
            tabla.setShowRoot(false);
        
        } catch (InterruptedException | ExecutionException | IOException ex) { Logger.getLogger(VistaProyectosTareasController.class.getName()).log(Level.SEVERE, null, ex); }   
    }
    
    @Override
    public Node getRoot() {
        return root;
    }  

    @FXML
    void colorAvance(ActionEvent event) {
        cp_color.setVisible(true);
        lbl_escojerColor.setVisible(true);
        lbl_rangoInicial.setVisible(true);
        txt_rangoInicial.setVisible(true);
        txt_rangoFinal.setPromptText("Digite el porcentaje Maximo");
        lbl_rangoFinal.setText("Rango Final:");
        selectorColor.setVisible(true);
    }
    
    @FXML
    void guardarColor(MouseEvent event) throws InterruptedException, ExecutionException, JsonProcessingException {
        if(lbl_rangoFinal.getText().equals("Nombre Proyecto:")){
            if(txt_rangoFinal.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Debe llenar el campos correspondiente");
            }
            else{
                ProyectoWebService.createProyecto(txt_rangoFinal.getText());
                JOptionPane.showMessageDialog(null, "Se ha creado correctamente el Proyecto");
            }
        }
    }
    
    @FXML
    private void cancelarColor(MouseEvent event) {
        selectorColor.setVisible(false);
    }
    
    @FXML
    private void menu(ActionEvent event) {
        FlowController.getInstance().goView("MenuGestion");
    }
       
    @FXML
    void crearProyecto(ActionEvent event) {
        cp_color.setVisible(false);
        lbl_escojerColor.setVisible(false);
        lbl_rangoInicial.setVisible(false);
        txt_rangoInicial.setVisible(false);
        txt_rangoFinal.setPromptText("Digite el nombre del proyecto");
        lbl_rangoFinal.setText("Nombre Proyecto:");
        selectorColor.setVisible(true);
    }

    @FXML
    void crearTarea(ActionEvent event) {
        FlowController.getInstance().goView("RegistroTarea");
    }

    @FXML
    private void actualizar(MouseEvent event) {
        tabla.getRoot().getChildren().clear();
        iniciar();
    }

    
    public class Modelo {

        private SimpleStringProperty idProperty;
        private SimpleStringProperty descripcionProperty;
        private SimpleStringProperty fechaInicioProperty;
        private SimpleStringProperty fechaFinalProperty;
        private SimpleStringProperty importanciaProperty;
        private SimpleStringProperty urgenciaProperty;
        private SimpleStringProperty prioridadProperty;
        private SimpleStringProperty porcentajeProperty;

        public Modelo(String descripcion, String id, String fechaInicio, String fechaFinal,
                String importancia, String urgencia,String prioridad, String porcentaje) {
            this.idProperty = new SimpleStringProperty(id);
            this.descripcionProperty = new SimpleStringProperty(descripcion);
            this.fechaInicioProperty = new SimpleStringProperty(fechaInicio);
            this.fechaFinalProperty = new SimpleStringProperty(fechaFinal);
            this.importanciaProperty = new SimpleStringProperty(importancia);
            this.urgenciaProperty = new SimpleStringProperty(urgencia);
            this.prioridadProperty= new SimpleStringProperty(prioridad);
            this.porcentajeProperty = new SimpleStringProperty(porcentaje);
        }

        public SimpleStringProperty getIdProperty() {
            return idProperty;
        }

        public SimpleStringProperty getDescripcionProperty() {
            return descripcionProperty;
        }

        public SimpleStringProperty getFechaInicioProperty() {
            return fechaInicioProperty;
        }

        public SimpleStringProperty getFechaFinalProperty() {
            return fechaFinalProperty;
        }

        public SimpleStringProperty getImportanciaProperty() {
            return importanciaProperty;
        }

        public SimpleStringProperty getUrgenciaProperty() {
            return urgenciaProperty;
        }

        public SimpleStringProperty getPrioridadProperty() {
            return prioridadProperty;
        }

        public SimpleStringProperty getPorcentajeProperty() {
            return porcentajeProperty;
        }
  
    }
    
}