package frank.lucassen.java;

import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import static frank.lucassen.java.Questionnaire.*;

public class EditQuestionPane extends GridPane {
    public static final double HEIGHT = 600;
    public static final double WIDTH = 1300;

    private final ArrayList<Question> questions;
    private final ListView<ToggleButton> list = new ListView<>();
    private final HashMap<ToggleButton, Question> map = new HashMap<>();

    public EditQuestionPane(Questionnaire questionnaire) {
        // get Questions
        questions = questionnaire.getQuestions();

        updateList();


        // Add Buttons
        Button deleteSelectedButton = new Button("Delete Selected");
        deleteSelectedButton.addEventHandler(ActionEvent.ACTION, e -> {
            for (ToggleButton button : list.getItems()) {
                if (button.isSelected()) {
                    questions.remove(map.get(button));
                }
            }
            updateList();
        });

        Button deleteAllButton = new Button("Delete All");
        deleteAllButton.addEventHandler(ActionEvent.ACTION, e -> {
            questions.clear();
            updateList();
        });

        Button openButton = new Button("Add from Save");
        openButton.addEventHandler(ActionEvent.ACTION, e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open file");
            File file = fileChooser.showOpenDialog(new Stage());
            if (file != null) loadFromFile(file, questions);
            updateList();
        });

        Button saveButton = new Button("Save");
        saveButton.addEventHandler(ActionEvent.ACTION, e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save file");
            File file = fileChooser.showSaveDialog(new Stage());
            if (file != null) saveToFile(file, questions);
            updateList();
        });

        Button convertButton = new Button("Add from Text");
        convertButton.addEventHandler(ActionEvent.ACTION, e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save file");
            File file = fileChooser.showOpenDialog(new Stage());
            if (file != null) convertFile(file, questions);
            updateList();
        });

        Button confirmButton = new Button("Confirm changes");
        confirmButton.addEventHandler(ActionEvent.ACTION, e -> {
            questionnaire.setQuestions(questions);
            ((Stage) getScene().getWindow()).close();
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.addEventHandler(ActionEvent.ACTION, e -> ((Stage) getScene().getWindow()).close());

        // add List
        GridPane.setColumnSpan(list, 2);
        add(list, 1, 1);
        list.setPrefWidth(WIDTH);
        list.setPrefHeight(0.9 * HEIGHT);

        // add bottom left buttons
        GridPane boxLeft = new GridPane();
        boxLeft.add(openButton,1,1);
        boxLeft.add(convertButton,2,1);
        boxLeft.add(saveButton,3,1);
        boxLeft.add(deleteSelectedButton,4,1);
        boxLeft.add(deleteAllButton,5,1);

        GridPane.setValignment(boxLeft, VPos.CENTER);
        add(boxLeft, 1, 2);

        // add bottom right buttons
        GridPane boxRight = new GridPane();
        boxRight.add(cancelButton,1,1);
        boxRight.add(confirmButton,2,1);
        GridPane.setHalignment(confirmButton, HPos.RIGHT);
        GridPane.setHalignment(boxRight, HPos.RIGHT);
        GridPane.setValignment(boxRight, VPos.CENTER);
        add(boxRight, 2, 2);

    }

    private void updateList() {
        list.getItems().clear();
        map.clear();
        questions.forEach(e -> {
            ToggleButton button = new ToggleButton(e.getQuestion());
            list.getItems().add(button);
            map.put(button, e);
        });
    }
}
