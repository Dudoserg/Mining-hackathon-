package model;

public class Coin {
    // Fields
    private String name;
    private Point position;
    private Integer idCoin;

    private Integer timeLive;


    // Methods

    public Coin(String name, Point position, Integer idCoin) {
        this.name = name;
        this.position = position;
        this.idCoin = idCoin;
        this.timeLive = 10 + (int)(Math.random() * 6 );
    }

    public String getName() {
        return name;
    }

    public Point getPosition() {
        return position;
    }

    public Integer getIdCoin() {
        return idCoin;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public void setIdCoin(Integer idCoin) {
        this.idCoin = idCoin;
    }
    public boolean step(){
        this.timeLive--;
        if( this.timeLive == 0){
            return true;
        }

        return false;
    }
}
