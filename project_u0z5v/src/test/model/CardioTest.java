package model;


import model.cardio.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CardioTest {

    private IndoorBike indoorBike;
    private StairClimber stairClimber;
    private Treadmill treadmill;
    private RowingMachine rowingMachine;

    public void setup(){
        indoorBike = new IndoorBike(50,3);
        stairClimber = new StairClimber(20,6);
        treadmill = new Treadmill(15,4);
        rowingMachine = new RowingMachine(10,10);
    }

    @Test
    public void testCardio() {
        setup();

        assertEquals(50,indoorBike.getMinutes());
        assertEquals(20,stairClimber.getMinutes());
        assertEquals(15,treadmill.getMinutes());
        assertEquals(10,rowingMachine.getMinutes());

        assertEquals(3, indoorBike.getLevel());
        assertEquals(6, stairClimber.getLevel());
        assertEquals(4, treadmill.getLevel());
        assertEquals(10, rowingMachine.getLevel());



        assertEquals("Indoor bike", indoorBike.getName());
        assertEquals("Stair climber", stairClimber.getName());
        assertEquals("Treadmill", treadmill.getName());
        assertEquals("Rowing machine", rowingMachine.getName());

        assertEquals(50 * 3 * 0.8,indoorBike.getCalories());
        assertEquals(20 * 6 * 0.8,stairClimber.getCalories());
        assertEquals(15 * 4 * 0.8,treadmill.getCalories());
        assertEquals(10 * 10 * 0.8,rowingMachine.getCalories());
    }


}
