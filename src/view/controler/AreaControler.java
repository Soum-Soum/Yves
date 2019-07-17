package view.controler;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import main.AppManager;
import util.Utilies;
import view.obj.SaveView;
import view.obj.ViewArea;

import java.util.Arrays;

public class AreaControler implements SaveViewListener{

    private final ObservableList<ViewArea> data = FXCollections.observableArrayList();
    public Scene nextScene, prevScene;
    public AppManager appManager;

    @FXML
    ToggleButton leftRight;
    @FXML
    Label error;
    @FXML
    Button add, remove, next, prev, validate;
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

    public ObservableList<ViewArea> getData() {
        return data;
    }

    public void init(){
        areaType.getItems().addAll(Arrays.asList("RECTANGLE","TRAPEZIUM1","TRAPEZIUM2","TRAPEZIUM3","TRAPEZIUM4","PENTAGON"));
        areaType.setOnAction(event -> {
            switch (areaType.getValue()){
                case "RECTANGLE":
                    thetaDeg.setDisable(true);
                    thetaDeg.clear();
                    thetaPercent.setDisable(true);
                    thetaPercent.clear();
                    faitageValue.setDisable(true);
                    faitageValue.clear();
                    leftRight.setDisable(false);
                    break;
                case "PENTAGON":
                    thetaDeg.setDisable(false);
                    thetaPercent.setDisable(false);
                    faitageValue.setDisable(false);
                    leftRight.setDisable(true);
                    break;
                default:
                    thetaDeg.setDisable(false);
                    thetaPercent.setDisable(false);
                    faitageValue.setDisable(true);
                    leftRight.setDisable(false);
                    faitageValue.clear();
                    break;
            }
        });

        table.setEditable(true);
        table.setItems(data);
        add.setOnAction(event -> {
            try {
                String temp0[] = heightxWidth.getText().split("x");
                String temp1[] = xY.getText().split("x");
                data.add(new ViewArea(areaType.getValue(),temp0[0],temp0[1],temp1[0],temp1[1],thetaDeg.getText(),thetaPercent.getText(),faitageValue.getText(),name.getText(),!leftRight.isSelected()));
            }catch (ArrayIndexOutOfBoundsException e){
                error.setText("Vérifier les séparateurs");
            }
        });

        table.setOnMouseClicked(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                if(event.getClickCount() == 2){
                    ViewArea tempViewArea = data.get(table.getSelectionModel().getFocusedIndex());
                    areaType.setValue(tempViewArea.type.getValue());
                    heightxWidth.setText(tempViewArea.height.getValue() + "x" + tempViewArea.wight.getValue());
                    xY.setText(tempViewArea.x.getValue() + "x" + tempViewArea.y.getValue());
                    thetaDeg.setText(tempViewArea.thetaDeg.getValue());
                    thetaPercent.setText(tempViewArea.thetaPercent.getValue());
                    faitageValue.setText(tempViewArea.faitageValue.getValue());
                    name.setText(tempViewArea.name.getValue());
                    data.remove(table.getSelectionModel().getFocusedIndex());
                }
            }
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

        prev.setOnAction(event -> {
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(prevScene);
        });

        thetaDeg.setOnAction(event -> {
            double radValue = (Double.parseDouble(thetaDeg.getText()))*((2*Math.PI)/360);
            thetaPercent.setText(String.valueOf(Utilies.round3(100*Math.tan(radValue))));
        });

        thetaPercent.setOnAction(event -> {
            double radValue = Math.atan(Double.parseDouble(thetaPercent.getText())/100);
            thetaDeg.setText(String.valueOf(Utilies.round3(radValue*(360/(Math.PI*2)))));
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

        leftRight.setOnAction(event -> {
           if (leftRight.isSelected()) {
               leftRight.setText("<- Gauche");
           }else {
               leftRight.setText("Droite ->");
           }
        });
    }

    public void setErrorMessage(String errorMessage){
        error.setText(errorMessage);
    }

    @Override
    public void loadSaveData(SaveView saveView){
        data.addAll(saveView.areas);
    }
}
