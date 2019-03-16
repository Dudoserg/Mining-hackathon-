package view;

import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import model.Point;

public class GenerateView extends ImageView {
    // Fields
    public static final Integer NUMBER_IMAGES_IN_SPRITE = 3;
    public static final Integer COEFF = 2;
    public static final Double HEIGHT_IMAGE_VIDEO_CARD = 200.0;
    public static final Double WITH_IMAGE_VIDEO_CARD = 200.0 ;

    private Animation animation;
    private Double heightImage;

    Image img;
    // Methods


    public GenerateView( Point coord) {

        super(  );


        Image aImage ;
        aImage = new Image(getClass().getResourceAsStream("/img/generateSprites.png"));

        this.setImage( aImage );

        this.img = aImage;


        this.setLayoutX( coord.getX() );
        this.setLayoutY( coord.getY() );

        setFitWidth( WITH_IMAGE_VIDEO_CARD );
        setFitHeight( HEIGHT_IMAGE_VIDEO_CARD );

//        this.setRotate(-90.0);


        animation = new SpriteAnimation(
                this,
                Duration.millis(200),
                (int)this.img.getWidth(),
                (int)(this.img.getHeight() / NUMBER_IMAGES_IN_SPRITE),
                WITH_IMAGE_VIDEO_CARD,
                HEIGHT_IMAGE_VIDEO_CARD
        );

        animation.setCycleCount(Animation.INDEFINITE);
        this.setViewport(new Rectangle2D(0, 0, this.img.getWidth(), this.img.getHeight()/3));
        this.animationPlay();
        //this.animationStop();

    }

    public void animationPlay(){
        animation.play();
    }

    public void animationStop(){
        animation.stop();
    }
}
