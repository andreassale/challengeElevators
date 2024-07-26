
package Entities;


public class Elevator extends Building {
    private Integer max_weight;

    public Elevator() {
    }

    public Elevator(Integer max_weight, Integer floors) {
        super(floors);
        this.max_weight = max_weight;
    }

    public Integer getMax_weight() {
        return max_weight;
    }

    public void setMax_weight(Integer max_weight) {
        this.max_weight = max_weight;
    }

    public Integer getFloors() {
        return floors;
    }

    public void setFloors(Integer floors) {
        this.floors = floors;
    }

    @Override
    public String toString() {
        return "Elevator{" + "max_weight=" + max_weight + '}';
    }

}