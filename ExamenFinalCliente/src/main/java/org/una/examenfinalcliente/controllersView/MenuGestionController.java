package org.una.examenfinalcliente.controllersView;

import com.fasterxml.jackson.databind.JsonMappingException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.una.examenfinalcliente.WebService.ProvinciaWebService;
import org.una.examenfinalcliente.utility.FlowController;

public class MenuGestionController extends Controller implements Initializable {

    @FXML
    private VBox root;

    private ProvinciaWebService provinciaWebService;
    
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
    private void proyectos(MouseEvent event) {
        try {
            System.out.println(provinciaWebService.getSumaCantidadPoblacionByProvinciaId(2));
        } catch (InterruptedException | ExecutionException | IOException ex) {
            Logger.getLogger(MenuGestionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @FXML
    private void poblacion(MouseEvent event) throws InterruptedException, ExecutionException, JsonMappingException, IOException {
        FlowController.getInstance().goView("CensoPoblacional");
    }

    @FXML
    private void cobros(MouseEvent event) {
    }
}
