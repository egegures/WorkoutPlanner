package model.cardio;

public class RowingMachine extends Cardio {
    // REQUIRES: minutes > 0, level >= 1, difficulty 0 <= x <= 10
    public RowingMachine(int minutes, int level) {
        super(minutes, level, 0.8,"Rowing machine");
    }


}
