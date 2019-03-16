package model;

public class Purse {


    private Integer countCoin;
    private Integer limitCoin;

    private Integer countDollar;


    public Purse() {
        this.countCoin = 0;
        this.limitCoin = 10;
        this.countDollar = 100 ;
    }

    public boolean incCountCoin(){
        if( this.countCoin < this.limitCoin){
            this.countCoin++;
            return true;
        }
        return false;
    }

    public Integer getCountCoin() {
        return countCoin;
    }

    public Integer getCountDollar() {
        return countDollar;
    }

    public void setCountDollar(Integer countDollar) {
        this.countDollar = countDollar;
    }

    public void setCountCoin(Integer countCoin) {
        this.countCoin = countCoin;
    }

    public  void step(){
    }

}
