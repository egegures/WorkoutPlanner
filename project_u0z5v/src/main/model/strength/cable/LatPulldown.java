package model.strength.cable;

import model.strength.StrengthExercise;

public class LatPulldown extends StrengthExercise {
    //REQUIRES: pounds >= 0, intensity 0 <= x <= 10
    public LatPulldown(int pounds, int reps, int intensity) {
        super(pounds, reps, intensity, 0.8,"Lat pulldown");
    }
}
