package view.controler;

import data.DATACONTAINER;
import file.FileManager;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.AppManager;
import org.json.simple.parser.ParseException;
import view.obj.SaveView;

import java.io.File;
import java.io.IOException;


public class StartControler {
    public Scene nextScene;
    public SaveView saveView;
    public AppManager appManager;

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
            try {
                saveView = reader.readSaveFile(selectedDirectory.getAbsolutePath());
                montantWidth.setText(saveView.montantWidth.toString());
                montantDist.setText(saveView.montantDist.toString());
                traverseWidth.setText(saveView.traverseWidth.toString());
                appManager.setSaveView(saveView);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
    }
}
