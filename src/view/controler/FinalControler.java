package view.controler;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import main.AppManager;

import java.io.File;

public class FinalControler {

    public Scene prevScene;
    public AppManager appManager;
    private static final int MIN_PIXELS = 10;
    public String strPathImg="", strPathFile="";


    @FXML
    Button prev, validate;
    @FXML
    TextField pathImg, pathFile;
    @FXML
    Button browseImg, browseFile;
    @FXML
    CheckBox sameDir;



    public FinalControler() {
    }

    public void init(){
        prev.setOnAction(event -> {
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(prevScene);
        });

        validate.setOnAction(event -> {
            appManager.buildMontants();
            appManager.generateImage(this);
            appManager.generateFile(this);
        });

        sameDir.setOnMouseClicked(event -> {
            if(sameDir.isSelected()){
                pathImg.setDisable(true);
                browseImg.setDisable(true);
                pathImg.setText(pathFile.getText());
                strPathImg = pathFile.getText()+"/";
            }else {
                pathImg.setDisable(false);
                browseImg.setDisable(false);
                pathImg.setText("");
            }
        });

        browseFile.setOnAction(event -> {
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory = directoryChooser.showDialog(stage);
            pathFile.setText(selectedDirectory.getAbsolutePath());
            strPathFile = pathFile.getText() +"/";
        });

        browseImg.setOnAction(event -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            File selectedDirectory = directoryChooser.showDialog(stage);
            pathImg.setText(selectedDirectory.getAbsolutePath());
            strPathImg = pathImg.getText()+"/";
        });
    }

    public void upgradeImg(String path){
        Image image = new Image(new File(path).toURI().toString());
        //Image image = new Image(new File("src/view/resources/generatedimg/lol.jpg").toURI().toString());
        Stage imageStage = new Stage();
        double width = image.getWidth();
        double height = image.getHeight();
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        reset(imageView, width / 2, height / 2);
        ObjectProperty<Point2D> mouseDown = new SimpleObjectProperty<>();

        imageView.setOnMousePressed(e -> {
            Point2D mousePress = imageViewToImage(imageView, new Point2D(e.getX(), e.getY()));
            mouseDown.set(mousePress);
        });

        imageView.setOnMouseDragged(e -> {
            Point2D dragPoint = imageViewToImage(imageView, new Point2D(e.getX(), e.getY()));
            shift(imageView, dragPoint.subtract(mouseDown.get()));
            mouseDown.set(imageViewToImage(imageView, new Point2D(e.getX(), e.getY())));
        });

        imageView.setOnScroll(e -> {
            double delta = e.getDeltaY();
            Rectangle2D viewport = imageView.getViewport();

            double scale = clamp(Math.pow(1.01, delta),

                    // don't scale so we're zoomed in to fewer than MIN_PIXELS in any direction:
                    Math.min(MIN_PIXELS / viewport.getWidth(), MIN_PIXELS / viewport.getHeight()),

                    // don't scale so that we're bigger than image dimensions:
                    Math.max(width / viewport.getWidth(), height / viewport.getHeight())

            );

            Point2D mouse = imageViewToImage(imageView, new Point2D(e.getX(), e.getY()));

            double newWidth = viewport.getWidth() * scale;
            double newHeight = viewport.getHeight() * scale;

            // To keep the visual point under the mouse from moving, we need
            // (x - newViewportMinX) / (x - currentViewportMinX) = scale
            // where x is the mouse X coordinate in the image

            // solving this for newViewportMinX gives

            // newViewportMinX = x - (x - currentViewportMinX) * scale

            // we then clamp this value so the image never scrolls out
            // of the imageview:

            double newMinX = clamp(mouse.getX() - (mouse.getX() - viewport.getMinX()) * scale,
                    0, width - newWidth);
            double newMinY = clamp(mouse.getY() - (mouse.getY() - viewport.getMinY()) * scale,
                    0, height - newHeight);

            imageView.setViewport(new Rectangle2D(newMinX, newMinY, newWidth, newHeight));
        });

        imageView.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                reset(imageView, width, height);
            }
        });


        HBox buttons = createButtons(width, height, imageView);
        Tooltip tooltip = new Tooltip("Scroll to zoom, drag to pan");
        Tooltip.install(buttons, tooltip);

        Pane container = new Pane(imageView);


        imageView.fitWidthProperty().bind(container.widthProperty());
        imageView.fitHeightProperty().bind(container.heightProperty());
        VBox rootStageImage = new VBox(container, buttons);
        rootStageImage.setFillWidth(true);
        VBox.setVgrow(container, Priority.ALWAYS);

        imageStage.setScene(new Scene(rootStageImage));
        imageStage.show();
        container.setPrefSize(imageStage.getScene().getWidth(), imageStage.getScene().getHeight()-100);
    }

    private HBox createButtons(double width, double height, ImageView imageView) {
        Button reset = new Button("Reset");
        reset.setOnAction(e -> reset(imageView, width / 2, height / 2));
        Button full = new Button("Full view");
        full.setOnAction(e -> reset(imageView, width, height));
        HBox buttons = new HBox(10, reset, full);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(10));
        return buttons;
    }

    // reset to the top left:
    private void reset(ImageView imageView, double width, double height) {
        imageView.setViewport(new Rectangle2D(0, 0, width, height));
    }

    // shift the viewport of the imageView by the specified delta, clamping so
    // the viewport does not move off the actual image:
    private void shift(ImageView imageView, Point2D delta) {
        Rectangle2D viewport = imageView.getViewport();

        double width = imageView.getImage().getWidth() ;
        double height = imageView.getImage().getHeight() ;

        double maxX = width - viewport.getWidth();
        double maxY = height - viewport.getHeight();

        double minX = clamp(viewport.getMinX() - delta.getX(), 0, maxX);
        double minY = clamp(viewport.getMinY() - delta.getY(), 0, maxY);

        imageView.setViewport(new Rectangle2D(minX, minY, viewport.getWidth(), viewport.getHeight()));
    }

    private double clamp(double value, double min, double max) {

        if (value < min)
            return min;
        if (value > max)
            return max;
        return value;
    }

    // convert mouse coordinates in the imageView to coordinates in the actual image:
    private Point2D imageViewToImage(ImageView imageView, Point2D imageViewCoordinates) {
        double xProportion = imageViewCoordinates.getX() / imageView.getBoundsInLocal().getWidth();
        double yProportion = imageViewCoordinates.getY() / imageView.getBoundsInLocal().getHeight();

        Rectangle2D viewport = imageView.getViewport();
        return new Point2D(
                viewport.getMinX() + xProportion * viewport.getWidth(),
                viewport.getMinY() + yProportion * viewport.getHeight());
    }

    public static void main(String[] args){
        String path = "src/view/resources/generatedimg/Generated_Img.jpg";
        Image image = new Image(new File(path).toURI().toString());
    }
}
