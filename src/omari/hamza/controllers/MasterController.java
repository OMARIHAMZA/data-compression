package omari.hamza.controllers;

import javafx.fxml.Initializable;
import javafx.scene.Node;
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

    protected void setContentView(Node node) {
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

}
