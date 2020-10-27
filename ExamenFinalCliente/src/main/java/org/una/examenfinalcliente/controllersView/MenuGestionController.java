package org.una.examenfinalcliente.controllersView;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Esteban Vargas
 */
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
    private void proyectos(MouseEvent event) {

    }

    @FXML
    private void poblacion(MouseEvent event) {
  
    }

    @FXML
    private void cobros(MouseEvent event) {
    }
}
