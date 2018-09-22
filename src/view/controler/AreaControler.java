package view.controler;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import view.obj.ViewArea;

import java.util.Arrays;

public class AreaControler {

    private final ObservableList<ViewArea> data = FXCollections.observableArrayList();

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

    public void intit(){

        areaType.getItems().addAll(Arrays.asList("Rectangulaire","Trapezoidal","Pentagonal"));

        table.setEditable(true);
        table.setItems(data);
        add.setOnAction(event -> {
            String temp0[] = heightxWidth.getText().split("x");
            String temp1[] = xY.getText().split("x");
            data.add(new ViewArea(areaType.getValue(),temp0[0],temp0[1],temp1[0],temp1[1],thetaDeg.getText(),thetaPercent.getText(),faitageValue.getText(),name.getText()));
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
    }
}
