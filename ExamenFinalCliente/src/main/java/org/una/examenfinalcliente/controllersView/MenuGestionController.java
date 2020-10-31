package org.una.examenfinalcliente.controllersView;

import com.fasterxml.jackson.databind.JsonMappingException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.una.examenfinalcliente.utility.FlowController;
import org.una.examenfinalcliente.utility.FlowController;

public class MenuGestionController extends Controller implements Initializable {

    @FXML
    private VBox root;
    
    @Override
    public void initialize() {
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @Override
    public Node getRoot() {
        return root;
    }  

    @FXML
    private void proyectos(MouseEvent event) throws InterruptedException, ExecutionException, JsonMappingException, IOException {
        FlowController.getInstance().goView("VistaProyectosTareas");
    }


    @FXML
    private void poblacion(MouseEvent event) throws InterruptedException, ExecutionException, JsonMappingException, IOException {
        FlowController.getInstance().goView("CensoPoblacional");
    }

    @FXML
    private void cobros(MouseEvent event) {
        FlowController.getInstance().goView("CobrosPendientes");
    }
}
