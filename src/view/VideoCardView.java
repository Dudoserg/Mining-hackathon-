package view;

import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import model.Point;

public class VideoCardView extends ImageView {
    // Fields
    public static final Integer NUMBER_IMAGES_IN_SPRITE = 3;
    public static final Integer COEFF = 3;
    public static final Double HEIGHT_IMAGE_VIDEO_CARD = 51.0 / NUMBER_IMAGES_IN_SPRITE * COEFF;
    public static final Double WITH_IMAGE_VIDEO_CARD = 35.0 * COEFF ;

    private Integer idVideoCard;
    private Animation animation;
    private Double heightImage;

    private Integer type;

    Image img;
    // Methods
    public VideoCardView( Integer idVideoCard, Point coord, Integer type) {
        super(  );

        this.type = type;
        Image aImage ;

        switch (type){
            case 1:{
                aImage = new Image(getClass().getResourceAsStream("/img/video1SpritesForward.png"));
                break;
            }
            case 2:{
                 aImage = new Image(getClass().getResourceAsStream("/img/video2SpritesForward.png"));
                break;
            }
            default:{
                aImage = new Image(getClass().getResourceAsStream("/img/video2SpritesForward.png"));
            }
        }

        this.setImage( aImage );


        this.img = aImage;

        this.idVideoCard = idVideoCard;
        this.setLayoutX( coord.getX() );
        this.setLayoutY( coord.getY() );

        setFitWidth( WITH_IMAGE_VIDEO_CARD );
        setFitHeight( HEIGHT_IMAGE_VIDEO_CARD );

        this.setRotate(-90.0);


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
        //this.animationPlay();
      //  this.animationStop();
    }

    public void crash(){
        switch (type){
            case 1:{
                Image aImage = new Image(getClass().getResourceAsStream("/img/video1crashSprites.png"));
                this.setImage( aImage );
                break;
            }
            case 2:{
                Image aImage = new Image(getClass().getResourceAsStream("/img/video2crashSprites.png"));
                this.setImage( aImage );
                break;
            }
            default:{
                Image aImage = new Image(getClass().getResourceAsStream("/img/video2crashSprites.png"));
                this.setImage( aImage );
                break;
            }
        }
    }

    public Integer getIdVideoCard() {
        return idVideoCard;
    }

    public void setIdVideoCard( Integer idVideoCard ){
        this.idVideoCard = idVideoCard;
    }

    public void animationPlay(){
        animation.play();
    }

    public void animationStop(){
        animation.stop();
    }

    public Integer getType() {
        return type;
    }
}
