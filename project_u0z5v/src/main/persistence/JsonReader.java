package persistence;

import model.WorkoutPlan;
import model.cardio.IndoorBike;
import model.cardio.RowingMachine;
import model.cardio.StairClimber;
import model.cardio.Treadmill;
import model.strength.barbell.BenchPress;
import model.strength.barbell.Deadlift;
import model.strength.barbell.Squat;
import model.strength.cable.LatPulldown;
import model.strength.dumbbell.BicepsCurl;
import model.strength.dumbbell.ShoulderPress;
import model.strength.machine.LegPress;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;


public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workout plan from file and returns it;
    // throws IOException if an error occurs reading data from file
    public WorkoutPlan read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWorkoutPlan(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses workout plan from JSON object and returns it
    private WorkoutPlan parseWorkoutPlan(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        WorkoutPlan plan = new WorkoutPlan();
        addStrengthExercises(plan, jsonObject);
        addCardios(plan,jsonObject);
        return plan;
    }

    // MODIFIES: plan
    // EFFECTS: parses exercises from JSON object and adds them to plan
    private void addStrengthExercises(WorkoutPlan plan, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("exercises");
        for (Object json : jsonArray) {
            JSONObject nextExercise = (JSONObject) json;
            addExercise(plan, nextExercise);
        }
    }

    // MODIFIES: plan
    // EFFECTS: parses cardios from JSON object and adds them to plan
    private void addCardios(WorkoutPlan plan, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("cardios");
        for (Object json : jsonArray) {
            JSONObject nextExercise = (JSONObject) json;
            addCardio(plan, nextExercise);
        }
    }

    // MODIFIES: plan
    // EFFECTS: parses exercise from JSON object and adds it to workout plan
    private void addExercise(WorkoutPlan plan, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int pounds = jsonObject.getInt("weight");
        int intensity = jsonObject.getInt("intensity");
        int reps = jsonObject.getInt("reps");
        addBarbell(plan, name, pounds, intensity, reps);
        addCable(plan, name, pounds, intensity, reps);
        addDumbell(plan, name, pounds, intensity, reps);
        addMachine(plan, name, pounds, intensity, reps);
    }

    public void addBarbell(WorkoutPlan plan, String name, int pounds, int intensity, int reps) {
        if (name.equals("Bench press")) {
            BenchPress exercise = new BenchPress(pounds, intensity, reps);
            plan.addStrengthExercise(exercise);
        } else if (name.equals("Deadlift")) {
            Deadlift exercise = new Deadlift(pounds, intensity, reps);
            plan.addStrengthExercise(exercise);
        } else if (name.equals("Squat")) {
            Squat exercise = new Squat(pounds, intensity, reps);
            plan.addStrengthExercise(exercise);
        }
    }

    public void addCable(WorkoutPlan plan, String name, int pounds, int intensity, int reps) {
        if (name.equals("Lat pulldown")) {
            LatPulldown exercise = new LatPulldown(pounds, intensity, reps);
            plan.addStrengthExercise(exercise);
        }
    }

    public void addDumbell(WorkoutPlan plan, String name, int pounds, int intensity, int reps) {
        if (name.equals("Shoulder press")) {
            ShoulderPress exercise = new ShoulderPress(pounds, intensity, reps);
            plan.addStrengthExercise(exercise);
        } else if (name.equals("Biceps curl")) {
            BicepsCurl exercise = new BicepsCurl(pounds, intensity, reps);
            plan.addStrengthExercise(exercise);
        }
    }

    public void addMachine(WorkoutPlan plan, String name, int pounds, int intensity, int reps) {
        if (name.equals("Leg press")) {
            LegPress exercise = new LegPress(pounds, intensity, reps);
            plan.addStrengthExercise(exercise);
        }
    }

    private void addCardio(WorkoutPlan plan, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int minutes = jsonObject.getInt("minutes");
        int level = jsonObject.getInt("level");
        if (name.equals("Indoor bike")) {
            IndoorBike cardio = new IndoorBike(minutes, level);
            plan.addCardio(cardio);
        } else if (name.equals("Rowing machine")) {
            RowingMachine cardio = new RowingMachine(minutes, level);
            plan.addCardio(cardio);
        } else if (name.equals("Stair climber")) {
            StairClimber cardio = new StairClimber(minutes, level);
            plan.addCardio(cardio);
        } else if (name.equals("Treadmill")) {
            Treadmill cardio = new Treadmill(minutes, level);
            plan.addCardio(cardio);
        }
    }
}
