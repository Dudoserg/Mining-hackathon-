package model;

public class Course {



    private Integer price;
    private Integer period;
    private Integer currentStep;


    public Course( ) {
        this.price = 7;
        this.period = 2;
        this.currentStep = 0;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void  step(){
        currentStep++;
        if( currentStep >= period ){
            currentStep = 0;
            this.price = 2 + (int)(Math.random() * 5 );
        }

    }
}
