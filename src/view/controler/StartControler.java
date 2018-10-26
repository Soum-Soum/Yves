package view.controler;

import data.DATACONTAINER;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.AppManager;


public class StartControler {
    public Scene nextScene;

    @FXML
    Button validate, next;
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
    }
}
