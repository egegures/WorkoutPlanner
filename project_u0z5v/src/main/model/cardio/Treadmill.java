package model.cardio;

public class Treadmill extends Cardio {
    // REQUIRES: minutes > 0, speed >= 1, incline >= 0
    public Treadmill(int minutes, int level) {
        super(minutes, level, 0.8,"Treadmill");
    }
}
