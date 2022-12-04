package model;



import model.cardio.*;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import model.strength.barbell.Squat;
import model.strength.machine.LegPress;

import static org.junit.jupiter.api.Assertions.*;

class WorkoutPlanTest {

    private WorkoutPlan plan1;
    private WorkoutPlan plan2;
    private IndoorBike indoorBike;
    private RowingMachine rowingMachine;
    private Squat squat;
    private LegPress legpress;

    @BeforeEach
    public void setup() {
        plan1 = new WorkoutPlan();
        plan2 = new WorkoutPlan();

        indoorBike = new IndoorBike(30, 5);
        rowingMachine = new RowingMachine(40, 4);

        squat = new Squat(300, 1, 7);
        legpress = new LegPress(200, 1, 6);

        plan1.addCardio(indoorBike);
        plan1.addCardio(rowingMachine);
        plan1.addCardio(rowingMachine);

        plan2.addStrengthExercise(squat);
        plan2.addStrengthExercise(legpress);
        plan2.addCardio(indoorBike);
        plan2.addCardio(rowingMachine);
    }

    @Test
    public void testWorkoutPlan() {
        setup();

        assertEquals(indoorBike, plan1.getCardios().get(0));
        assertEquals(rowingMachine, plan1.getCardios().get(1));
        assertEquals(rowingMachine, plan1.getCardios().get(2));

        assertEquals(indoorBike, plan2.getCardios().get(0));
        assertEquals(rowingMachine, plan2.getCardios().get(1));
        assertEquals(squat, plan2.getStrengthExercises().get(0));
        assertEquals(legpress, plan2.getStrengthExercises().get(1));

        assertEquals(30 * 5 * 0.8, plan1.getCardios().get(0).getCalories());
        assertEquals(40 * 4 * 0.8, plan2.getCardios().get(1).getCalories());

        assertEquals(300 * 7 * 0.8, plan2.getStrengthExercises().get(0).getCalories());
        assertEquals(200 * 6 * 0.8, plan2.getStrengthExercises().get(1).getCalories());

    }

    @Test
    public void testRemoveItem() {
        plan2.removeExercise(squat);
        assertEquals(1, plan2.getStrengthExercises().size());

        plan2.removeCardio(indoorBike);
        plan2.removeCardio(rowingMachine);
        assertEquals(0, plan2.getCardios().size());

        assertTrue(plan1.removeExerciseByName("Indoor bike"));
        assertFalse(plan1.removeExerciseByName("sdakjfas"));

        assertTrue(plan2.removeExerciseByName("Leg press"));
    }

    @Test
    public void testExerciseToJson() {
        JSONObject testJson = new JSONObject();

        testJson.put("name", plan2.getStrengthExercises().get(0).getName());
        testJson.put("weight", plan2.getStrengthExercises().get(0).getWeight());
        testJson.put("intensity", plan2.getStrengthExercises().get(0).getIntensity());
        testJson.put("reps", plan2.getStrengthExercises().get(0).getReps());
        testJson.put("calorie multiplier", plan2.getStrengthExercises().get(0).getCalorieMultiplier());
        testJson.put("total calories", 300 * 1 * 7 * 0.8);

        assertEquals(testJson.toString(),plan2.getStrengthExercises().get(0).toJson().toString());
    }

    @Test
    public void testCardioToJson() {
        JSONObject testJson = new JSONObject();
        testJson.put("name", plan1.getCardios().get(0).getName());
        testJson.put("minutes", plan1.getCardios().get(0).getMinutes());
        testJson.put("level", plan1.getCardios().get(0).getLevel());
        testJson.put("calorie multiplier", plan1.getCardios().get(0).getCalorieMultiplier());
        testJson.put("total calories", 30 * 5 * 0.8);
        assertEquals(testJson.toString(),plan1.getCardios().get(0).toJson().toString());
    }
}