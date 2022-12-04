package model;

import model.strength.barbell.Deadlift;
import model.strength.barbell.Squat;
import model.strength.dumbbell.ShoulderPress;
import org.junit.jupiter.api.Test;
import model.strength.barbell.BenchPress;
import model.strength.cable.LatPulldown;
import model.strength.dumbbell.BicepsCurl;
import model.strength.machine.LegPress;

import static org.junit.jupiter.api.Assertions.*;
public class StrengthExerciseTest {

    private BenchPress benchPress;
    private LatPulldown latPulldown;
    private BicepsCurl bicepsCurl;
    private LegPress legPress;
    private Squat squat;
    private Deadlift deadlift;
    private ShoulderPress shoulderPress;

    private void setup() {
        benchPress = new BenchPress(225,1, 8);
        latPulldown = new LatPulldown(150,1,5);
        bicepsCurl = new BicepsCurl(50,1,6);
        legPress = new LegPress(500,1, 7);
        squat = new Squat(500,1, 7);
        deadlift = new Deadlift(100,4,5);
        shoulderPress = new ShoulderPress(100,4,5);
    }

    @Test
    public void testStrengthExercise() {
        setup();

        assertEquals(225,benchPress.getWeight());
        assertEquals(150,latPulldown.getWeight());
        assertEquals(50,bicepsCurl.getWeight());
        assertEquals(500,legPress.getWeight());

        assertEquals(8,benchPress.getIntensity());
        assertEquals(5,latPulldown.getIntensity());
        assertEquals(6,bicepsCurl.getIntensity());
        assertEquals(7,legPress.getIntensity());

        assertEquals(0.8,benchPress.getCalorieMultiplier());
        assertEquals(4,shoulderPress.getReps());

        assertEquals("Bench press",benchPress.getName());
        assertEquals("Lat pulldown",latPulldown.getName());
        assertEquals("Biceps curl",bicepsCurl.getName());
        assertEquals("Leg press",legPress.getName());
        assertEquals("Squat",squat.getName());
        assertEquals("Deadlift",deadlift.getName());
        assertEquals("Shoulder press",shoulderPress.getName());

        assertEquals(225 * 8 * 0.8,benchPress.getCalories());
        assertEquals(150 * 5 * 0.8,latPulldown.getCalories());
        assertEquals(50 * 6 * 0.8,bicepsCurl.getCalories());
        assertEquals(500 * 7 * 0.8,legPress.getCalories());
        assertEquals(100 * 4 * 5 * 0.8, deadlift.getCalories());
        assertEquals(100 * 4 * 5 * 0.8, shoulderPress.getCalories());

    }
}
