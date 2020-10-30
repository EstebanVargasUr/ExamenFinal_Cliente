package org.una.examenfinalcliente.controllersView;

import com.fasterxml.jackson.databind.JsonMappingException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.VBox;
import org.una.examenfinalcliente.DTOs.ProvinciaDTO;
import org.una.examenfinalcliente.WebService.ProvinciaWebService;

/**
 * FXML Controller class
 *
 * @author Adrian
 */
public class CensoPoblacionalController extends Controller implements Initializable{
    
    @FXML
    private VBox root;
    @FXML
    private TreeTableView<TipoArea> TableView1;
    @FXML
    private TreeTableColumn<TipoArea, String> Col1;
    @FXML
    private TreeTableColumn<TipoArea, String> Col2;
    @FXML
    private TreeTableColumn<TipoArea, Number> Col3;
    @FXML
    private TreeTableColumn<TipoArea, Number> Col4;
    @FXML
    private TreeTableColumn<TipoArea, Number> Col5;

    private ProvinciaWebService provinciaWebService; 
    
    TreeItem<TipoArea> areaProvincia = new TreeItem<>(new TipoArea("Provincia","-",0,0,0));
    TreeItem<TipoArea> areaCanton = new TreeItem<>(new TipoArea("Canton","-",0,0,0));
    TreeItem<TipoArea> areaDistrito = new TreeItem<>(new TipoArea("Distrito","-",0,0,0));
    TreeItem<TipoArea> areaUnidad = new TreeItem<>(new TipoArea("Unidad","-",0,0,0));
    
    
    TreeItem<TipoArea> area1 = new TreeItem<>(new TipoArea("Provincia","SJ",100,100,100));
    TreeItem<TipoArea> area2 = new TreeItem<>(new TipoArea("Provincia","Heredia",200,200,1.00));
    TreeItem<TipoArea> treeRoot = new TreeItem<>(new TipoArea("Tipo de área","-",0,0,0));
    
    TreeItem<TipoArea> area;
           
    void CargarDatosIniciales() throws InterruptedException, ExecutionException, IOException
    {
        List<ProvinciaDTO> provincias = ProvinciaWebService.getAllProvincias();
        for (int i = 0; i < provincias.toArray().length; i++)
        {
            area = new TreeItem<>(new TipoArea(provincias.get(i).getNombreProvincia(),provincias.get(i).getNombreProvincia(),provincias.get(i).getCodigoProvincia(),ProvinciaWebService.getSumaCantidadPoblacionByProvinciaId(i+1),ProvinciaWebService.getSumaAreaCuadradaByProvinciaId(i+1)));
            areaProvincia.getChildren().add(area);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        try {

            CargarDatosIniciales();

            Col1.setCellValueFactory((TreeTableColumn.CellDataFeatures<TipoArea, String> param) -> param.getValue().getValue().getNombreTipoAreaProperty());
            Col2.setCellValueFactory((TreeTableColumn.CellDataFeatures<TipoArea, String> param) -> param.getValue().getValue().getNombreProperty());
            Col3.setCellValueFactory((TreeTableColumn.CellDataFeatures<TipoArea, Number> param) -> param.getValue().getValue().getCodigoProperty());
            Col4.setCellValueFactory((TreeTableColumn.CellDataFeatures<TipoArea, Number> param) -> param.getValue().getValue().getPoblacionProperty());
            Col5.setCellValueFactory((TreeTableColumn.CellDataFeatures<TipoArea, Number> param) -> param.getValue().getValue().getAreaCuadradaProperty());

            treeRoot.getChildren().setAll(areaProvincia,areaCanton,areaDistrito,areaUnidad);

            TableView1.setRoot(treeRoot);
            TableView1.setShowRoot(false);

        } catch (InterruptedException | ExecutionException | IOException ex) { Logger.getLogger(CensoPoblacionalController.class.getName()).log(Level.SEVERE, null, ex); }   
    }  
    
    @Override
    public void initialize() {
        
    }
    
    @Override
    public Node getRoot() {
        return root;
    }
    
    class TipoArea {

    SimpleStringProperty nombreTipoAreaProperty;
    SimpleStringProperty nombreProperty;
    SimpleIntegerProperty codigoProperty;
    SimpleLongProperty poblacionProperty;
    SimpleDoubleProperty areaCuadradaProperty;

    public TipoArea(String nombreTipoArea,String nombre, Integer codigo, long poblacion, double areaCuadrada) {
       
        this.nombreTipoAreaProperty = new SimpleStringProperty(nombreTipoArea);
        this.nombreProperty = new SimpleStringProperty(nombre);
        this.codigoProperty = new SimpleIntegerProperty(codigo);
        this.poblacionProperty = new SimpleLongProperty(poblacion);
        this.areaCuadradaProperty = new SimpleDoubleProperty(areaCuadrada);
    }

    public SimpleStringProperty getNombreTipoAreaProperty() {
        return nombreTipoAreaProperty;
    }
    
    public SimpleStringProperty getNombreProperty() {
        return nombreProperty;
    }

    public SimpleIntegerProperty getCodigoProperty() {
        return codigoProperty;
    }

    public SimpleLongProperty getPoblacionProperty() {
        return poblacionProperty;
    }

    public SimpleDoubleProperty getAreaCuadradaProperty() {
        return areaCuadradaProperty;
    }     
  }
}

