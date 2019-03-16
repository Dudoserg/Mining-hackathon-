package controller;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import model.*;
import sound.Sound;
import view.CoinView;
import view.GenerateView;
import view.VideoCardView;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;

public class Controller  {

    public Button ds;
    public Button btn1;
    public Label labelPurse;
    public Label labelDollar;
    public Label labelPriceChange;
    public ImageView oilBoil;
    public Label labelPriceOilBoil;
    public ImageView swapButton;

    private Label labelBTC;

    private AnchorPane anchorPane;
    private Pane pane;
    private Integer countStep;
    private Integer counterVideoCard;
    private Integer counterCoin;

    private ArrayList<VideoCard> allVideoCardsModel;
    private ArrayList<VideoCardView> allVideoCardsView;
    private Pane[][] allSlotForVideoCard;
    private Integer[][] slotForVideoCardIsBusy;     // -1 слот свободен ; >=0 индекс видюхи

    private ArrayList<Coin> allCoinModel;
    private ArrayList<CoinView> allcoinViews;

    private ArrayList<Coin> fallCoin;               // Упавшие монетки

    private Generate generate;
    private GenerateView generateView;

    private Rectangle generateHp;

    private Purse purse;
    private Course course;

    private Double deltaX ;
    private Double deltaY ;
// ЗВУК
    private String musicFile;
    private Media sound;
    private MediaPlayer mediaPlayer;

    private String musicFile2;
    private Media sound2;
    private MediaPlayer mediaPlayerEngine;

    private String musicFile3;
    private Media sound3;
    private MediaPlayer mediaPlayerWater;

    public Controller() {

    }

    public void generateVideoCard( Integer type ){
        System.out.println("Card created.");
        VideoCard aVideoCard = new VideoCard( counterVideoCard++, type );
        //aVideoCard.setPosition(20, 630);
        this.allVideoCardsModel.add( aVideoCard  );
        this.addVideoCardView( aVideoCard );
    }

    public void setRootandOther(Pane root, AnchorPane anchorPane){
        this.anchorPane = anchorPane;
        this.pane = pane;
    }

    public void init(){

        this.labelBTC = new Label();
        this.labelBTC.setText( "1" );
        this.labelBTC.setTextFill(Color.web("#f5c722"));
        this.labelBTC.setFont(new Font("TimesRoman", 65));

        this.labelBTC.setLayoutX( 170 );
        this.labelBTC.setLayoutY( 450 );

        this.anchorPane.getChildren().add( this.labelBTC );

        this.musicFile = "dzin.mp3";     // For example
        this.sound = new Media(new File(musicFile).toURI().toString());
        this.mediaPlayer = new MediaPlayer(sound);


        this.musicFile2 = "engine.mp3";
        this.sound2 = new Media(new File(musicFile2).toURI().toString());
        this.mediaPlayerEngine = new MediaPlayer(sound2);
        this.mediaPlayerEngine.setCycleCount(999);
        this.mediaPlayerEngine.setVolume(0.7);
        this.mediaPlayerEngine.play();


        this.musicFile3 = "oilSound.mp3";
        this.sound3 = new Media(new File(musicFile3).toURI().toString());
        this.mediaPlayerWater = new MediaPlayer(sound3);



        this.labelPurse.setTextFill(Color.web("#f5c722"));
        this.labelPurse.setFont(new Font("TimesRoman", 35));

        this.labelDollar.setTextFill(Color.web("#228B22"));
        this.labelDollar.setFont(new Font("TimesRoman", 35));

        this.labelPriceChange.setTextFill(Color.web("228B22"));
        this.labelPriceChange.setFont(new Font("TimesRoman", 65));

        this.labelPriceOilBoil.setText("10$");
        this.labelPriceOilBoil.setTextFill(Color.web("#f5c722"));
        this.labelPriceOilBoil.setFont(new Font("TimesRoman", 35));

        this.allSlotForVideoCard = new Pane[3][6];

        for(int i = 0 ; i < this.allSlotForVideoCard.length; i++)
            for(int j = 0 ; j < this.allSlotForVideoCard[i].length; j++){
                Pane aPane = new Pane();
                aPane.setLayoutX(390 + j * 80);
                aPane.setLayoutY(30 + i * 115);
                aPane.setPrefWidth(45);
                aPane.setPrefHeight(70);
                aPane.setStyle("-fx-background-color: gray; -fx-opacity: 0");
                aPane.setVisible(true);
                anchorPane.getChildren().add(aPane);
                this.allSlotForVideoCard[i][j] = aPane;
            }
        // ИНИЦИАЛИЗИРУЕМ
        this.slotForVideoCardIsBusy = new Integer[3][6];
        for(int i = 0 ; i < this.slotForVideoCardIsBusy.length; i++)
            for(int j = 0 ; j < this.slotForVideoCardIsBusy[i].length; j++)
                this.slotForVideoCardIsBusy[i][j] = -1;


        this.countStep = 0;

        this.allVideoCardsModel = new ArrayList<>();
        this.allVideoCardsView = new ArrayList<>();
        this.counterVideoCard = 0;

        this.allCoinModel = new ArrayList<>();
        this.allcoinViews = new ArrayList<>();
        this.counterCoin = 0;

        this.purse = new Purse();
        this.course = new Course();

        this.generate = new Generate(new Point(575,430));
        this.generateView = new GenerateView(this.generate.getPosition());
        this.anchorPane.getChildren().add(this.generateView);

        this.generateHp = new Rectangle(575,430,100,10);
        this.anchorPane.getChildren().add( this.generateHp );

        this.anchorPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("x = " + event.getX() + "y = " + event.getY());
            }
        });

