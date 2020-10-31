/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.examenfinalcliente.controllersView;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.una.examenfinalcliente.WebService.CantonWebService;
import org.una.examenfinalcliente.WebService.DistritoWebService;
import org.una.examenfinalcliente.WebService.ProvinciaWebService;
import org.una.examenfinalcliente.WebService.UnidadWebService;
import org.una.examenfinalcliente.utility.FlowController;

/**
 * FXML Controller class
 *
 * @author adria
 */
public class CreacionPoblacionalController extends Controller implements Initializable{

    @FXML
    private VBox root;
    @FXML
    private ComboBox<String> cbAreaCrear;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtCodigo;
    @FXML
    private Label lblIdForanea;
    @FXML
    private TextField txtIDForanea;
    
    private ProvinciaWebService provinciaWebService; 
    @FXML
    private Label lblAreaCuadrada;
    @FXML
    private TextField txtAreaCuadrada;
    @FXML
    private Label lblPoblacion;
    @FXML
    private TextField txtPoblacion;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbAreaCrear.getItems().add("Provincia");
        cbAreaCrear.getItems().add("Canton");
        cbAreaCrear.getItems().add("Distrito");
        cbAreaCrear.getItems().add("Unidad");
    }    

    @Override
    public void initialize() {
    }

    @Override
    public Node getRoot() {
        return root;
    }

    @FXML
    private void btnGuardarAction(MouseEvent event) throws InterruptedException, ExecutionException, JsonProcessingException, IOException 
    {
        if (cbAreaCrear.getValue().equals("Provincia")) 
        {
            int num = Integer.parseInt(txtCodigo.getText());
            provinciaWebService.createProvincia(txtNombre.getText(), num);
        }
        if (cbAreaCrear.getValue().equals("Canton")) 
        {
            int num = Integer.parseInt(txtCodigo.getText());
            long num2 = Long.parseLong(txtIDForanea.getText());
            CantonWebService.createCanton(txtNombre.getText(), num, num2 );
        }
        if (cbAreaCrear.getValue().equals("Distrito")) 
        {
            int num = Integer.parseInt(txtCodigo.getText());
            long num2 = Long.parseLong(txtIDForanea.getText());
            DistritoWebService.createDistrito(txtNombre.getText(), num, num2);
        }
        if (cbAreaCrear.getValue().equals("Unidad")) 
        {
            int num = Integer.parseInt(txtCodigo.getText());
            long num2 = Long.parseLong(txtIDForanea.getText());
            long num3 = Long.parseLong(txtPoblacion.getText());
            double num4 = Double.parseDouble(txtAreaCuadrada.getText());
            UnidadWebService.createUnidad(txtNombre.getText(), num,num2,num3,num4);
        }
    }

    private void cbAreaCrearAction(MouseEvent event) 
    {
        if (cbAreaCrear.getValue().equals("Provincia")) 
        {
            txtIDForanea.setDisable(true);
        }
        else
        {
            txtIDForanea.setDisable(false);
        }
    }

    @FXML
    private void cbAreaCrearAction(ActionEvent event) {
        if (cbAreaCrear.getValue().equals("Provincia")) 
        {
            txtIDForanea.setDisable(true);
            lblAreaCuadrada.setVisible(false);
                lblPoblacion.setVisible(false);
                txtAreaCuadrada.setVisible(false);
                txtPoblacion.setVisible(false);
            lblIdForanea.setText("");
        }
        else
        {
            txtIDForanea.setDisable(false);
            
            if (cbAreaCrear.getValue().equals("Canton")) {
                lblIdForanea.setText("Provincia");
                lblAreaCuadrada.setVisible(false);
                lblPoblacion.setVisible(false);
                txtAreaCuadrada.setVisible(false);
                txtPoblacion.setVisible(false);
            }
            if (cbAreaCrear.getValue().equals("Distrito")) {
                lblIdForanea.setText("Canton");
                lblAreaCuadrada.setVisible(false);
                lblPoblacion.setVisible(false);
                txtAreaCuadrada.setVisible(false);
                txtPoblacion.setVisible(false);
            }    
             if (cbAreaCrear.getValue().equals("Unidad")) 
             {
                lblIdForanea.setText("Distrito");
                lblAreaCuadrada.setVisible(true);
                lblPoblacion.setVisible(true);
                txtAreaCuadrada.setVisible(true);
                txtPoblacion.setVisible(true);
            }
        }
    }

    @FXML
    private void btnVolverAction(ActionEvent event) 
    {
         FlowController.getInstance().goView("CensoPoblacional");
    }
    
}
