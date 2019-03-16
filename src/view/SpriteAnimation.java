package view;

import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import model.VideoCard;


class SpriteAnimation extends Transition {
    private final ImageView imageView;
//    private final int count;
//    private final int columns;
//    private final int offsetX;
//    private final int offsetY;
    private final int width;
    private final int height;
    private Double resultWidth;
    private Double resultHeight;

    public SpriteAnimation(
            ImageView imageView,
            Duration duration,
//            int count, int columns,
//            int offsetX, int offsetY,
            int width, int height,
            Double resultWidth, Double resultHeight)
    {
        this.imageView = imageView;
//        this.count = count;
//        this.columns = columns;
//        this.offsetX = offsetX;
//        this.offsetY = offsetY;
        this.width = width;
        this.height = height;
        this.resultWidth = resultWidth;
        this.resultHeight = resultHeight;
        setCycleDuration(duration);
    }


    protected void interpolate(double k) {

        Integer tmp = (int)((k*100) / (0.34*100)) + 1;

        final int x = 0;
        final int y = (tmp-1) * height;
        imageView.setViewport(new Rectangle2D(x, y, width, height));

        imageView.setFitWidth( this.resultWidth );
        imageView.setFitHeight( this.resultHeight);

//        System.out.print("K = ");
//        System.out.print(k);
//        System.out.print("index = ");
//        System.out.print(tmp);
//        System.out.println();
    }
}
