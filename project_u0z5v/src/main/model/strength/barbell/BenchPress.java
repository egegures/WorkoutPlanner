package model.strength.barbell;

import model.strength.StrengthExercise;

public class BenchPress extends StrengthExercise {
    //REQUIRES: pounds >= 0, intensity 0 <= x <= 10
    public BenchPress(int pounds, int reps, int intensity) {
        super(pounds, reps, intensity,0.8,"Bench press");
    }
}
