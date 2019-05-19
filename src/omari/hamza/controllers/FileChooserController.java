package omari.hamza.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

import static omari.hamza.Main.APPLICATION_NAME;
import static omari.hamza.Main.COMPRESSION_WINDOW_PATH;

public class FileChooserController extends MasterController {

    @FXML
    private AnchorPane rootView;

    @FXML
    private ImageView dropImageView;

    @FXML
    private JFXButton browseButton;

    @Override
    protected void onCreate() {
        setContentView(rootView);
        configureDragAndDrop();
    }

    /**
     * Method for enabling Drag and Drop feature
     */

    private void configureDragAndDrop() {
        dropImageView.setOnDragOver(event -> {
            if (event.getGestureSource() != dropImageView
                    && event.getDragboard().hasFiles()) {
                /* allow for both copying and moving, whatever user chooses */
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });

        dropImageView.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                launchNextScene(db.getFiles().get(0).toString());
                success = true;
            }
            /* let the source know whether the string was successfully
             * transferred and used */
            event.setDropCompleted(success);
            event.consume();
        });
    }

    @FXML
    void browseFiles(ActionEvent event) {
        launchNextScene(chooseFile().getPath());
    }

    /**
     * Method for launching CompressionWindow after the user has selected the required files
     *
     * @param filePath: Path of the file/folder to compress/decompress
     */
    private void launchNextScene(String filePath) {
        Stage primaryStage = ((Stage) rootView.getScene().getWindow());
        try {
            Parent root = FXMLLoader.load(getClass().getResource(COMPRESSION_WINDOW_PATH));
            primaryStage.setTitle(APPLICATION_NAME);
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
