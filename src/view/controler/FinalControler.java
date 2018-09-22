package view.controler;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class FinalControler {

    public Scene prevScene;

    @FXML
    Button prev, validate;

    public FinalControler() {
    }

    public void init(){
        prev.setOnAction(event -> {
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(prevScene);
        });
    }
}