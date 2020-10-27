package org.una.examenfinalcliente;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.una.examenfinalcliente.utility.FlowController;


/**
 * JavaFX App
 */
public class App extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        FlowController.getInstance().InitializeFlow(stage, null);
    //    stage.getIcons().add(new Image("org/una/examenfinalcliente/resources/Icono.png"));
        stage.setTitle("ExamenFinal");
        FlowController.getInstance().goMain();
        FlowController.getInstance().goView("MenuGestion");
    } 

    public static void main(String[] args) {
        launch();
    }
}