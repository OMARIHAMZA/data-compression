package omari.hamza.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import omari.hamza.utils.compression.CompressionUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static omari.hamza.Main.APPLICATION_NAME;
import static omari.hamza.utils.compression.CompressionUtils.CompressionAlgorithms;

public class CompressionWindowController extends MasterController {

    @FXML
    private TextField fileNameTextField;

    @FXML
    private JFXComboBox<String> algorithmComboBox;

    @FXML
    private JFXButton decompressButton;

    @FXML
    private JFXButton compressButton;


    private String filePath;

    @Override
    protected void onCreate() {
        algorithmComboBox.getItems().addAll(CompressionAlgorithms.RLE.toString(),
                CompressionAlgorithms.HUFFMAN.toString(),
                CompressionAlgorithms.ADAPTIVE_HUFFMAN.toString());
    }


    @FXML
    void actionCompress() {
        try {
            CompressionUtils.compress(filePath,
                    new File(filePath).getParent() + "/" + fileNameTextField.getText(),
                    algorithmComboBox.getSelectionModel().getSelectedItem());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void actionDecompress() {
        try {
            CompressionUtils.decompress(filePath,
                    new File(filePath).getParent() + "/" + fileNameTextField.getText(),
                    algorithmComboBox.getSelectionModel().getSelectedItem());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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
