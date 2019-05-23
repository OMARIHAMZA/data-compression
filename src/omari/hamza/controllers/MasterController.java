package omari.hamza.controllers;

import com.jfoenix.controls.JFXDialog;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public abstract class MasterController implements Initializable {

    private Node rootView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        onCreate();
    }

    void setContentView(Node node) {
        this.rootView = node;
    }

    protected abstract void onCreate();


    File chooseFile() {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extensionFilter =
                new FileChooser.ExtensionFilter("File", "*.*");

        fileChooser.getExtensionFilters().add(extensionFilter);

        return fileChooser.showOpenDialog(rootView.getScene().getWindow());
    }

    protected void createDialog(String title, String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
