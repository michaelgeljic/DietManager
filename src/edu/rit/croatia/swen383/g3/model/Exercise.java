package edu.rit.croatia.swen383.g3.model;

public class Exercise {
    private String name;
    private double caloriesPerKgPerHour;

    public Exercise(String name, double caloriesPerKgPerHour) {
        this.name = name;
        this.caloriesPerKgPerHour = caloriesPerKgPerHour;
    }

    public String getName() {
        return name;
    }

    public double getCaloriesPerKgPerHour() {
        return caloriesPerKgPerHour;
    }

    public void setCaloriesPerKgPerHour(double caloriesPerKgPerHour) {
        this.caloriesPerKgPerHour = caloriesPerKgPerHour;
    }

    @Override
    public String toString() {
        return name + " (" + caloriesPerKgPerHour + " cal/kg/hr)";
    }
}
