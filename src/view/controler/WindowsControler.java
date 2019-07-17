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
import view.obj.SaveView;
import view.obj.ViewWindow;

import java.util.Arrays;

public class WindowsControler implements SaveViewListener{

    private final ObservableList<ViewWindow> data = FXCollections.observableArrayList();
    public Scene prevScene, nextScene;
    public AppManager appManager;

    @FXML
    Label error;
    @FXML
    ToggleButton leftRight;
    @FXML
    Button add, remove, next, validate, prev;
    @FXML
    TextField heightxWidth, xY, thetaDeg, thetaPercent, name;
    @FXML
    ChoiceBox<String> windowType;
    @FXML
    TableView<ViewWindow> table;
    @FXML
    TableColumn<ViewWindow,String> nameCol,xCol, yCol, heightCol, widthCol, typeCol,thetaDegCol,thetaPercentCol;

    public WindowsControler() {
    }

    public void init(){

        table.setEditable(true);
        table.setItems(data);
        add.setOnAction(event -> {
            try {
                String temp0[] = heightxWidth.getText().split("x");
                String temp1[] = xY.getText().split("x");
                data.add(new ViewWindow(windowType.getValue(),temp0[0],temp0[1],temp1[0],temp1[1],thetaDeg.getText(),thetaPercent.getText(),name.getText(), !leftRight.isSelected()));
            }catch (ArrayIndexOutOfBoundsException e){
                error.setText("Vérifier les séparateurs");
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

        windowType.getItems().addAll(Arrays.asList("RECTANGLE","TRAPEZIUM1","TRAPEZIUM2","TRAPEZIUM3","TRAPEZIUM4"));
        windowType.setOnAction(event -> {
            switch (windowType.getValue()){
                case "RECTANGLE":
                    thetaDeg.setDisable(true);
                    thetaDeg.clear();
                    thetaPercent.setDisable(true);
                    thetaPercent.clear();
                    break;
                default:
                    thetaDeg.setDisable(false);
                    thetaPercent.setDisable(false);
                    break;
            }
        });

        validate.setOnAction(event -> {
            if (validate.getText().equals("Valider")){
                validate.setText("Changer");
                table.setDisable(true);
                remove.setDisable(true);
                next.setDisable(false);
                add.setDisable(true);
            }else {
                validate.setText("Valider");
                add.setDisable(false);
                next.setDisable(true);
                remove.setDisable(false);
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

        thetaDeg.setOnAction(event -> {
            double radValue = (Double.parseDouble(thetaDeg.getText()))*((2*Math.PI)/360);
            thetaPercent.setText(String.valueOf(100*Math.tan(radValue)));
        });

        thetaPercent.setOnAction(event -> {
            double radValue = Math.atan(Double.parseDouble(thetaPercent.getText())/100);
            thetaDeg.setText(String.valueOf(radValue*(360/(Math.PI*2))));
        });

        leftRight.setOnAction(event -> {
            if (leftRight.isSelected()) {
                leftRight.setText("<- Gauche");
            }else {
                leftRight.setText("Droite ->");
            }
        });

        table.setOnMouseClicked(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                if(event.getClickCount() == 2){
                    ViewWindow tempViewWindow = data.get(table.getSelectionModel().getFocusedIndex());
                    windowType.setValue(tempViewWindow.type.getValue());
                    heightxWidth.setText(tempViewWindow.height.getValue() + "x" + tempViewWindow.wight.getValue());
                    xY.setText(tempViewWindow.x.getValue() + "x" + tempViewWindow.y.getValue());
                    thetaDeg.setText(tempViewWindow.thetaDeg.getValue());
                    thetaPercent.setText(tempViewWindow.thetaPercent.getValue());
                    name.setText(tempViewWindow.name.getValue());
                    data.remove(table.getSelectionModel().getFocusedIndex());
                }
            }
        });
    }
    public ObservableList<ViewWindow> getData() {
        return data;
    }

    @Override
    public void loadSaveData(SaveView saveView) {
        data.addAll(saveView.windows);
    }
}
