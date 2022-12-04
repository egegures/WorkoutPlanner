package model.strength.dumbbell;

import model.strength.StrengthExercise;

public class ShoulderPress extends StrengthExercise {
    //REQUIRES: pounds >= 0, intensity 0 <= x <= 10
    public ShoulderPress(int pounds, int reps, int intensity) {
        super(pounds, reps, intensity,0.8,"Shoulder press");
    }
}
