package model.cardio;

import org.json.JSONObject;
import persistence.Writable;

public abstract class Cardio implements Writable {

    private int minutes;
    private int level;
    private double calorieMultiplier;
    String name;
    private double totalCalories;

    // REQUIRES: minutes > 0, level >= 1
    public Cardio(int minutes, int level, double calorieMultiplier,String name) {
        this.minutes = minutes;
        this.level = level;
        this.calorieMultiplier = calorieMultiplier;
        this.totalCalories = minutes * level * calorieMultiplier;
        this.name = name;
    }

    // get methods
    public int getMinutes() {
        return minutes;
    }

    public int getLevel() {
        return level;
    }

    public double getCalories() {
        return totalCalories;
    }

    public String getName() {
        return name;
    }

    public Double getCalorieMultiplier() {
        return calorieMultiplier;
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", getName());
        json.put("minutes", getMinutes());
        json.put("level", getLevel());
        json.put("calorie multiplier", getCalorieMultiplier());
        json.put("total calories", getCalories());
        return json;
    }


}
