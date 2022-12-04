package model;

import model.cardio.Cardio;
import model.strength.StrengthExercise;
import org.json.JSONObject;

import org.json.JSONArray;
import persistence.Writable;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//
public class WorkoutPlan implements Writable {

    private List<StrengthExercise> strengthExercises;
    private List<Cardio> cardios;
    private JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
    private JsonReader jsonReader = new JsonReader(JSON_STORE);
    private WorkoutPlan plan;
    private static final String JSON_STORE = "./data/workoutplan.json";


    public WorkoutPlan() {
        strengthExercises = new ArrayList<>();
        cardios = new ArrayList<>();
    }

    //getters
    public List<StrengthExercise> getStrengthExercises() {
        return strengthExercises;
    }

    public List<Cardio> getCardios() {
        return cardios;
    }

    // MODIFIES: this
    // EFFECTS: adds strength exercise to this list
    public void addStrengthExercise(StrengthExercise strengthExercise) {
        EventLog.getInstance().logEvent(new Event("Exercise " + strengthExercise.getName() + " added."));
        strengthExercises.add(strengthExercise);
    }

    // MODIFIES: this
    // EFFECTS: adds cardio to this list
    public void addCardio(Cardio cardio) {
        EventLog.getInstance().logEvent(new Event("Cardio " + cardio.getName() + " added."));
        cardios.add(cardio);
    }

    // REQUIRES: the given strength exercise must already be in the list
    // MODIFIES: this
    // EFFECTS: removes exercise from this gym
    public void removeExercise(StrengthExercise  strengthExercise) {
        EventLog.getInstance().logEvent(new Event("Exercise " + strengthExercise.getName() + " removed."));
        strengthExercises.remove(strengthExercise);
    }

    // REQUIRES: the given cardio must already be in the list
    // MODIFIES: this
    // EFFECTS: removes cardio from this list
    public void removeCardio(Cardio cardio) {
        EventLog.getInstance().logEvent(new Event("Cardio " + cardio.getName() + " removed."));
        cardios.remove(cardio);
    }

    public Boolean removeExerciseByName(String name) {
        boolean temp = false;
        for (StrengthExercise strengthExercise : strengthExercises) {
            if (name.equals(strengthExercise.getName())) {
                strengthExercises.remove(strengthExercise);
                EventLog.getInstance().logEvent(new Event("Exercise "
                        + strengthExercise.getName() + " removed."));
                temp = true;
                break;
            }
        }
        for (Cardio cardio : cardios) {
            if (name.equals(cardio.getName())) {
                cardios.remove(cardio);
                EventLog.getInstance().logEvent(new Event("Cardio " + cardio.getName() + " removed."));
                temp = true;
                break;
            }
        }
        return temp;
    }


    public void savePlan() {
        try {
            jsonWriter.open();
            jsonWriter.write(this);
            jsonWriter.close();
            EventLog.getInstance().logEvent(new Event("Saved plan to" + JSON_STORE));
        } catch (FileNotFoundException e) {
            EventLog.getInstance().logEvent(new Event("Unable to write to file: " + JSON_STORE));
        }
    }

    public WorkoutPlan loadPlan() {
        try {
            plan = jsonReader.read();
            EventLog.getInstance().logEvent(new Event("Plan loaded from file" + JSON_STORE));
        } catch (IOException e) {
            EventLog.getInstance().logEvent(new Event("Unable to read from file: " + JSON_STORE));
        }
        return plan;
    }

    public void logClear() {
        EventLog.getInstance().logEvent(new Event("Text box cleared."));
    }

    public void logPrint() {
        EventLog.getInstance().logEvent(new Event("Printed plan."));
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", "workout plan");
        json.put("exercises", strengthExercisesToJson());
        json.put("cardios", cardiosToJson());
        return json;
    }

    // EFFECTS: returns exercises in this workout plan as a JSON array
    private JSONArray strengthExercisesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (StrengthExercise strengthExercise : strengthExercises) {
            jsonArray.put(strengthExercise.toJson());
        }

        return jsonArray;
    }

    private JSONArray cardiosToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Cardio cardio : cardios) {
            jsonArray.put(cardio.toJson());
        }

        return jsonArray;
    }


}