//        Image image = new Image(getClass().getResourceAsStream("/img/garage.jpg"));
//        ImageView fon = new ImageView(image);
//
//        Double sizeWidth = this.anchorPane.getPrefWidth();
//        Double sizeHeight = this.anchorPane.getPrefHeight();
//
//        fon.setFitHeight( sizeHeight );
//        fon.setFitWidth( sizeWidth );
//
//        this.anchorPane.getChildren().add(fon);

        this.generateVideoCard( 1);
        this.generateVideoCard( 2);

        this.createLabelPrice();

        this.btn1.setVisible(false);
    }

    public void createLabelPrice(){
        Label labelPrice1 = new Label("33$");
        labelPrice1.setTextFill( Color.web("#000000"));
        labelPrice1.setFont( new Font("TimesRoman", 35));
        labelPrice1.setLayoutX( 885 );
        labelPrice1.setLayoutY( 705 );
        this.anchorPane.getChildren().add(labelPrice1);

        Label labelPrice2 = new Label("49$");
        labelPrice2.setTextFill( Color.web("#000000"));
        labelPrice2.setFont( new Font("TimesRoman", 35));
        labelPrice2.setLayoutX( 995 );
        labelPrice2.setLayoutY( 705 );
        this.anchorPane.getChildren().add(labelPrice2);
    }

    public void checkAvailiable(){
        for( int i = 0; i < this.allVideoCardsModel.size(); i++ ){
            // Если видеокарта не работает( ТАК ПРОВЕРЯЕМ НА МАГАЗИГ)
            if( this.allVideoCardsModel.get(i).getByingVideoCard() == false ){
                // Если нельзя купить(нет денег) Делаем их невидимыми
                if( this.allVideoCardsModel.get( i ).getPrice() > this.purse.getCountDollar() ){
                    this.allVideoCardsView.get( i ).setDisable( true );
                    this.allVideoCardsView.get( i ).setOpacity( 0.25 );
                    //this.allVideoCardsView.get( i ).setVisible(false);
                }else {
                    // Если можно купить( деньги водятся) Делаем их видимыми
                    this.allVideoCardsView.get( i ).setDisable( false );
                    this.allVideoCardsView.get( i ).setOpacity( 1 );
                    // this.allVideoCardsView.get( i ).setVisible(true);
                }
            }
        }
    }

    public void gameStep(){
        System.out.println("Count farmed Coin = " + this.purse.getCountCoin());
        countStep++;
        System.out.println("step # " + countStep.toString());
        for( VideoCard currentCard : this.allVideoCardsModel){
            if( currentCard.step() ){
                System.out.println("Новая монетка!");
                Point point = currentCard.getPosition();


                int height = VideoCardView.HEIGHT_IMAGE_VIDEO_CARD.intValue();
                int width = VideoCardView.WITH_IMAGE_VIDEO_CARD.intValue();

                Double diff = (Math.random() * width - width/2);

                Point aPoin = new Point(point.getX() + width/2 + diff.intValue(),
                        point.getY() + height + 5);

                this.createCoin(aPoin);
            }
        }

        for(int i = 0 ; i < this.allCoinModel.size(); i++){
            if( this.allCoinModel.get(i).step() ){
                this.deleteCoin(i);
            }
        }

        this.checkAvailiable();
        this.purse.step();
        this.course.step();
        this.reloadLabel();

        Boolean result = this.generate.step();


        if( result ){
            this.generateView.animationStop();
            this.mediaPlayerEngine.stop();
            // отключаем видеокарты
            for(int i = 0 ; i < this.allVideoCardsModel.size(); i++){
                this.allVideoCardsModel.get(i).setWorking( false );
            }
            for(int i = 0 ; i < this.allVideoCardsView.size(); i++)
                this.allVideoCardsView.get(i).animationStop();
        }
        else{
            this.mediaPlayerEngine.play();
            // Включаемм видеокарты
            for(int i = 0 ; i < this.allVideoCardsModel.size(); i++){
                if(this.allVideoCardsModel.get(i).getByingVideoCard() == true)      // видео продано, то включаем
                    this.allVideoCardsModel.get(i).setWorking( true );
            }
            for(int i = 0 ; i < this.allVideoCardsView.size(); i++)
                if(this.allVideoCardsModel.get(i).getByingVideoCard() == true)     // видео продано, то анимируем
                this.allVideoCardsView.get(i).animationPlay();

            // Включаем генератор
            this.generate.setWorking(true);
            // Включаем генератор(анимируем)
            this.generateView.animationPlay();



            this.generateHp.setWidth( this.generate.getFuelLevel() * 2 );
            switch ((int)(this.generateHp.getWidth() / 33) ){
                case 0:{
                    this.generateHp.setFill(Color.RED);
                    break;
                }
                case 1:{
                    this.generateHp.setFill(Color.YELLOW);
                    break;
                }
                case 2:{
                    this.generateHp.setFill(Color.GREEN);
                    break;
                }
                default:{
                    this.generateHp.setFill(Color.GREEN);
                    break;
                }
            }
        }
        // Ломаем видеокарты, если хп кончилось
        for(int i = 0 ; i < this.allVideoCardsModel.size(); i++){
            if( this.allVideoCardsModel.get(i).getHp() < 0){
                this.allVideoCardsView.get(i).crash();
            }
        }

    }



    public void deleteCoin(Integer i){
        this.allCoinModel.remove(i);
        this.anchorPane.getChildren().remove(this.allcoinViews.get(i));
        this.allcoinViews.remove(i);
    }

    public void stopGame(){
        // for(int i = 0 ; i < this.all)
    }
    public void startGame(){
        // for(int i = 0 ; i < this.all)
    }

    private void reloadLabel(){
        this.labelPurse.setText(" x "+this.purse.getCountCoin().toString() );
        this.labelDollar.setText(" x "+this.purse.getCountDollar().toString() );
        this.labelPriceChange.setText(this.course.getPrice() + "$");
    }

    private void createCoin(Point point){

        Coin aCoin = new Coin("BTC",point,this.counterCoin);
        this.allCoinModel.add(aCoin);

        Image aImage = new Image(getClass().getResourceAsStream("/img/coin.png"));
        CoinView aCoinView = new CoinView(aImage,this.counterCoin);

        aCoinView.setFitWidth(20);
        aCoinView.setFitHeight(20);

        aCoinView.setLayoutX( aCoin.getPosition().getX() );
        aCoinView.setLayoutY( aCoin.getPosition().getY() );
        this.allcoinViews.add(aCoinView);

        this.counterCoin++;

        ObservableList ss = this.anchorPane.getChildren();
        ss.add(aCoinView);

        aCoinView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if( purse.incCountCoin()){
                    // ГАВНОКОД - да да, надо было юзать хэшмап
                    for(int i = 0 ; i < allCoinModel.size(); i++){
                        if(allCoinModel.get(i).getIdCoin() == aCoinView.getIdCoin()){
                            deleteCoin(i);
                            break;
                        }
                    }
                }

            }
        });


    }

    public void btn1_oncklick(ActionEvent actionEvent) {
//        //createCoin(new Point(228,199))
//        System.out.println("click");
//        VideoCard aVideoCard = new VideoCard( counterVideoCard++ );
//        aVideoCard.setPosition(472, 600);
//        allVideoCardsModel.add(aVideoCard  );
//        this.addVideoCardView( aVideoCard );

    }

    public void addVideoCardView( VideoCard videoCard ){
//        Image videoCardTexture = new Image(getClass().getResourceAsStream("/img/video1Sprites.png"));

        VideoCardView aVideoCardView = new VideoCardView( videoCard.getIdVideoCard(), videoCard.getPosition(), videoCard.getType());
        this.allVideoCardsView.add( aVideoCardView );
        this.anchorPane.getChildren().add( aVideoCardView );

        aVideoCardView.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                deltaX = aVideoCardView.getLayoutX() - event.getScreenX();
                deltaY = aVideoCardView.getLayoutY() - event.getScreenY();

                // Подсвечиваем слоты для видеокарт при  нажатии на видюху
                for( int shelfNum = 0 ; shelfNum < allSlotForVideoCard.length ; shelfNum++) {
                    for (int slotNum = 0; slotNum < allSlotForVideoCard[shelfNum].length ; slotNum++) {
                        // Если свободен
                        if( slotForVideoCardIsBusy[shelfNum][slotNum] == -1 ) {
                            Pane currentPane = allSlotForVideoCard[shelfNum][slotNum];
                            currentPane.setStyle("-fx-background-color: gold; -fx-opacity: 0.15");
                        }
                    }
                }
            }
        });

        aVideoCardView.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {


                // ПРОЗРАЧИРУЕМ слоты для видеокарт при  отпускании нажатия на видюху
                for( int shelfNum = 0 ; shelfNum < allSlotForVideoCard.length ; shelfNum++) {
                    for (int slotNum = 0; slotNum < allSlotForVideoCard[shelfNum].length ; slotNum++) {
                        Pane currentPane = allSlotForVideoCard[shelfNum][slotNum];
                        currentPane.setStyle("-fx-background-color: gold; -fx-opacity: 0");
                    }
                }

                boolean miss = true;

                for( int shelfNum = 0 ; shelfNum < allSlotForVideoCard.length && miss; shelfNum++){
                    for( int slotNum = 0 ; slotNum < allSlotForVideoCard[shelfNum].length && miss; slotNum++){

                        Pane currentPane = allSlotForVideoCard[shelfNum][slotNum];

                        Double paneX = currentPane.getLayoutX();
                        Double paneY = currentPane.getLayoutY();

                        Double paneWidth = currentPane.getWidth();
                        Double paneHeight = currentPane.getHeight();

                        Double cardX = aVideoCardView.getLayoutX();
                        Double cardY = aVideoCardView.getLayoutY();

                        Double cardWidth = aVideoCardView.getFitWidth();
                        Double cardHeight = aVideoCardView.getFitHeight();

                        Double cardCentreX = cardX + cardWidth/2;
                        Double cardCentreY = cardY + cardHeight/2;

                        Double paneCenterX = paneX + paneWidth/2;
                        Double paneCenterY = paneY + paneHeight/2;

                        Double newCardX = paneCenterX - cardWidth/2;
                        Double newCardY = paneCenterY - cardHeight/2;

                        if( cardCentreX > paneX && cardCentreX < paneX + paneWidth
                                && cardCentreY > paneY && cardCentreY < paneY + paneHeight
                                && slotForVideoCardIsBusy[shelfNum][slotNum] == -1) // Если попали в свободный слот
                        {
                            System.out.println("Видюха попала");
                            // Освобождаем старый слот
                            for(int i = 0 ; i < slotForVideoCardIsBusy.length; i++)
                                for(int j = 0 ; j < slotForVideoCardIsBusy[i].length; j++)
                                    if( slotForVideoCardIsBusy[i][j] == aVideoCardView.getIdVideoCard() )
                                        slotForVideoCardIsBusy[i][j] = -1;
                            // Новый слот занят
                            slotForVideoCardIsBusy[shelfNum][slotNum] = aVideoCardView.getIdVideoCard();

                            for( int i = 0; i < allVideoCardsModel.size(); i++ ){
                                if( allVideoCardsModel.get( i ).getIdVideoCard() == aVideoCardView.getIdVideoCard()
                                        && !allVideoCardsModel.get( i ).getWorking()) {
                                    if( allVideoCardsModel.get(i).getByingVideoCard() == false){
                                        Integer price = allVideoCardsModel.get(i).getPrice();
                                        purse.setCountDollar( purse.getCountDollar() - price );
                                        allVideoCardsModel.get(i).setByingVideoCard(true);
                                    }
                                }
                            }
                            for(int i = 0 ; i < allVideoCardsModel.size(); i++){
                                if( allVideoCardsModel.get(i).getIdVideoCard() == aVideoCardView.getIdVideoCard()){
                                    if( !allVideoCardsModel.get(i).getWorking() )
                                        generateVideoCard( aVideoCardView.getType() );
                                }
                            }
                            //generateVideoCard( aVideoCardView.getType() );

                            // Помещаем ИЗОБРАЖЕНИЯ по центру слота
                            aVideoCardView.setLayoutX(newCardX);
                            aVideoCardView.setLayoutY(newCardY);
                            miss = false;

                            // изменяем координаты у МОДЕЛИ видеокарты
                            for(int i = 0 ; i < allVideoCardsModel.size(); i++){
                                if( allVideoCardsModel.get(i).getIdVideoCard() == aVideoCardView.getIdVideoCard() ){
                                    allVideoCardsModel.get( i ).setPosition( newCardX.intValue(), newCardY.intValue() );
                                    if(generate.getFuelLevel() > 0){
                                        //И  продана
                                        if(allVideoCardsModel.get(i).getByingVideoCard() == true){
                                            allVideoCardsModel.get( i ).setWorking( true );
                                            // Запускаем анимаци.
                                            aVideoCardView.animationPlay();
                                        }

                                    }
                                    else{
                                        allVideoCardsModel.get( i ).setWorking( false );
                                        // Запускаем анимаци.
                                        aVideoCardView.animationStop();
                                    }
                                    // Видеокарта становится купленной
                                    allVideoCardsModel.get(i).setByingVideoCard(true);
                                    break;
                                }
                            }




                        }
                    }
                }
                if( miss ){
                    System.out.println("Видюха НЕ попала");
                    // изменяем координаты у изображения видеокарты на старое значение
                    for(int i = 0 ; i < allVideoCardsModel.size(); i++){
                        if( allVideoCardsModel.get(i).getIdVideoCard() == aVideoCardView.getIdVideoCard() ){
                            aVideoCardView.setLayoutX( allVideoCardsModel.get(i).getPosition().getX());
                            aVideoCardView.setLayoutY( allVideoCardsModel.get(i).getPosition().getY());
                            break;
                        }
                    }
                }


            }
        });

        aVideoCardView.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                aVideoCardView.setLayoutX(event.getScreenX() + deltaX);
                aVideoCardView.setLayoutY(event.getScreenY() + deltaY);

