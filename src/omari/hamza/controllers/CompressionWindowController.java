package omari.hamza.controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSpinner;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import omari.hamza.utils.compression.CompressionUtils;

import java.io.File;
import java.io.IOException;

import static omari.hamza.Main.APPLICATION_NAME;
import static omari.hamza.utils.compression.CompressionUtils.CompressionAlgorithms;

public class CompressionWindowController extends MasterController {

    @FXML
    private VBox inputLayout;

    @FXML
    private TextField fileNameTextField;

    @FXML
    private JFXComboBox<String> algorithmComboBox;

    @FXML
    private JFXSpinner progressBar;


    private String filePath;

    @Override
    protected void onCreate() {
        algorithmComboBox.getItems().addAll(CompressionAlgorithms.RLE.toString(),
                CompressionAlgorithms.HUFFMAN.toString(),
                CompressionAlgorithms.ADAPTIVE_HUFFMAN.toString(),
                CompressionAlgorithms.LZ77.toString(),
                CompressionAlgorithms.ARITHMETIC_CODING.toString(),
                CompressionAlgorithms.ADAPTIVE_ARITHMETIC_CODING.toString());
    }


    @FXML
    void actionCompress() {

        inputLayout.setVisible(false);
        progressBar.setVisible(true);

        Thread compressionThread = createThread(new Task<>() {
            @Override
            protected Void call() {
                try {
                    CompressionUtils.compress(filePath,
                            new File(filePath).getParent() + "/" + fileNameTextField.getText(),
                            algorithmComboBox.getSelectionModel().getSelectedItem());

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });

        compressionThread.start();

    }

    @FXML
    void actionDecompress() {

        inputLayout.setVisible(false);
        progressBar.setVisible(true);


        Thread decompressionThread = createThread(new Task<>() {
            @Override
            protected Void call() {
                try {
                    CompressionUtils.decompress(filePath,
                            new File(filePath).getParent() + "/" + fileNameTextField.getText(),
                            algorithmComboBox.getSelectionModel().getSelectedItem());

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });

        decompressionThread.start();

    }

    private Thread createThread(Task<Void> task) {
        task.setOnFailed(event -> {
            inputLayout.setVisible(true);
            progressBar.setVisible(false);
        });

        task.setOnSucceeded(event -> {
            inputLayout.setVisible(true);
            progressBar.setVisible(false);
        });

        return new Thread(task);
    }

    @FXML
    void actionReturn() {
        Parent root;
        Stage stage;
        //noinspection all
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/file_chooser_window.fxml"));
            root = fxmlLoader.load();
            ((Stage) fileNameTextField.getScene().getWindow()).close();
            stage = new Stage();
            stage.setTitle(APPLICATION_NAME);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void setFilePath(String filePath) {
        this.filePath = filePath;
        fileNameTextField.setText(new File(filePath).getName());
    }
}
