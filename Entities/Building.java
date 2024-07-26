
package Entities;

public class Building {
    protected Integer floors;

    public Building() {
    }

    public Building(Integer floors) {
        this.floors = floors;
    }

    public Integer getFloors() {
        return floors;
    }

    public void setFloors(Integer floors) {
        this.floors = floors;
    }

    @Override
    public String toString() {
        return "Building{" + "floors=" + floors + '}';
    }  
}
