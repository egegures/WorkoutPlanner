package ui;

import model.Event;
import model.EventLog;
import model.WorkoutPlan;
import model.cardio.*;
import model.strength.StrengthExercise;
import model.strength.barbell.*;
import model.strength.cable.*;
import model.strength.dumbbell.*;
import model.strength.machine.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

// GUI for workout plan
public class WorkoutPlanGUI extends JFrame {

    public static final int WIDTH = 700;
    public static final int HEIGHT = 600;

    JButton saveToFile;
    JButton loadFromFile;
    JButton addExercise;
    JButton clear;
    JButton print;
    JButton removeExercise;

    JTextField leftTextField;
    JTextField middleTextField;
    JTextField rightTextField;

    JLabel leftLabel;
    JLabel middleLabel;
    JLabel rightLabel;

    JFrame frame;

    JMenuBar menuBar;
    JMenu  photo;
    JMenuItem photoMenuItem;

    ImageIcon quote1;
    ImageIcon quote2;
    ImageIcon quote3;
    ImageIcon quote4;
    JTextArea textArea;
    private JDesktopPane desktop;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/workoutplan.json";
    StringBuilder stringBuilder;
    int poundsAndLevel;
    int repsAndMinutes;
    int intensity;
    List<String> exerciseNames;

    JComboBox barbellList;
    JComboBox cableList;
    JComboBox dumbbellList;
    JComboBox machineList;
    JComboBox cardioList;
    JComboBox removeList;


    private WorkoutPlan plan;

