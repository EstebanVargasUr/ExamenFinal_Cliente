package org.una.examenfinalcliente.controllersView;

import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.una.examenfinalcliente.DTOs.*;
import org.una.examenfinalcliente.WebService.*;
import org.una.examenfinalcliente.utility.FlowController;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CobrosController extends Controller implements Initializable {

    @FXML
    private VBox root;

    @FXML
    private JFXTextField txtFiltro;

    @FXML
    private HBox Htabla;

    @FXML
    private TreeTableView<Cobro> treeTableView;

    @FXML
    private TreeTableColumn<Cobro, String> colPrincipal;

    @FXML
    private TreeTableColumn<Cobro, String> colNombre;

    @FXML
    private TreeTableColumn<Cobro, String> colCedula;

    @FXML
    private TreeTableColumn<Cobro, String> colTel;

    @FXML
    private TreeTableColumn<Cobro, String> colTipo;

    @FXML
    private TreeTableColumn<Cobro, String> colMontoMembresia;

    @FXML
    private TreeTableColumn<Cobro, String> colPeriodo;

    @FXML
    private TreeTableColumn<Cobro, String> colFechaRegistro;

    @FXML
    private TreeTableColumn<Cobro, String> colMontoCobro;

    @FXML
    private TreeTableColumn<Cobro, String> colFechaVencimiento;

    @FXML
    private Button btnAtras;

    @FXML
    void devueltaOnAction(ActionEvent event) {

        FlowController.getInstance().goView("MenuGestion");

    }

    TreeItem<Cobro> CobroCliente = new TreeItem<>(new Cobro("Cliente","-","-","-","-","-","-","-","-","-"));
    TreeItem<Cobro> CobroMembresia = new TreeItem<>(new Cobro("Membresia","-","-","-","-","-","-","-","-","-"));
    TreeItem<Cobro> CobroPendiente = new TreeItem<>(new Cobro("Cobro","-","-","-","-","-","-","-","-","-"));
    TreeItem<Cobro> treeRoot = new TreeItem<>(new Cobro("Clientes","-","-","-","-","-","-","-","-","-"));

    TreeItem<Cobro> Cliente;
    TreeItem<Cobro> Membresia;
    TreeItem<Cobro> Cob;

    void CargarDatosIniciales() throws InterruptedException, ExecutionException, IOException
    {
        List<ClienteDTO> clientesModel = ClienteWebService.getAllClientes();
        for (int i = 0; i < clientesModel.toArray().length; i++)
        {
            Cliente = new TreeItem<>(new Cobro("Cliente",clientesModel.get(i).getNombreCompleto(),
                    clientesModel.get(i).getCedula(), clientesModel.get(i).getTelefono(),
                    "-","-","-","-","-","-"));

            List<ClienteTipoServicioDTO> clientesTipoModel = ClienteTipoServicioWebService.getAllClientesTiposServicios();
            List<MembresiaDTO> membresias = MembresiaWebService.getMembresiasByClienteId(clientesModel.get(i).getId());
            for(int j = 0; j < membresias.toArray().length; j++){
                Membresia = new TreeItem<>(new Cobro("Membresia","-","-","-",clientesTipoModel.get(j).getTipoServicio().getNombre(),membresias.get(j).getMonto()+"",membresias.get(j).getPeriodicidad()+"",
                        membresias.get(j).getFechaRegistro()+"","-","-"));

                List<CobroPendienteDTO> cobros = CobroPendienteWebService.getCobrosPendientesByMembresiaId(membresias.get(i).getId());
                for(int k = 0; k < cobros.toArray().length; k++) {
                    Cob = new TreeItem<>(new Cobro("Cobro", "-", "-", "-", "-", "-", "-","-", membresias.get(j).getMonto()/membresias.get(j).getPeriodicidad()+"",
                             cobros.get(k).getFechaVencimiento()+""));
                    Membresia.getChildren().add(Cob);
                }
                Cliente.getChildren().add(Membresia);
            }
            CobroCliente.getChildren().add(Cliente);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
        CargarDatosIniciales();
        colPrincipal.setCellValueFactory((TreeTableColumn.CellDataFeatures<Cobro, String> param) -> param.getValue().getValue().getAreaProperty());
        colNombre.setCellValueFactory((TreeTableColumn.CellDataFeatures<Cobro, String> param) -> param.getValue().getValue().getNombreProperty());
        colCedula.setCellValueFactory((TreeTableColumn.CellDataFeatures<Cobro, String> param) -> param.getValue().getValue().getCedulaProperty());
        colTel.setCellValueFactory((TreeTableColumn.CellDataFeatures<Cobro, String> param) -> param.getValue().getValue().getTelefonoProperty());
        colTipo.setCellValueFactory((TreeTableColumn.CellDataFeatures<Cobro, String> param) -> param.getValue().getValue().getTipoProperty());
        colMontoMembresia.setCellValueFactory((TreeTableColumn.CellDataFeatures<Cobro, String> param) -> param.getValue().getValue().getMontoMemProperty());
        colPeriodo.setCellValueFactory((TreeTableColumn.CellDataFeatures<Cobro, String> param) -> param.getValue().getValue().getPeriodoProperty());
        colFechaRegistro.setCellValueFactory((TreeTableColumn.CellDataFeatures<Cobro, String> param) -> param.getValue().getValue().getFechaRegistroProperty());
        colMontoCobro.setCellValueFactory((TreeTableColumn.CellDataFeatures<Cobro, String> param) -> param.getValue().getValue().getMontoCoProperty());
        colFechaVencimiento.setCellValueFactory((TreeTableColumn.CellDataFeatures<Cobro, String> param) -> param.getValue().getValue().getFechaVencimientoProperty());
        treeRoot.getChildren().setAll(CobroCliente);
        treeTableView.setRoot(CobroCliente);
        treeTableView.setShowRoot(false);
        } catch (InterruptedException | ExecutionException | IOException ex) { Logger.getLogger(CobrosController.class.getName()).log(Level.SEVERE, null, ex); }
    }

    @Override
    public void initialize() {

    }

    @Override
    public Node getRoot() {
        return root;
    }

    public class Cobro {

        private SimpleStringProperty areaProperty;
        private SimpleStringProperty nombreProperty;
        private SimpleStringProperty cedulaProperty;
        private SimpleStringProperty telefonoProperty;
        private SimpleStringProperty tipoProperty;
        private SimpleStringProperty montoMemProperty;
        private SimpleStringProperty periodoProperty;
        private SimpleStringProperty fechaRegistroProperty;
        private SimpleStringProperty montoCoProperty;
        private SimpleStringProperty fechaVencimientoProperty;

        public Cobro(String area, String nombre, String cedula, String telefono,
                      String tipo, String montoMem,String periodo, String fechaRegistro, String montoCo,String fechaVencimiento) {
            this.areaProperty = new SimpleStringProperty(area);
            this.nombreProperty = new SimpleStringProperty(nombre);
            this.cedulaProperty = new SimpleStringProperty(cedula);
            this.telefonoProperty = new SimpleStringProperty(telefono);
            this.tipoProperty = new SimpleStringProperty(tipo);
            this.montoMemProperty = new SimpleStringProperty(montoMem);
            this.periodoProperty= new SimpleStringProperty(periodo);
            this.fechaRegistroProperty = new SimpleStringProperty(fechaRegistro);
            this.montoCoProperty = new SimpleStringProperty(montoCo);
            this.fechaVencimientoProperty = new SimpleStringProperty(fechaVencimiento);
        }

        public SimpleStringProperty getAreaProperty() {
            return areaProperty;
        }

        public SimpleStringProperty getNombreProperty() {
            return nombreProperty;
        }

        public SimpleStringProperty getCedulaProperty() {
            return cedulaProperty;
        }

        public SimpleStringProperty getTelefonoProperty() {
            return telefonoProperty;
        }

        public SimpleStringProperty getTipoProperty() {
            return tipoProperty ;
        }

        public SimpleStringProperty getMontoMemProperty() {
            return montoMemProperty;
        }

        public SimpleStringProperty getPeriodoProperty() {
            return periodoProperty;
        }

        public SimpleStringProperty getFechaRegistroProperty() {
            return fechaRegistroProperty;
        }

        public SimpleStringProperty getMontoCoProperty() {
            return montoCoProperty;
        }

        public SimpleStringProperty getFechaVencimientoProperty() {
            return fechaVencimientoProperty;
        }

    }

}
