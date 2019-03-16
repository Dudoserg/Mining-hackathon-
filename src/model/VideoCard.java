package model;

import javafx.scene.layout.Pane;


public class VideoCard extends Pane {

    private Point position;
    private Integer idVideoCard;

    private Boolean working;

    private Integer period;

    private Integer counter;
    private Integer price;

    private Integer type;

    private Integer hp;

    private boolean byingVideoCard;


    public VideoCard( Integer idVideoCard, Integer type) {
        this.hp = 110 + (int)(Math.random() * 40);
        this.type = type;
        this.idVideoCard = idVideoCard;
        this.position = new Point(0,0);
        this.working = false;
        this.counter = -1;
        this.byingVideoCard = false;

        switch (type){
            case 1:{
                this.price = 33;
                this.period = 13;
                this.setPosition(850,630);
                break;
            }
            case 2:{
                this.price = 49;
                this.period = 10;
                this.setPosition(960,630);
                break;
            }
        }
    }

    public void setByingVideoCard(boolean flag){
        this.byingVideoCard = flag;
    }
    public boolean getByingVideoCard(){
        return this.byingVideoCard;
    }

    public boolean step(){
        System.out.println("VideoCarrd stepping");
        if( working ){
            this.counter++;
            this.hp--;
        }
        if( this.counter == this.period ){
            this.counter = 0;
            return true;
        }
        if( this.hp == 0 ){
            this.period *= 2;
            this.hp--;
        }
        return false;
    }

    public Integer getCounter() {
        return counter;
    }

    public Integer getPrice() {
        return price;
    }

    public Boolean getWorking() {
        return working;
    }

    public void setWorking(Boolean working) {
        this.working = working;
    }

    public Integer getIdVideoCard() {
        return idVideoCard;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setIdVideoCard(Integer idVideoCard) {
        this.idVideoCard = idVideoCard;
    }

    public void setPosition(Integer x, Integer y){
        this.position.setX( x );
        this.position.setY( y );
    }

    public Point getPosition(){
        return this.position;
    }

    public Integer getType() {
        return type;
    }

    public  Integer getHp(){
       return this.hp;
    }
}
