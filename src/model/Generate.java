package model;

public class Generate  {
    //Fields
    private Point position;

    private Boolean working;

    private Integer fuelLevel;

    // Methods

    public Generate(Point position ) {
        this.position = position;
        this.working = true;
        this.fuelLevel = 100;

    }


    public Point getPosition() {
        return position;
    }

    public Boolean getWorking() {
        return working;
    }

    public Integer getFuelLevel() {
        return fuelLevel;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public void setWorking(Boolean working) {
        this.working = working;
    }

    public void setFuelLevel(Integer fuelLevel) {
        this.fuelLevel = fuelLevel;
        if(this.fuelLevel > 100)
            this.fuelLevel = 100;
    }

    public boolean step(){
        if (this.working){
            this.fuelLevel--;
        }

        if(this.fuelLevel == 0){
            this.working = false;
            return true;
        }

        return false;
    }
}
