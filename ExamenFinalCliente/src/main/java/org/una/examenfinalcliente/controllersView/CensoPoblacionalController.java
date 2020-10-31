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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.una.examenfinalcliente.DTOs.CantonDTO;
import org.una.examenfinalcliente.DTOs.DistritoDTO;
import org.una.examenfinalcliente.DTOs.ProvinciaDTO;
import org.una.examenfinalcliente.DTOs.UnidadDTO;
import org.una.examenfinalcliente.WebService.ProvinciaWebService;
import org.una.examenfinalcliente.WebService.CantonWebService;
import org.una.examenfinalcliente.WebService.DistritoWebService;
import org.una.examenfinalcliente.WebService.UnidadWebService;
import org.una.examenfinalcliente.utility.FlowController;

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
    
    @FXML
    private ComboBox<String> cbFiltro;
    @FXML
    private ComboBox<String> cbOrdenar;
    @FXML
    private TextField txtFiltro;
    @FXML
    private Button btnFiltrar;
    @FXML
    private ComboBox<String> cbAreaFiltro;
    @FXML
    private Button btnCrear;
    
    private ProvinciaWebService provinciaWebService; 
    
    TreeItem<TipoArea> areaTipo = new TreeItem<>(new TipoArea("-> Tipo de área","-",0,0,0));
    TreeItem<TipoArea> treeRoot = new TreeItem<>(new TipoArea("Tipo de área","-",0,0,0));
    
    TreeItem<TipoArea> area;
    TreeItem<TipoArea> area01;
    TreeItem<TipoArea> area02;
    TreeItem<TipoArea> area03;
    
           
    void CargarDatosIniciales() throws InterruptedException, ExecutionException, IOException
    {
        int idCanton=1; int idDistrito=1; int idUnidad=1;

        List<ProvinciaDTO> provincias = ProvinciaWebService.getAllProvincias();
    
        
        for (int i = 0; i < provincias.toArray().length; i++)
        {
            area = new TreeItem<>(new TipoArea("-> Provincia",provincias.get(i).getNombreProvincia(),provincias.get(i).getCodigoProvincia(),ProvinciaWebService.getSumaCantidadPoblacionByProvinciaId(i+1),ProvinciaWebService.getSumaAreaCuadradaByProvinciaId(i+1)));
            areaTipo.getChildren().add(area);
  
           List<CantonDTO> cantones = ProvinciaWebService.getCantonByProvinciaId(i+1);
           
            for (int j = 0; j < cantones.toArray().length; j++) 
            {
                area01 = new TreeItem<>(new TipoArea("-> Canton",cantones.get(j).getNombreCanton(),cantones.get(j).getCodigoCanton(),CantonWebService.getSumaCantidadPoblacionByCantonId(idCanton),CantonWebService.getSumaAreaCuadradaByCantonId(idCanton)));
                area.getChildren().add(area01);
                idCanton = idCanton+1;
                
                List<DistritoDTO> distritos = CantonWebService.getDistritoByCantonId(idCanton-1);
                for (int k = 0; k < distritos.toArray().length; k++) {
                    area02 = new TreeItem<>(new TipoArea("-> Distrito",distritos.get(k).getNombreDistrito(),distritos.get(k).getCodigoDistrito(),DistritoWebService.getSumaCantidadPoblacionByDistritoId(idDistrito),DistritoWebService.getSumaAreaCuadradaByDistritoId(idDistrito)));
                    area01.getChildren().add(area02);
                    idDistrito = idDistrito+1;
                    
                    List<UnidadDTO> unidades = DistritoWebService.getUnidadByDistritoId(idDistrito-1);
                    for (int l = 0; l < unidades.toArray().length; l++) 
                    {
                        area03 = new TreeItem<>(new TipoArea("-> Unidad",unidades.get(l).getNombreUnidad(),unidades.get(l).getCodigoUnidad(),UnidadWebService.getSumaCantidadPoblacionByUnidadId(idUnidad),UnidadWebService.getSumaAreaCuadradaByUnidadId(idUnidad)));
                        area02.getChildren().add(area03);
                        idUnidad = idUnidad+1;
                    }
                }
            }
        }
                
            Col1.setCellValueFactory((TreeTableColumn.CellDataFeatures<TipoArea, String> param) -> param.getValue().getValue().getNombreTipoAreaProperty());
            Col2.setCellValueFactory((TreeTableColumn.CellDataFeatures<TipoArea, String> param) -> param.getValue().getValue().getNombreProperty());
            Col3.setCellValueFactory((TreeTableColumn.CellDataFeatures<TipoArea, Number> param) -> param.getValue().getValue().getCodigoProperty());
            Col4.setCellValueFactory((TreeTableColumn.CellDataFeatures<TipoArea, Number> param) -> param.getValue().getValue().getPoblacionProperty());
            Col5.setCellValueFactory((TreeTableColumn.CellDataFeatures<TipoArea, Number> param) -> param.getValue().getValue().getAreaCuadradaProperty());

            treeRoot.getChildren().setAll(areaTipo);

            TableView1.setRoot(treeRoot);
            TableView1.setShowRoot(false);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        cbFiltro.getItems().add("Id");
        cbFiltro.getItems().add("Nombre");
        cbFiltro.getItems().add("Codigo");
        cbFiltro.getItems().add("Todos");
        
        cbAreaFiltro.getItems().add("Provincia");
        cbAreaFiltro.getItems().add("Canton");
        cbAreaFiltro.getItems().add("Distrito");
        cbAreaFiltro.getItems().add("Unidad");
        
        cbOrdenar.getItems().add("Población");
        cbOrdenar.getItems().add("Area en metros cuadrados");

        try {CargarDatosIniciales();} 
        catch (InterruptedException | ExecutionException | IOException ex) {Logger.getLogger(CensoPoblacionalController.class.getName()).log(Level.SEVERE, null, ex);}
    }  
    
    @Override
    public void initialize() {
        
    }
    
    @Override
    public Node getRoot() {
        return root;
    }

    @FXML
    private void btnFiltroClick(MouseEvent event) 
    {
        if (cbFiltro.getValue().equals("Todos")) 
        {
            TableView1.getRoot().getChildren().clear();
            areaTipo.getChildren().clear();
            try {CargarDatosIniciales();} 
            catch (InterruptedException | ExecutionException | IOException ex) {Logger.getLogger(CensoPoblacionalController.class.getName()).log(Level.SEVERE, null, ex);}
        }
        
        if (cbFiltro.getValue().equals("Id")) 
        {
            TableView1.getRoot().getChildren().clear();
            areaTipo.getChildren().clear();
            try {FiltraPorId();} 
            catch (InterruptedException | ExecutionException | IOException ex) {Logger.getLogger(CensoPoblacionalController.class.getName()).log(Level.SEVERE, null, ex);}
        }
        
        if (cbFiltro.getValue().equals("Nombre")) 
        {
            TableView1.getRoot().getChildren().clear();
            areaTipo.getChildren().clear();
            try {FiltraPorNombre();} 
            catch (InterruptedException | ExecutionException | IOException ex) {Logger.getLogger(CensoPoblacionalController.class.getName()).log(Level.SEVERE, null, ex);}
        }
        
        if (cbFiltro.getValue().equals("Codigo")) 
        {
            TableView1.getRoot().getChildren().clear();
            areaTipo.getChildren().clear();
            try {FiltraPorCodigo();} 
            catch (InterruptedException | ExecutionException | IOException ex) {Logger.getLogger(CensoPoblacionalController.class.getName()).log(Level.SEVERE, null, ex);}
        }
    } 
            
    void FiltraPorNombre() throws InterruptedException, ExecutionException, IOException
    {
        int idCanton=1; int idDistrito=1; int idUnidad=1;
        if (cbAreaFiltro.getValue().equals("Provincia"))
        {
        List<ProvinciaDTO> provincias = ProvinciaWebService.getProvinciaByNombreProvincia(txtFiltro.getText());
    
        for (int i = 0; i < provincias.toArray().length; i++)
        {
        
            area = new TreeItem<>(new TipoArea("-> Provincia",provincias.get(i).getNombreProvincia(),provincias.get(i).getCodigoProvincia(),ProvinciaWebService.getSumaCantidadPoblacionByProvinciaId(i+1),ProvinciaWebService.getSumaAreaCuadradaByProvinciaId(i+1)));
            areaTipo.getChildren().add(area);
  
           List<CantonDTO> cantones = ProvinciaWebService.getCantonByProvinciaId(i+1);
           
            for (int j = 0; j < cantones.toArray().length; j++) 
            {
                area01 = new TreeItem<>(new TipoArea("-> Canton",cantones.get(j).getNombreCanton(),cantones.get(j).getCodigoCanton(),CantonWebService.getSumaCantidadPoblacionByCantonId(idCanton),CantonWebService.getSumaAreaCuadradaByCantonId(idCanton)));
                area.getChildren().add(area01);
                idCanton = idCanton+1;
                
                List<DistritoDTO> distritos = CantonWebService.getDistritoByCantonId(idCanton-1);
                for (int k = 0; k < distritos.toArray().length; k++) {
                    area02 = new TreeItem<>(new TipoArea("-> Distrito",distritos.get(k).getNombreDistrito(),distritos.get(k).getCodigoDistrito(),DistritoWebService.getSumaCantidadPoblacionByDistritoId(idDistrito),DistritoWebService.getSumaAreaCuadradaByDistritoId(idDistrito)));
                    area01.getChildren().add(area02);
                    idDistrito = idDistrito+1;
                    
                    List<UnidadDTO> unidades = DistritoWebService.getUnidadByDistritoId(idDistrito-1);
                    for (int l = 0; l < unidades.toArray().length; l++) 
                    {
                        area03 = new TreeItem<>(new TipoArea("-> Unidad",unidades.get(l).getNombreUnidad(),unidades.get(l).getCodigoUnidad(),UnidadWebService.getSumaCantidadPoblacionByUnidadId(idUnidad),UnidadWebService.getSumaAreaCuadradaByUnidadId(idUnidad)));
                        area02.getChildren().add(area03);
                        idUnidad = idUnidad+1;
                    }
                }
            }
        }
      }          
        if (cbAreaFiltro.getValue().equals("Canton"))
        {

           List<CantonDTO> cantones = CantonWebService.getCantonByNombreCanton(txtFiltro.getText());
           
            for (int j = 0; j < cantones.toArray().length; j++) 
            {
                area01 = new TreeItem<>(new TipoArea("-> Canton",cantones.get(j).getNombreCanton(),cantones.get(j).getCodigoCanton(),CantonWebService.getSumaCantidadPoblacionByCantonId(idCanton),CantonWebService.getSumaAreaCuadradaByCantonId(idCanton)));
                areaTipo.getChildren().add(area01);
                idCanton = idCanton+1;
                
                List<DistritoDTO> distritos = CantonWebService.getDistritoByCantonId(idCanton-1);
                for (int k = 0; k < distritos.toArray().length; k++) {
                    area02 = new TreeItem<>(new TipoArea("-> Distrito",distritos.get(k).getNombreDistrito(),distritos.get(k).getCodigoDistrito(),DistritoWebService.getSumaCantidadPoblacionByDistritoId(idDistrito),DistritoWebService.getSumaAreaCuadradaByDistritoId(idDistrito)));
                    area01.getChildren().add(area02);
                    idDistrito = idDistrito+1;
                    
                    List<UnidadDTO> unidades = DistritoWebService.getUnidadByDistritoId(idDistrito-1);
                    for (int l = 0; l < unidades.toArray().length; l++) 
                    {
                        area03 = new TreeItem<>(new TipoArea("-> Unidad",unidades.get(l).getNombreUnidad(),unidades.get(l).getCodigoUnidad(),UnidadWebService.getSumaCantidadPoblacionByUnidadId(idUnidad),UnidadWebService.getSumaAreaCuadradaByUnidadId(idUnidad)));
                        area02.getChildren().add(area03);
                        idUnidad = idUnidad+1;
                    }
                }
            }
        }
        
        if (cbAreaFiltro.getValue().equals("Distrito"))
        {

                List<DistritoDTO> distritos = DistritoWebService.getDistritoByNombreDistrito(txtFiltro.getText());
                for (int k = 0; k < distritos.toArray().length; k++) {
                    area02 = new TreeItem<>(new TipoArea("-> Distrito",distritos.get(k).getNombreDistrito(),distritos.get(k).getCodigoDistrito(),DistritoWebService.getSumaCantidadPoblacionByDistritoId(idDistrito),DistritoWebService.getSumaAreaCuadradaByDistritoId(idDistrito)));
                    areaTipo.getChildren().add(area02);
                    idDistrito = idDistrito+1;
                    
                    List<UnidadDTO> unidades = DistritoWebService.getUnidadByDistritoId(idDistrito-1);
                    for (int l = 0; l < unidades.toArray().length; l++) 
                    {
                        area03 = new TreeItem<>(new TipoArea("-> Unidad",unidades.get(l).getNombreUnidad(),unidades.get(l).getCodigoUnidad(),UnidadWebService.getSumaCantidadPoblacionByUnidadId(idUnidad),UnidadWebService.getSumaAreaCuadradaByUnidadId(idUnidad)));
                        area02.getChildren().add(area03);
                        idUnidad = idUnidad+1;
                    }
                }
            }
        if (cbAreaFiltro.getValue().equals("Unidad"))
        {
            List<UnidadDTO> unidades = UnidadWebService.getUnidadByNombreUnidad(txtFiltro.getText());
            for (int l = 0; l < unidades.toArray().length; l++) 
            {
                area03 = new TreeItem<>(new TipoArea("-> Unidad",unidades.get(l).getNombreUnidad(),unidades.get(l).getCodigoUnidad(),UnidadWebService.getSumaCantidadPoblacionByUnidadId(idUnidad),UnidadWebService.getSumaAreaCuadradaByUnidadId(idUnidad)));
                areaTipo.getChildren().add(area03);
                idUnidad = idUnidad+1;
            }
        }
            
        
                
            Col1.setCellValueFactory((TreeTableColumn.CellDataFeatures<TipoArea, String> param) -> param.getValue().getValue().getNombreTipoAreaProperty());
            Col2.setCellValueFactory((TreeTableColumn.CellDataFeatures<TipoArea, String> param) -> param.getValue().getValue().getNombreProperty());
            Col3.setCellValueFactory((TreeTableColumn.CellDataFeatures<TipoArea, Number> param) -> param.getValue().getValue().getCodigoProperty());
            Col4.setCellValueFactory((TreeTableColumn.CellDataFeatures<TipoArea, Number> param) -> param.getValue().getValue().getPoblacionProperty());
            Col5.setCellValueFactory((TreeTableColumn.CellDataFeatures<TipoArea, Number> param) -> param.getValue().getValue().getAreaCuadradaProperty());

            treeRoot.getChildren().setAll(areaTipo);

            TableView1.setRoot(treeRoot);
            TableView1.setShowRoot(false);
    }     
    
    void FiltraPorId() throws InterruptedException, ExecutionException, IOException
    {
        int idCanton=1; int idDistrito=1; int idUnidad=1;
        long num = Long.parseLong(txtFiltro.getText());
        
         if (cbAreaFiltro.getValue().equals("Provincia"))
         {
            ProvinciaDTO provincias = ProvinciaWebService.getProvinciaById(num);
 
            area = new TreeItem<>(new TipoArea("-> Provincia",provincias.getNombreProvincia(),provincias.getCodigoProvincia(),ProvinciaWebService.getSumaCantidadPoblacionByProvinciaId(num),ProvinciaWebService.getSumaAreaCuadradaByProvinciaId(num)));
            areaTipo.getChildren().add(area);
  
           List<CantonDTO> cantones = ProvinciaWebService.getCantonByProvinciaId(num);
           
            for (int j = 0; j < cantones.toArray().length; j++) 
            {
                area01 = new TreeItem<>(new TipoArea("-> Canton",cantones.get(j).getNombreCanton(),cantones.get(j).getCodigoCanton(),CantonWebService.getSumaCantidadPoblacionByCantonId(idCanton),CantonWebService.getSumaAreaCuadradaByCantonId(idCanton)));
                area.getChildren().add(area01);
                idCanton = idCanton+1;
                
                List<DistritoDTO> distritos = CantonWebService.getDistritoByCantonId(idCanton-1);
                for (int k = 0; k < distritos.toArray().length; k++) {
                    area02 = new TreeItem<>(new TipoArea("-> Distrito",distritos.get(k).getNombreDistrito(),distritos.get(k).getCodigoDistrito(),DistritoWebService.getSumaCantidadPoblacionByDistritoId(idDistrito),DistritoWebService.getSumaAreaCuadradaByDistritoId(idDistrito)));
                    area01.getChildren().add(area02);
                    idDistrito = idDistrito+1;
                    
                    List<UnidadDTO> unidades = DistritoWebService.getUnidadByDistritoId(idDistrito-1);
                    for (int l = 0; l < unidades.toArray().length; l++) 
                    {
                        area03 = new TreeItem<>(new TipoArea("-> Unidad",unidades.get(l).getNombreUnidad(),unidades.get(l).getCodigoUnidad(),UnidadWebService.getSumaCantidadPoblacionByUnidadId(idUnidad),UnidadWebService.getSumaAreaCuadradaByUnidadId(idUnidad)));
                        area02.getChildren().add(area03);
                        idUnidad = idUnidad+1;
                    }
                }   
            } 
          }
         
         if (cbAreaFiltro.getValue().equals("Canton"))
         {
            CantonDTO cantones = CantonWebService.getCantonById(num);
            
            area01 = new TreeItem<>(new TipoArea("-> Canton",cantones.getNombreCanton(),cantones.getCodigoCanton(),CantonWebService.getSumaCantidadPoblacionByCantonId(num),CantonWebService.getSumaAreaCuadradaByCantonId(num)));
            areaTipo.getChildren().add(area01);
            idCanton = idCanton+1;

            List<DistritoDTO> distritos = CantonWebService.getDistritoByCantonId(idCanton-1);
            for (int k = 0; k < distritos.toArray().length; k++) {
                area02 = new TreeItem<>(new TipoArea("-> Distrito",distritos.get(k).getNombreDistrito(),distritos.get(k).getCodigoDistrito(),DistritoWebService.getSumaCantidadPoblacionByDistritoId(idDistrito),DistritoWebService.getSumaAreaCuadradaByDistritoId(idDistrito)));
                area01.getChildren().add(area02);
                idDistrito = idDistrito+1;

                List<UnidadDTO> unidades = DistritoWebService.getUnidadByDistritoId(idDistrito-1);
                for (int l = 0; l < unidades.toArray().length; l++) 
                {
                    area03 = new TreeItem<>(new TipoArea("-> Unidad",unidades.get(l).getNombreUnidad(),unidades.get(l).getCodigoUnidad(),UnidadWebService.getSumaCantidadPoblacionByUnidadId(idUnidad),UnidadWebService.getSumaAreaCuadradaByUnidadId(idUnidad)));
                    area02.getChildren().add(area03);
                    idUnidad = idUnidad+1;
                } 
            } 
          }
           
         if (cbAreaFiltro.getValue().equals("Distrito"))
         {
            DistritoDTO distritos = DistritoWebService.getDistritoById(num);

            area02 = new TreeItem<>(new TipoArea("-> Distrito",distritos.getNombreDistrito(),distritos.getCodigoDistrito(),DistritoWebService.getSumaCantidadPoblacionByDistritoId(num),DistritoWebService.getSumaAreaCuadradaByDistritoId(num)));
            areaTipo.getChildren().add(area02);
            idDistrito = idDistrito+1;

            List<UnidadDTO> unidades = DistritoWebService.getUnidadByDistritoId(idDistrito-1);
            for (int l = 0; l < unidades.toArray().length; l++) 
            {
                area03 = new TreeItem<>(new TipoArea("-> Unidad",unidades.get(l).getNombreUnidad(),unidades.get(l).getCodigoUnidad(),UnidadWebService.getSumaCantidadPoblacionByUnidadId(idUnidad),UnidadWebService.getSumaAreaCuadradaByUnidadId(idUnidad)));
                area02.getChildren().add(area03);
                idUnidad = idUnidad+1;
            }
          }
          
        if (cbAreaFiltro.getValue().equals("Unidad"))
        {
           UnidadDTO unidades = UnidadWebService.getUnidadById(num);

           area03 = new TreeItem<>(new TipoArea("-> Unidad",unidades.getNombreUnidad(),unidades.getCodigoUnidad(),UnidadWebService.getSumaCantidadPoblacionByUnidadId(num),UnidadWebService.getSumaAreaCuadradaByUnidadId(num)));
           areaTipo.getChildren().add(area03);
           idUnidad = idUnidad+1;
         }
         
        Col1.setCellValueFactory((TreeTableColumn.CellDataFeatures<TipoArea, String> param) -> param.getValue().getValue().getNombreTipoAreaProperty());
        Col2.setCellValueFactory((TreeTableColumn.CellDataFeatures<TipoArea, String> param) -> param.getValue().getValue().getNombreProperty());
        Col3.setCellValueFactory((TreeTableColumn.CellDataFeatures<TipoArea, Number> param) -> param.getValue().getValue().getCodigoProperty());
        Col4.setCellValueFactory((TreeTableColumn.CellDataFeatures<TipoArea, Number> param) -> param.getValue().getValue().getPoblacionProperty());
        Col5.setCellValueFactory((TreeTableColumn.CellDataFeatures<TipoArea, Number> param) -> param.getValue().getValue().getAreaCuadradaProperty());

        treeRoot.getChildren().setAll(areaTipo);

        TableView1.setRoot(treeRoot);
        TableView1.setShowRoot(false);
    }
    
     void FiltraPorCodigo() throws InterruptedException, ExecutionException, IOException
    {
        int idCanton=1; int idDistrito=1; int idUnidad=1;
        Integer num = Integer.parseInt(txtFiltro.getText());
        
         if (cbAreaFiltro.getValue().equals("Provincia"))
         {
            ProvinciaDTO provincias = ProvinciaWebService.getProvinciaByCodigoProvincia(num);
 
            area = new TreeItem<>(new TipoArea("-> Provincia",provincias.getNombreProvincia(),provincias.getCodigoProvincia(),ProvinciaWebService.getSumaCantidadPoblacionByProvinciaId(num),ProvinciaWebService.getSumaAreaCuadradaByProvinciaId(num)));
            areaTipo.getChildren().add(area);
  
           List<CantonDTO> cantones = ProvinciaWebService.getCantonByProvinciaId(num);
           
            for (int j = 0; j < cantones.toArray().length; j++) 
            {
                area01 = new TreeItem<>(new TipoArea("-> Canton",cantones.get(j).getNombreCanton(),cantones.get(j).getCodigoCanton(),CantonWebService.getSumaCantidadPoblacionByCantonId(idCanton),CantonWebService.getSumaAreaCuadradaByCantonId(idCanton)));
                area.getChildren().add(area01);
                idCanton = idCanton+1;
                
                List<DistritoDTO> distritos = CantonWebService.getDistritoByCantonId(idCanton-1);
                for (int k = 0; k < distritos.toArray().length; k++) {
                    area02 = new TreeItem<>(new TipoArea("-> Distrito",distritos.get(k).getNombreDistrito(),distritos.get(k).getCodigoDistrito(),DistritoWebService.getSumaCantidadPoblacionByDistritoId(idDistrito),DistritoWebService.getSumaAreaCuadradaByDistritoId(idDistrito)));
                    area01.getChildren().add(area02);
                    idDistrito = idDistrito+1;
                    
                    List<UnidadDTO> unidades = DistritoWebService.getUnidadByDistritoId(idDistrito-1);
                    for (int l = 0; l < unidades.toArray().length; l++) 
                    {
                        area03 = new TreeItem<>(new TipoArea("-> Unidad",unidades.get(l).getNombreUnidad(),unidades.get(l).getCodigoUnidad(),UnidadWebService.getSumaCantidadPoblacionByUnidadId(idUnidad),UnidadWebService.getSumaAreaCuadradaByUnidadId(idUnidad)));
                        area02.getChildren().add(area03);
                        idUnidad = idUnidad+1;
                    }
                }   
            } 
          }
         
         if (cbAreaFiltro.getValue().equals("Canton"))
         {
            CantonDTO cantones = CantonWebService.getCantonByCodigoCanton(num);
            
            area01 = new TreeItem<>(new TipoArea("-> Canton",cantones.getNombreCanton(),cantones.getCodigoCanton(),CantonWebService.getSumaCantidadPoblacionByCantonId(num),CantonWebService.getSumaAreaCuadradaByCantonId(num)));
            areaTipo.getChildren().add(area01);
            idCanton = idCanton+1;

            List<DistritoDTO> distritos = CantonWebService.getDistritoByCantonId(idCanton-1);
            for (int k = 0; k < distritos.toArray().length; k++) {
                area02 = new TreeItem<>(new TipoArea("-> Distrito",distritos.get(k).getNombreDistrito(),distritos.get(k).getCodigoDistrito(),DistritoWebService.getSumaCantidadPoblacionByDistritoId(idDistrito),DistritoWebService.getSumaAreaCuadradaByDistritoId(idDistrito)));
                area01.getChildren().add(area02);
                idDistrito = idDistrito+1;

                List<UnidadDTO> unidades = DistritoWebService.getUnidadByDistritoId(idDistrito-1);
                for (int l = 0; l < unidades.toArray().length; l++) 
                {
                    area03 = new TreeItem<>(new TipoArea("-> Unidad",unidades.get(l).getNombreUnidad(),unidades.get(l).getCodigoUnidad(),UnidadWebService.getSumaCantidadPoblacionByUnidadId(idUnidad),UnidadWebService.getSumaAreaCuadradaByUnidadId(idUnidad)));
                    area02.getChildren().add(area03);
                    idUnidad = idUnidad+1;
                } 
            } 
          }
           
         if (cbAreaFiltro.getValue().equals("Distrito"))
         {
            DistritoDTO distritos = DistritoWebService.getDistritoByCodigoDistrito(num);

            area02 = new TreeItem<>(new TipoArea("-> Distrito",distritos.getNombreDistrito(),distritos.getCodigoDistrito(),DistritoWebService.getSumaCantidadPoblacionByDistritoId(num),DistritoWebService.getSumaAreaCuadradaByDistritoId(num)));
            areaTipo.getChildren().add(area02);
            idDistrito = idDistrito+1;

            List<UnidadDTO> unidades = DistritoWebService.getUnidadByDistritoId(idDistrito-1);
            for (int l = 0; l < unidades.toArray().length; l++) 
            {
                area03 = new TreeItem<>(new TipoArea("-> Unidad",unidades.get(l).getNombreUnidad(),unidades.get(l).getCodigoUnidad(),UnidadWebService.getSumaCantidadPoblacionByUnidadId(idUnidad),UnidadWebService.getSumaAreaCuadradaByUnidadId(idUnidad)));
                area02.getChildren().add(area03);
                idUnidad = idUnidad+1;
            }
          }
          
        if (cbAreaFiltro.getValue().equals("Unidad"))
        {
           UnidadDTO unidades = UnidadWebService.getUnidadByCodigoUnidad(num);

           area03 = new TreeItem<>(new TipoArea("-> Unidad",unidades.getNombreUnidad(),unidades.getCodigoUnidad(),UnidadWebService.getSumaCantidadPoblacionByUnidadId(num),UnidadWebService.getSumaAreaCuadradaByUnidadId(num)));
           areaTipo.getChildren().add(area03);
           idUnidad = idUnidad+1;
         }
         
        Col1.setCellValueFactory((TreeTableColumn.CellDataFeatures<TipoArea, String> param) -> param.getValue().getValue().getNombreTipoAreaProperty());
        Col2.setCellValueFactory((TreeTableColumn.CellDataFeatures<TipoArea, String> param) -> param.getValue().getValue().getNombreProperty());
        Col3.setCellValueFactory((TreeTableColumn.CellDataFeatures<TipoArea, Number> param) -> param.getValue().getValue().getCodigoProperty());
        Col4.setCellValueFactory((TreeTableColumn.CellDataFeatures<TipoArea, Number> param) -> param.getValue().getValue().getPoblacionProperty());
        Col5.setCellValueFactory((TreeTableColumn.CellDataFeatures<TipoArea, Number> param) -> param.getValue().getValue().getAreaCuadradaProperty());

        treeRoot.getChildren().setAll(areaTipo);

        TableView1.setRoot(treeRoot);
        TableView1.setShowRoot(false);
    }

    @FXML
    private void btnCrearAction(MouseEvent event) 
    {
         FlowController.getInstance().goView("CreacionPoblacional");
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

