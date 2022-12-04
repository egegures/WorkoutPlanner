package Persistence;

import model.WorkoutPlan;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest{

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            WorkoutPlan plan = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testReaderWorkoutPlan() {
        JsonReader reader = new JsonReader("./data/testReaderWorkoutPlan.json");
        try {
            WorkoutPlan plan = reader.read();

            assertEquals(7, plan.getStrengthExercises().size());
            checkExercise(100,15,5,0.8,plan.getStrengthExercises().get(0));
            checkExercise(100,15,5,0.8,plan.getStrengthExercises().get(1));
            checkExercise(100,15,5,0.8,plan.getStrengthExercises().get(2));
            checkExercise(100,15,5,0.8,plan.getStrengthExercises().get(3));
            checkExercise(100,15,5,0.8,plan.getStrengthExercises().get(4));
            checkExercise(100,15,5,0.8,plan.getStrengthExercises().get(5));
            checkExercise(100,15,5,0.8,plan.getStrengthExercises().get(6));

            checkCardio(10,20,0.8,plan.getCardios().get(0));
            checkCardio(10,20,0.8,plan.getCardios().get(1));
            checkCardio(10,20,0.8,plan.getCardios().get(2));
            checkCardio(10,20,0.8,plan.getCardios().get(3));

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
