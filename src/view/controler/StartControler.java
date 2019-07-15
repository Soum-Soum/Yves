package view.controler;

import data.DATACONTAINER;
import file.FileManager;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;


public class StartControler {
    public Scene nextScene;

    @FXML
    Button validate, next, import_data;
    @FXML
    TextField montantWidth, montantDist, traverseWidth;

    public void init(){
        next.setDisable(true);
        next.setOnAction(event -> {
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(nextScene);
        });
        validate.setOnAction(event -> {
            if (montantDist.getText()!= "" && montantWidth.getText() != "" && traverseWidth.getText() != ""){{
                DATACONTAINER.SETDATACONTAINERVALUES(Double.parseDouble(montantWidth.getText()),
                        Double.parseDouble(montantDist.getText()),
                        Double.parseDouble(traverseWidth.getText()));
            }}
            next.setDisable(false);
        });
        import_data.setOnAction(event -> {
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            FileChooser fileChooser= new FileChooser();
            File selectedDirectory = fileChooser.showOpenDialog(stage);
            System.out.println(selectedDirectory.getAbsolutePath());
            FileManager reader = new FileManager();
            reader.readSaveFile(selectedDirectory.getAbsolutePath());
        });
    }
}
