package sample;

import controller.Controller;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Duration;
import model.Generate;
import model.Purse;
import view.GenerateView;

import javax.swing.*;


import java.util.TimerTask;

public class Main extends Application {

    private Controller controller;
    private  AnchorPane anchorPane;
    private Pane root;

    private MenuBox menuBox;
    private Double xOffset;
    private Double yOffset;
    private Stage primaryStage;
    ImageView imageView;





    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        String path = "sample.fxml" ;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));

        root = (Pane) fxmlLoader.load();

        // ПОлучаем с главной панели Анхорпейн, на котором будем все отрисовывать
        ObservableList ss = root.getChildren();
        anchorPane = (AnchorPane) (ss.get(0));

        controller = (Controller) fxmlLoader.getController();
        controller.setRootandOther(this.root, this.anchorPane);
        controller.init();

        Scene scene = new Scene(root, Color.TRANSPARENT);
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
        MenuItem continueGame = new MenuItem("Продолжить");
        MenuItem exitGame = new MenuItem("ВЫХОД");
        SubMenu mainMenu = new SubMenu(
                continueGame,exitGame
        );

        this.menuBox = new MenuBox(mainMenu);

        continueGame.setOnMouseClicked(event -> {
            System.out.println("Нажата кнопка меню продолжить");
            this.showOrHideMenu();
        });
        exitGame.setOnMouseClicked( event->{
            System.exit(0);
        });


        root.getChildren().addAll(menuBox);


        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                this.showOrHideMenu();
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////


        primaryStage.setTitle("Mining");
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();

        this.addHeaderRrctangle();

        this.go();
    }

    private void addHeaderRrctangle(){
        Rectangle rectangleHeader = new Rectangle(1100, 35);
        rectangleHeader.setOpacity(0.35);
        rectangleHeader.setArcWidth(6);
        rectangleHeader.setArcHeight(6);
        rectangleHeader.setLayoutX(0);
        rectangleHeader.setLayoutY(0);

        Color rectangleColor = Color.rgb(43,53,54);
        rectangleHeader.setFill(rectangleColor);


        anchorPane.getChildren().addAll(rectangleHeader);
        rectangleHeader.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = primaryStage.getX() - event.getScreenX();
                yOffset = primaryStage.getY() - event.getScreenY();
            }
        });
        rectangleHeader.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setX(event.getScreenX() + xOffset);
                primaryStage.setY(event.getScreenY() + yOffset);
            }
        });
    }

    public void go(){
        java.util.Timer timer = new java.util.Timer();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
//                controller.gameStep();
                Platform.runLater(() -> {
                    controller.gameStep();
                });
            }


        };

        timer.schedule(timerTask, 0, 500);

    }
    public void showOrHideMenu( ){
        FadeTransition ft = new FadeTransition(Duration.seconds(0.6), this.menuBox);
        this.showOrHideMenuRealisation(ft);
    }
    public void showOrHideMenu( Double time){
        FadeTransition ft = new FadeTransition(Duration.seconds(time), this.menuBox);
        this.showOrHideMenuRealisation(ft);
    }
    private void showOrHideMenuRealisation(FadeTransition ft){
        if (!this.menuBox.isVisible()) {
            this.controller.stopGame();
            ft.setFromValue(0);
            ft.setToValue(1);
            ft.play();
            this.menuBox.setVisible(true);
        }
        else{
            this.controller.startGame();
            ft.setFromValue(1);
            ft.setToValue(0);
            ft.setOnFinished(evt ->    this.menuBox.setVisible(false));
            ft.play();

        }
    }

    private static class MenuBox extends Pane {
        static SubMenu subMenu;
        public MenuBox(SubMenu subMenu){
            MenuBox.subMenu = subMenu;
            Image image = new Image(getClass().getResourceAsStream("/img/garageDoor.png"));
            ImageView imageView = new ImageView(image );
            imageView.setViewport(new Rectangle2D(0, 0, image.getWidth(), image.getHeight()));
            imageView.setFitWidth(1100);
            imageView.setFitHeight(750);
            getChildren().addAll(imageView);

            setVisible(false);
            //Rectangle bg = new Rectangle(1000,700,Color.LIGHTBLUE);
            Rectangle bg = new Rectangle(1100,750,Color.GRAY);
            bg.setOpacity(0.4);
            getChildren().addAll(bg, subMenu);
        }
        public void setSubMenu(SubMenu subMenu){
            getChildren().remove(MenuBox.subMenu);
            MenuBox.subMenu = subMenu;
            getChildren().add(MenuBox.subMenu);
        }
    }

    private static class SubMenu extends VBox {
        public SubMenu(MenuItem...items){
            setSpacing(15);
            setTranslateY(255);
            setTranslateX(375);
            for(MenuItem item : items){
                getChildren().addAll(item);
            }
        }
    }

    private static class MenuItem extends StackPane {
        public  MenuItem(String name){
            Rectangle bg = new Rectangle(400,40,Color.GRAY);
            bg.setOpacity(0.6);

            Text text = new Text(name);
            text.setFill(Color.WHITE);
            text.setFont(Font.font("Arial", FontWeight.BOLD,14));

            setAlignment(Pos.CENTER);
            getChildren().addAll(bg,text);
            FillTransition st = new FillTransition(Duration.seconds(0.5),bg);
            setOnMouseEntered(event -> {
                st.setFromValue(Color.DARKGRAY);
                st.setToValue(Color.DARKGOLDENROD);
                st.setCycleCount(Animation.INDEFINITE);
                st.setAutoReverse(true);
                st.play();
            });
            setOnMouseExited(event -> {
                st.stop();
                bg.setFill(Color.GRAY);
            });
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
