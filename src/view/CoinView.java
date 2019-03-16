package view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CoinView extends ImageView {

    private Integer idCoin;
    private Image img;

    public CoinView(Image image, Integer idCoin) {
        super(image);
        this.idCoin = idCoin;
        this.img = image;
    }

    public Integer getIdCoin() {
        return idCoin;
    }
}
