package Persistence;

import model.WorkoutPlan;
import model.cardio.*;
import model.strength.barbell.BenchPress;
import model.strength.barbell.Deadlift;
import model.strength.barbell.Squat;
import model.strength.cable.LatPulldown;
import model.strength.dumbbell.BicepsCurl;
import model.strength.dumbbell.ShoulderPress;
import model.strength.machine.LegPress;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            WorkoutPlan plan = new WorkoutPlan();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyPlan() {
        try {
            WorkoutPlan plan = new WorkoutPlan();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyPlan.json");
            writer.open();
            writer.write(plan);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyPlan.json");
            plan = reader.read();
            assertEquals(0, plan.getCardios().size() + plan.getStrengthExercises().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralPlan() {
        try {
            WorkoutPlan plan = new WorkoutPlan();

            plan.addStrengthExercise(new BenchPress(100,15,5));
            plan.addStrengthExercise(new Deadlift(100,15,5));
            plan.addStrengthExercise(new Squat(100,15,5));
            plan.addStrengthExercise(new LatPulldown(100,15,5));
            plan.addStrengthExercise(new BicepsCurl(100,15,5));
            plan.addStrengthExercise(new ShoulderPress(100,15,5));
            plan.addStrengthExercise(new LegPress(100,15,5));

            plan.addCardio(new IndoorBike(10,20));
            plan.addCardio(new RowingMachine(10,20));
            plan.addCardio(new StairClimber(10,20));
            plan.addCardio(new Treadmill(10,20));

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralPlan.json");
            writer.open();
            writer.write(plan);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralPLan.json");
            plan = reader.read();

            assertEquals(7, plan.getStrengthExercises().size());
            assertEquals(4, plan.getCardios().size());

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
            fail("Exception should not have been thrown");
        }
    }
}
