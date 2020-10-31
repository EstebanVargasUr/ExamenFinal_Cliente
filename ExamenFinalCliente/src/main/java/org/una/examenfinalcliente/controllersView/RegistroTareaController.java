package org.una.examenfinalcliente.controllersView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javax.swing.JOptionPane;
import org.una.examenfinalcliente.DTOs.ProyectoDTO;
import org.una.examenfinalcliente.WebService.ProyectoWebService;
import org.una.examenfinalcliente.WebService.TareaWebService;
import org.una.examenfinalcliente.utility.FlowController;

/**
 * FXML Controller class
 *
 * @author Esteban Vargas
 */
public class RegistroTareaController extends Controller implements Initializable {

    @FXML
    private VBox root;

    @FXML
    private JFXTextField txt_descripcion;

    @FXML
    private DatePicker dpk_fechaInicio;

    @FXML
    private DatePicker dpk_fechaFinal;

    @FXML
    private JFXTextField txt_importancia;

    @FXML
    private JFXTextField txt_urgencia;

    @FXML
    private JFXTextField txt_avance;

    @FXML
    private JFXComboBox<String> cb_proyecto;
    
    List<ProyectoDTO> proyectos;

    @Override
    public void initialize() {
  
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            proyectos = ProyectoWebService.getAllProyectos();
            for (int i = 0; i < proyectos.toArray().length; i++){
                cb_proyecto.getItems().add(proyectos.get(i).getNombreProyecto());
            }
        } catch (InterruptedException | ExecutionException | IOException ex) { Logger.getLogger(RegistroTareaController.class.getName()).log(Level.SEVERE, null, ex); } 
        
        
        // TODO
    }    

    @Override
    public Node getRoot() {
        return root;
    } 
    
    @FXML
    private void cancelar(MouseEvent event) {
        FlowController.getInstance().goView("VistaProyectosTareas");
    }

    @FXML
    private void guardar(MouseEvent event) throws InterruptedException, ExecutionException, JsonProcessingException {
        
        if(txt_descripcion.getText().equals("") || txt_avance.getText().equals("") || txt_importancia.getText().equals("")
            || txt_urgencia.getText().equals("") || dpk_fechaInicio.getValue() == null || 
                dpk_fechaFinal.getValue() == null || cb_proyecto.getValue().equals("")){
             JOptionPane.showMessageDialog(null, "Debe llenar todos los campos correspondientes");
        }
        else{
            LocalDate localDate = dpk_fechaInicio.getValue();
            Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            Date inicio = Date.from(instant);
            
            LocalDate localDate2 = dpk_fechaInicio.getValue();
            Instant instant2 = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            Date fin = Date.from(instant);
                for (int i = 0; i < proyectos.toArray().length; i++){
                    if(proyectos.get(i).getNombreProyecto().equals(cb_proyecto.getValue())){
                        TareaWebService.createTarea(txt_descripcion.getText(), inicio, fin,
                        Short.valueOf(txt_importancia.getText()), Short.valueOf(txt_urgencia.getText()),
                        Integer.parseInt(txt_avance.getText()), proyectos.get(i));
                        JOptionPane.showMessageDialog(null, "Se ha creado correctamente la Tarea");
                    }
                    
                }
            
        }
            
            
    }
            
}
    
