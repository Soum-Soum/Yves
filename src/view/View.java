package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.AppManager;
import view.controler.*;

import java.io.IOException;


public class View extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        AppManager appManager = new AppManager();
        FXMLLoader startLoader = new FXMLLoader(getClass().getResource("resources/scene/startView.fxml"));
        Parent startRoot = startLoader.load();
        StartControler startControler= startLoader.getController();
        startControler.init();
        Scene firstScene= new Scene(startRoot);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("resources/scene/areaView.fxml"));
        Parent root = loader.load();
        AreaControler controler = loader.getController();
        controler.appManager = appManager;
        controler.prevScene = firstScene;
        controler.init();
        Scene scene1 = new Scene(root);
        startControler.nextScene = scene1;

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
        beamControler.areas=controler.getData();
        windowsControler.nextScene=beamScene;
        beamControler.init();

        FXMLLoader loaderFinal = new FXMLLoader(getClass().getResource("resources/scene/finalView.fxml"));
        Parent rootFinal = loaderFinal.load();
        FinalControler finalControler = loaderFinal.getController();
        Scene finalScene = new Scene(rootFinal);
        finalControler.init();
        beamControler.nextScene=finalScene;
        finalControler.areaControler=controler;
        finalControler.beamControler=beamControler;
        finalControler.windowsControler=windowsControler;
        finalControler.appManager=appManager;
        finalControler.prevScene=beamScene;

        primaryStage.setScene(firstScene);
        primaryStage.show();
    }
}
