package model.strength.dumbbell;

import model.strength.StrengthExercise;

public class BicepsCurl extends StrengthExercise {
    //REQUIRES: pounds >= 0, intensity 0 <= x <= 10
    public BicepsCurl(int pounds, int reps, int intensity) {
        super(pounds, reps, intensity, 0.8, "Biceps curl");
    }
}

