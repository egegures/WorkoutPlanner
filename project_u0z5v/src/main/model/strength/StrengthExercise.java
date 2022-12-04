package model.strength;

import org.json.JSONObject;
import persistence.Writable;

public abstract class StrengthExercise implements Writable {
    private int pounds;
    private int intensity;
    private double calorieMultiplier;
    String name;
    private int reps;
    private double totalCalories;

    //REQUIRES: pounds >= 0, intensity 0 <= x <= 10
    public StrengthExercise(int pounds, int reps, int intensity, double calorieMultiplier,String name) {
        this.pounds = pounds;
        this.intensity = intensity;
        this.reps = reps;
        this.calorieMultiplier = calorieMultiplier;
        this.name = name;
        this.totalCalories = pounds * intensity * reps * calorieMultiplier;
    }

    // getters
    public int getWeight() {
        return pounds;
    }

    public int getIntensity() {
        return intensity;
    }

    public double getCalories() {
        return totalCalories;
    }

    public String getName() {
        return name;
    }

    public Integer getReps() {
        return reps;
    }

    public Double getCalorieMultiplier() {
        return calorieMultiplier;
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", getName());
        json.put("weight", getWeight());
        json.put("intensity", getIntensity());
        json.put("reps", getReps());
        json.put("calorie multiplier", getCalorieMultiplier());
        json.put("total calories", getCalories());
        return json;
    }
}
