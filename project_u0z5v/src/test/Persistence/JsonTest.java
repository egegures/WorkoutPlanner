package Persistence;

import model.cardio.Cardio;
import model.strength.StrengthExercise;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {

    protected void checkExercise(int pounds, int intensity, int reps, double calorieMultiplier,
                                 StrengthExercise exercise) {
        assertEquals(pounds, exercise.getWeight());
        assertEquals(intensity, exercise.getIntensity());
        assertEquals(reps, exercise.getReps());
        assertEquals(calorieMultiplier, exercise.getCalorieMultiplier());
    }

    protected void checkCardio (int minutes, int level, double calorieMultiplier, Cardio cardio) {
        assertEquals(level, cardio.getLevel());
        assertEquals(minutes,cardio.getMinutes());
        assertEquals(calorieMultiplier,cardio.getCalorieMultiplier());
    }
}
