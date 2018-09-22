package view.controler;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import view.obj.ViewArea;

import java.util.Arrays;

public class AreaControler {

    private final ObservableList<ViewArea> data = FXCollections.observableArrayList();
    public Scene nextScene;

    @FXML
    Label error;
    @FXML
    Button add, remove, next, validate;
    @FXML
    TextField heightxWidth, xY, thetaDeg, thetaPercent, faitageValue,name;
    @FXML
    ChoiceBox<String> areaType;
    @FXML
    TableView<ViewArea> table;
    @FXML
    TableColumn<ViewArea,String> nameCol, xCol, yCol, heightCol, widthCol, faitageCol,typeCol,thetaDegCol,thetaPercentCol;

    public AreaControler() {

    }

    public void init(){

        areaType.getItems().addAll(Arrays.asList("Rectangulaire","Trapezoidal","Pentagonal"));
        areaType.setOnAction(event -> {
            switch (areaType.getValue()){
                case "Rectangulaire":
                    thetaDeg.setDisable(true);
                    thetaDeg.clear();
                    thetaPercent.setDisable(true);
                    thetaPercent.clear();
                    faitageValue.setDisable(true);
                    faitageValue.clear();
                    break;
                case "Trapezoidal":
                    thetaDeg.setDisable(false);
                    thetaPercent.setDisable(false);
                    faitageValue.setDisable(true);
                    faitageValue.clear();
                    break;
                case "Pentagonal":
                    thetaDeg.setDisable(false);
                    thetaPercent.setDisable(false);
                    faitageValue.setDisable(false);
                    break;
            }
        });

        table.setEditable(true);
        table.setItems(data);
        add.setOnAction(event -> {
            String temp0[] = heightxWidth.getText().split("x");
            String temp1[] = xY.getText().split("x");
            data.add(new ViewArea(areaType.getValue(),temp0[0],temp0[1],temp1[0],temp1[1],thetaDeg.getText(),thetaPercent.getText(),faitageValue.getText(),name.getText()));
        });

        remove.setOnAction(event -> {
            data.remove(table.getSelectionModel().getFocusedIndex());
        });

        nameCol.setCellValueFactory(celltab -> celltab.getValue().name);
        typeCol.setCellValueFactory(celltab -> celltab.getValue().type);
        heightCol.setCellValueFactory(celltab -> celltab.getValue().height);
        widthCol.setCellValueFactory(celltab -> celltab.getValue().wight);
        xCol.setCellValueFactory(celltab -> celltab.getValue().x);
        yCol.setCellValueFactory(celltab -> celltab.getValue().y);
        thetaDegCol.setCellValueFactory(celltab -> celltab.getValue().thetaDeg);
        thetaPercentCol.setCellValueFactory(celltab -> celltab.getValue().thetaPercent);
        faitageCol.setCellValueFactory(celltab -> celltab.getValue().faitageValue);

        faitageValue.setOnAction(event -> {
            if (Double.parseDouble(faitageValue.getText())>1){
                setErrorMessage("Le faitage ne peut pas être supérieur à 1");
            }
        });

        next.setDisable(true);
        next.setOnAction(event -> {
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(nextScene);
        });

        thetaDeg.setOnAction(event -> {
            double radValue = (Double.parseDouble(thetaDeg.getText()))*((2*Math.PI)/360);
            thetaPercent.setText(String.valueOf(100*Math.tan(radValue)));
        });

        thetaPercent.setOnAction(event -> {
            double radValue = Math.atan(Double.parseDouble(thetaPercent.getText())/100);
            thetaDeg.setText(String.valueOf(radValue*(360/(Math.PI*2))));
        });

        validate.setOnAction(event -> {
            if (validate.getText().equals("Valider")){
                if (data.size()==0){
                    setErrorMessage("Vous devez spécifier au moins une\n zonne de travail");
                }else {
                    validate.setText("Changer");
                    next.setDisable(false);
                    add.setDisable(true);
                    remove.setDisable(true);
                    table.setDisable(true);
                }
            }else {
                validate.setText("Valider");
                next.setDisable(true);
                add.setDisable(false);
                remove.setDisable(false);
                table.setDisable(false);
            }
        });
    }

    public void setErrorMessage(String errorMessage){
        error.setText(errorMessage);
    }
}