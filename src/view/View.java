package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.controler.AreaControler;
import view.controler.FinalControler;

import java.io.IOException;


public class View extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("resources/scene/areaView.fxml"));
        Parent root = loader.load();
        AreaControler controler = loader.getController();
        controler.init();
        Scene scene1 = new Scene(root);

        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("resources/scene/finalView.fxml"));
        Parent root2 = loader2.load();
        FinalControler finalControler = loader2.getController();
        finalControler.init();
        Scene finalScene = new Scene(root2);
        controler.nextScene=finalScene;
        finalControler.prevScene=scene1;



        primaryStage.setScene(scene1);
        primaryStage.show();
    }
}