//                primaryStage.setX(event.getScreenX() + xx);
//                primaryStage.setY(event.getScreenY() + yy);
            }
        });




    }

    public void changeCourse(MouseEvent mouseEvent) {

        Integer countCoin = this.purse.getCountCoin();
        Integer countDollar = this.purse.getCountDollar();

        Integer course = this.course.getPrice();

        this.purse.setCountDollar( countDollar + countCoin * course);
        this.purse.setCountCoin(0);

        Glow glow = new Glow();
        //setting the level property
        glow.setLevel(0.8);
        this.swapButton.setEffect(glow);

        //if( countCoin > 0) {
            this.mediaPlayer.play();
            this.mediaPlayer.stop();
            this.mediaPlayer.play();
            this.mediaPlayer.stop();
        //}

    }

    public void oilBoil_clicked(MouseEvent mouseEvent) {


        mediaPlayerWater.play();
        mediaPlayerWater.stop();
        mediaPlayerWater.play();
        mediaPlayerWater.stop();

        if( this.purse.getCountDollar() >= 10 ){
            // Снимаем бабилити
            this.purse.setCountDollar(this.purse.getCountDollar() - 10);

            // Заливаем бенз
            this.generate.setFuelLevel(this.generate.getFuelLevel() + 30);

        }
    }

    public  void oilBoil_entered(MouseEvent mouseEvent){
        System.out.println("Курсор над бочкой");
        // Create Bloom Effect
        Glow glow = new Glow();
        //setting the level property
        glow.setLevel(0.5);
        this.oilBoil.setEffect(glow);
    }

    public  void oilBoil_exited(MouseEvent mouseEvent){
        System.out.println("Курсор покинул бочку");
        this.oilBoil.setEffect( null );
    }

    public void swapButton_entered(MouseEvent mouseEvent){
        System.out.println("Курсор над кнопкой");
        // Create Bloom Effect
        Glow glow = new Glow();
        //setting the level property
        glow.setLevel(0.5);
        this.swapButton.setEffect(glow);
    }

    public void swapButton_exited(MouseEvent mouseEvent){
        System.out.println("Курсор покинул кнопку");
        this.swapButton.setEffect( null );
    }

    public void swapButton_reseled(MouseEvent mouseEvent){
        // Create Bloom Effect
        Glow glow = new Glow();
        //setting the level property
        glow.setLevel(0.5);
        this.swapButton.setEffect(glow);
    }
}

