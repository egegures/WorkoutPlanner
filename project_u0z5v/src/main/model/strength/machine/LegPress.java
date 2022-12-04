package model.strength.machine;

import model.strength.StrengthExercise;

public class LegPress extends StrengthExercise {
    //REQUIRES: pounds >= 0, intensity 0 <= x <= 10
    public LegPress(int pounds, int reps, int intensity) {
        super(pounds, reps, intensity, 0.8,"Leg press");
    }

}
