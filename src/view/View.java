package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.AppManager;
import view.controler.AreaControler;
import view.controler.BeamControler;
import view.controler.FinalControler;
import view.controler.WindowsControler;

import java.io.IOException;


public class View extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        AppManager appManager = new AppManager();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("resources/scene/areaView.fxml"));
        Parent root = loader.load();
        AreaControler controler = loader.getController();
        controler.appManager = appManager;
        controler.init();
        Scene scene1 = new Scene(root);

        FXMLLoader loaderWindow = new FXMLLoader(getClass().getResource("resources/scene/windowsView.fxml"));
        Parent rootWindow = loaderWindow.load();
        WindowsControler windowsControler = loaderWindow.getController();
        Scene windowsScene = new Scene(rootWindow);
        windowsControler.appManager=appManager;
        windowsControler.prevScene=scene1;
        controler.nextScene=windowsScene;
        windowsControler.init();

        FXMLLoader loaderBeam = new FXMLLoader(getClass().getResource("resources/scene/beamView.fxml"));
        Parent rootBeam = loaderBeam.load();
        BeamControler beamControler = loaderBeam.getController();
        Scene beamScene = new Scene(rootBeam);
        beamControler.appManager=appManager;
        beamControler.prevScene=windowsScene;
        windowsControler.nextScene=beamScene;
        beamControler.init();

        FXMLLoader loaderFinal = new FXMLLoader(getClass().getResource("resources/scene/finalView.fxml"));
        Parent rootFinal = loaderFinal.load();
        FinalControler finalControler = loaderFinal.getController();
        Scene finalScene = new Scene(rootFinal);
        finalControler.init();
        beamControler.nextScene=finalScene;
        finalControler.appManager=appManager;
        finalControler.prevScene=beamScene;

        primaryStage.setScene(scene1);
        primaryStage.show();
    }
}