    //Initializes GUI
    public WorkoutPlanGUI() {
        plan = new WorkoutPlan();
        desktop = new JDesktopPane();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        setContentPane(desktop);

        setTitle("Workout Planner");
        setSize(WIDTH, HEIGHT);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setDesktop();
        centreOnScreen();
        setVisible(true);

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                Iterator<Event> iterator = EventLog.getInstance().iterator();
                while (iterator.hasNext()) {
                    Event tempEvent = iterator.next();
                    System.out.println(tempEvent.toString() + "\n");
                }
                System.exit(0);
            }
        });
    }

    //set of helper functions to initialize GUI
    private void setDesktop() {
        setTextFields();
        setComboBoxes();
        setButtons();
        setLabels();
        setTextArea();
        setMenu();
    }

    //Creates buttons
    private void setButtons() {
        saveAndLoadButtons();
        addAndRemoveButtons();
        clearAndPrintButtons();
        buttonSettings();

        removeExercise.setSize(100,30);
        removeExercise.setLocation(removeList.getX(),removeList.getY() - 30);
        removeExercise.setVisible(true);

    }

    //Creates add and remove buttons
    private void addAndRemoveButtons() {
        addExercise = new JButton("Add");
        addExercise.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addExercise();
            }
        });

        removeExercise = new JButton("Remove");
        removeExercise.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (0 == exerciseNames.size()) {
                    JOptionPane.showMessageDialog(frame, "Nothing to remove.");
                } else if (0 == removeList.getSelectedIndex()) {
                    textArea.append("");
                } else {
                    textArea.append("\nRemoved exercise from the plan.");
                    plan.removeExerciseByName(removeList.getSelectedItem().toString());
                    updateRemoveList();
                }

            }
        });
    }

    //Creates save and load buttons
    private void saveAndLoadButtons() {
        saveToFile = new JButton("Save to file");
        saveToFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveButton();
            }
        });

        loadFromFile = new JButton("Load from file");
        loadFromFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadButton();
            }
        });
    }

    //Creates clear and print buttons
    private void clearAndPrintButtons() {
        clear = new JButton("Clear");
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText("");
                plan.logClear();
            }
        });

        print = new JButton("Print");
        print.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                plan.logPrint();
                if (textArea.equals("")) {
                    textArea.append(printWorkoutPlan() + "\n");
                } else {
                    textArea.append("\n" + "\n" + printWorkoutPlan() + "\n");
                }
            }

        });
    }

    //sets button sizes and locations
    private void buttonSettings() {

        desktop.add(saveToFile);
        desktop.add(loadFromFile);
        desktop.add(addExercise);
        desktop.add(clear);
        desktop.add(print);
        desktop.add(removeExercise);

        saveToFile.setSize(115,30);
        saveToFile.setLocation(10,400);
        saveToFile.setVisible(true);

        loadFromFile.setSize(115,30);
        loadFromFile.setLocation(10,430);
        loadFromFile.setVisible(true);

        addExercise.setSize(70,30);
        addExercise.setLocation(middleTextField.getX() + 15,middleTextField.getY() + 40);
        addExercise.setVisible(true);

        clear.setSize(70,30);
        clear.setLocation(10,490);
        clear.setVisible(true);

        print.setSize(70,30);
        print.setLocation(10,clear.getY() - 30);
        print.setVisible(true);
    }

    private void loadButton() {
        loadWorkoutPlan();
        if (textArea.getText().equals("")) {
            textArea.append("Loaded plan from file.");
        } else {
            textArea.append("\nLoaded plan from file.");
        }
        updateRemoveList();
    }

    //MODIFIES: plan
    //EFFECTS: Creates a new exercise with the inputs given then logs it.
    private void addExercise() {

        try {
            getInputs();
            addWorkout();
            if (textArea.getText().equals("")) {
                textArea.append("Exercise added to plan!");
            } else {
                textArea.append("\nExercise added to plan!");
            }

            resetFields();

            updateRemoveList();

        } catch (NoSelectionMadeException e) {
            JOptionPane.showMessageDialog(frame, "Please select an exercise.");
        } catch (EmptyTextFieldException e) {
            JOptionPane.showMessageDialog(frame, "Please fill all inputs.");
        } catch (MoreThanOneChoiceSelectedException e) {
            JOptionPane.showMessageDialog(frame, "Please select only one exercise.");
        }
    }

    //EFFECTS: Saves inputs taken from text fields. Throws an EmptyTextFieldException if at least one of the fields is
    //empty.
    private void getInputs() throws EmptyTextFieldException {

        if (leftTextField.getText().equals("") || leftTextField.getText().equals("")
                || leftTextField.getText().equals("")) {
            throw new EmptyTextFieldException();
        } else {
            poundsAndLevel = Integer.parseInt(leftTextField.getText());
            repsAndMinutes = Integer.parseInt(middleTextField.getText());
            intensity = Integer.parseInt(rightTextField.getText());
        }
    }

    //EFFECTS: Resets the Text fields and Combo boxes to their initial state.
    private void resetFields() {
        leftTextField.setText("");
        middleTextField.setText("");
        rightTextField.setText("");


        barbellList.setSelectedIndex(0);
        cableList.setSelectedIndex(0);
        dumbbellList.setSelectedIndex(0);
        machineList.setSelectedIndex(0);
        cardioList.setSelectedIndex(0);
    }

    //Action when save button is clicked. Saves the workout plan, then logs it.
    private void saveButton() {
        saveWorkoutPlan();
        if (textArea.getText().equals("")) {
            textArea.append("Saved plan.");
        } else {
            textArea.append("\nSaved plan.");
        }
    }

    //Initializes combo boxes.
    private void setComboBoxes() {
        String[] barbell = {"Barbell","Bench press","Deadlift","Squat"};
        String[] cable = {"Cable", "Lat pulldown"};
        String[] dumbbell = {"Dumbbell", "Biceps curl", "Shoulder press"};
        String[] machine = {"Machine", "Leg press"};
        String[] cardio = {"Cardio","Indoor bike","Rowing machine","Stair climber","Treadmill"};

        barbellList = new JComboBox(barbell);
        cableList = new JComboBox(cable);
        dumbbellList = new JComboBox(dumbbell);
        machineList = new JComboBox(machine);
        cardioList = new JComboBox(cardio);
        removeList = new JComboBox(getExerciseNames().toArray());

        comboBoxSettings();

        removeList.setSize(100,30);
        removeList.setLocation(210,410);
        removeList.setVisible(true);
    }

    //Helper for initializing combo boxes.
    private void comboBoxSettings() {
        desktop.add(barbellList);
        desktop.add(cableList);
        desktop.add(dumbbellList);
        desktop.add(machineList);
        desktop.add(cardioList);
        desktop.add(removeList);

        barbellList.setSize(100,30);
        barbellList.setLocation(10,10);
        barbellList.setVisible(true);

        cableList.setSize(100,30);
        cableList.setLocation(110,10);
        cableList.setVisible(true);

        dumbbellList.setSize(100,30);
        dumbbellList.setLocation(210,10);
        dumbbellList.setVisible(true);

        machineList.setSize(100,30);
        machineList.setLocation(310,10);
        machineList.setVisible(true);

        cardioList.setSize(100,30);
        cardioList.setLocation(410,10);
        cardioList.setVisible(true);
    }

    //Helper for initializing text fields.
    private void setTextFields() {
        leftTextField = new JTextField();
        middleTextField = new JTextField();
        rightTextField = new JTextField();

        desktop.add(leftTextField);
        desktop.add(middleTextField);
        desktop.add(rightTextField);

        leftTextField.setSize(100,30);
        leftTextField.setLocation(10,150);
        leftTextField.setVisible(true);

        middleTextField.setSize(100,30);
        middleTextField.setLocation(leftTextField.getX() + 100,leftTextField.getY());
        middleTextField.setVisible(true);

        rightTextField.setSize(100,30);
        rightTextField.setLocation(leftTextField.getX() + 200,leftTextField.getY());
        rightTextField.setVisible(true);
    }

    //Initializes and places labels.
    private void setLabels() {
        leftLabel = new JLabel("weight/level");
        middleLabel = new JLabel("reps/minutes");
        rightLabel = new JLabel("intensity");

        desktop.add(leftLabel);
        desktop.add(middleLabel);
        desktop.add(rightLabel);

        leftLabel.setSize(100,30);
        leftLabel.setLocation(leftTextField.getX() + 10,leftTextField.getY() - 25);
        leftLabel.setVisible(true);

        middleLabel.setSize(100,30);
        middleLabel.setLocation(middleTextField.getX() + 10,middleTextField.getY() - 25);
        middleLabel.setVisible(true);

        rightLabel.setSize(100,30);
        rightLabel.setLocation(rightTextField.getX() + 20,rightTextField.getY() - 25);
        rightLabel.setVisible(true);

    }

    //Initializes the text area.
    private void setTextArea() {
        textArea = new JTextArea();

        Border border = BorderFactory.createLineBorder(Color.BLACK);
        textArea.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        desktop.add(textArea);

        textArea.setSize(300,450);
        textArea.setLocation(350,100);
        rightLabel.setVisible(true);

        textArea.setEditable(false);
    }

    public void setMenu() {
        menuBar = new JMenuBar();
        photo = new JMenu("Quote");
        photoMenuItem = new JMenuItem(new AddPhotoMenuAction());
        photo.add(photoMenuItem);
        menuBar.add(photo);
        setJMenuBar(menuBar);

    }

    //EFFECTS: Prints the workout plan and total calories. Saves them in a StringBuilder, then returns it as a string.
    public String printWorkoutPlan() {
        double temp = 0;
        stringBuilder = new StringBuilder();
        stringBuilder.append("Your workout plan and their calories: \n");
        for (Cardio cardio : plan.getCardios()) {
            stringBuilder.append(cardio.getName() + " " + cardio.getCalories() + "\n");
            temp = temp + cardio.getCalories();
        }
        for (StrengthExercise strengthExercise : plan.getStrengthExercises()) {
            stringBuilder.append(strengthExercise.getName() + " " + strengthExercise.getCalories() + "\n");
            temp = temp + strengthExercise.getCalories();
        }
        stringBuilder.append("Total calories for this plan: " + temp);

        return stringBuilder.toString();
    }

    // Helper to centre main application window on desktop
    private void centreOnScreen() {
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        setLocation((width - getWidth()) / 2, (height - getHeight()) / 2);
    }

    //Saves the Workout plan as a json file.
    private void saveWorkoutPlan() {
        plan.savePlan();
    }

    // MODIFIES: this
    // EFFECTS: loads workout plan from file
    private void loadWorkoutPlan() {
        plan = plan.loadPlan();
    }

    //Adds a new exercise according to
    private void addWorkout() throws NoSelectionMadeException, MoreThanOneChoiceSelectedException {
        if (checkComboBoxes() > 1) {
            throw new MoreThanOneChoiceSelectedException();
        } else if (1 == barbellList.getSelectedIndex()) {
            plan.addStrengthExercise(new BenchPress(poundsAndLevel,repsAndMinutes,intensity));
        } else if (2 == barbellList.getSelectedIndex()) {
            plan.addStrengthExercise(new Deadlift(poundsAndLevel,repsAndMinutes,intensity));
        } else if (3 == barbellList.getSelectedIndex()) {
            plan.addStrengthExercise(new Squat(poundsAndLevel,repsAndMinutes,intensity));
        } else if (1 == cableList.getSelectedIndex()) {
            plan.addStrengthExercise(new LatPulldown(poundsAndLevel,repsAndMinutes,intensity));
        } else if (1 == dumbbellList.getSelectedIndex()) {
            plan.addStrengthExercise(new BicepsCurl(poundsAndLevel,repsAndMinutes,intensity));
        } else if (2 == dumbbellList.getSelectedIndex()) {
            plan.addStrengthExercise(new ShoulderPress(poundsAndLevel,repsAndMinutes,intensity));
        } else if (1 == machineList.getSelectedIndex()) {
            plan.addStrengthExercise(new LegPress(poundsAndLevel,repsAndMinutes,intensity));
        } else if (1 == cardioList.getSelectedIndex() || 2 == cardioList.getSelectedIndex()
                || 3 == cardioList.getSelectedIndex()) {
            checkCardio();
        } else {
            throw new NoSelectionMadeException();
        }
    }

    private void checkCardio() {
        if (1 == cardioList.getSelectedIndex()) {
            plan.addCardio(new IndoorBike(poundsAndLevel,repsAndMinutes));
        } else if (2 == cardioList.getSelectedIndex()) {
            plan.addCardio(new RowingMachine(poundsAndLevel,repsAndMinutes));
        } else if (3 == cardioList.getSelectedIndex()) {
            plan.addCardio(new StairClimber(poundsAndLevel,repsAndMinutes));
        } else if (4 == cardioList.getSelectedIndex()) {
            plan.addCardio(new Treadmill(poundsAndLevel,repsAndMinutes));
        }
    }

    private List<String> getExerciseNames() {
        exerciseNames = new ArrayList<>();
        exerciseNames.clear();
        for (StrengthExercise strengthExercise: plan.getStrengthExercises()) {
            exerciseNames.add(strengthExercise.getName());
        }
        for (Cardio cardio: plan.getCardios()) {
            exerciseNames.add(cardio.getName());
        }
        return exerciseNames;
    }

    private void updateRemoveList() {
        removeList.removeAllItems();
        removeList.addItem("Remove");
        for (String s : getExerciseNames()) {
            removeList.addItem(s);
        }
    }

    private class AddPhotoMenuAction extends AbstractAction {

        AddPhotoMenuAction() {
            super("show a random motivation quote");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            Random rand = new Random();
            quote1 = new ImageIcon(getClass().getResource("quote1.png"));
            quote2 = new ImageIcon(getClass().getResource("quote2.png"));
            quote3 = new ImageIcon(getClass().getResource("quote3.png"));
            quote4 = new ImageIcon(getClass().getResource("quote4.png"));

            List<ImageIcon> images = new ArrayList<>();
            images.add(quote1);
            images.add(quote2);
            images.add(quote3);
            images.add(quote4);


            ImageIcon randomImage = images.get(rand.nextInt(images.size()));
            JOptionPane.showMessageDialog(null,"","Today's quote",
                    JOptionPane.INFORMATION_MESSAGE,randomImage);
        }

    }

    public int checkComboBoxes() {
        int i = 0;
        if (0 != barbellList.getSelectedIndex()) {
            i++;
        }
        if (0 != cableList.getSelectedIndex()) {
            i++;
        }
        if (0 != dumbbellList.getSelectedIndex()) {
            i++;
        }
        if (0 != machineList.getSelectedIndex()) {
            i++;
        }
        if (0 != cardioList.getSelectedIndex()) {
            i++;
        }
        return i;
    }


    private class NoSelectionMadeException extends Exception {
    }

    private class EmptyTextFieldException extends Exception {
    }

    private class MoreThanOneChoiceSelectedException extends Exception {

    }

}


