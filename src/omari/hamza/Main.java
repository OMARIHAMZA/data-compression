package omari.hamza;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public final static String APPLICATION_NAME = "L O S S";
    public final static String COMPRESSION_WINDOW_PATH = "views/compression_window.fxml";
    public final static String FILE_CHOOSER_WINDOW_PATH = "views/file_chooser_window.fxml";

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource(FILE_CHOOSER_WINDOW_PATH));
        primaryStage.setTitle(APPLICATION_NAME);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
