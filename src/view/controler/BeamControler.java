package view.controler;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.AppManager;
import view.obj.ViewBeam;

public class BeamControler {

    private final ObservableList<ViewBeam> data = FXCollections.observableArrayList();
    public Scene prevScene, nextScene;
    public AppManager appManager;

    @FXML
    Button add, remove, next, validate, prev;
    @FXML
    TextField heightxWidth, x;
    @FXML
    ChoiceBox<String> dependance;
    @FXML
    TableView<ViewBeam> table;
    @FXML
    TableColumn<ViewBeam,String> xCol, heightCol, widthCol, dependanceCol;

    public BeamControler() {

    }

    public void init(){

        table.setEditable(true);
        table.setItems(data);
        add.setOnAction(event -> {
            String temp0[] = heightxWidth.getText().split("x");
            data.add(new ViewBeam(temp0[0],temp0[1],x.getText(),dependance.getValue()));
        });

        remove.setOnAction(event -> {
            data.remove(table.getSelectionModel().getFocusedIndex());
        });

        heightCol.setCellValueFactory(celltab -> celltab.getValue().height);
        widthCol.setCellValueFactory(celltab -> celltab.getValue().wight);
        xCol.setCellValueFactory(celltab -> celltab.getValue().x);

        validate.setOnAction(event -> {
            if (validate.getText().equals("Valider")){
                remove.setDisable(true);
                table.setDisable(true);
                next.setDisable(false);
                validate.setText("Changer");
                add.setDisable(true);
            }else {
                next.setDisable(true);
                remove.setDisable(false);
                validate.setText("Valider");
                add.setDisable(false);
                table.setDisable(false);
            }
        });

        next.setDisable(true);
        next.setOnAction(event -> {
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(nextScene);
        });

        prev.setOnAction(event -> {
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(prevScene);
        });

    }

}
