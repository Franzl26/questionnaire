package frank.lucassen.java;

import javafx.event.ActionEvent;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class RootPane extends GridPane {
    static boolean solve = false;

    public RootPane() {
        Questionnaire questionnaire = new Questionnaire();
        Alert noElementsAvailable = new Alert(Alert.AlertType.ERROR, "No elements available");

        // Buttons
        ToggleButton showSolutionButton = new ToggleButton("show Solution (Y/N)");
        Button nextButton = new Button("_Next");
        nextButton.addEventHandler(ActionEvent.ACTION, e -> {
            if (questionnaire.size() < 1) noElementsAvailable.showAndWait();
            else questionnaire.showNext(showSolutionButton.isSelected());
        });

        Button previousButton = new Button("_Previous");
        previousButton.addEventHandler(ActionEvent.ACTION, e -> {
            if (questionnaire.size() < 1) noElementsAvailable.showAndWait();
            else questionnaire.showPrevious(showSolutionButton.isSelected());
        });

        Button solveButton = new Button("_Solve");
        solveButton.addEventHandler(ActionEvent.ACTION, e -> {
            if (questionnaire.getCurrent() < 0) noElementsAvailable.showAndWait();
            else questionnaire.solveQuestion();
        });

        Button addButton = new Button("Add");
        addButton.addEventHandler(ActionEvent.ACTION, e -> questionnaire.addQuestion());

        Button openButton = new Button("Open");
        openButton.addEventHandler(ActionEvent.ACTION, e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open file");
            File file = fileChooser.showOpenDialog(new Stage());
            if (file != null) questionnaire.loadFromFile(file, showSolutionButton.isSelected());
        });

        Button saveButton = new Button("Save");
        saveButton.addEventHandler(ActionEvent.ACTION, e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save file");
            File file = fileChooser.showSaveDialog(new Stage());
            if (file != null) questionnaire.saveToFile(file);
        });

        Button convertButton = new Button("Convert");
        convertButton.addEventHandler(ActionEvent.ACTION, e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save file");
            File file = fileChooser.showOpenDialog(new Stage());
            if (file != null) questionnaire.convertFile(file, showSolutionButton.isSelected());
        });

        Button clearQuestionsButton = new Button("_Clear Questions");
        clearQuestionsButton.addEventHandler(ActionEvent.ACTION, e -> questionnaire.clearQuestions());


        // Label
        Label questionLabel = new Label("Question:");
        Label answerLabel = new Label("Answer:");

        // Texts
        TextField questionText = new TextField();
        TextArea answerText = new TextArea();
        questionText.setPrefWidth(1150);
        answerText.setPrefWidth(1150);
        answerText.setPrefHeight(230);

        // put together
        // Buttons button left
        GridPane bottomLeftPane = new GridPane();
        bottomLeftPane.add(addButton, 1, 1);
        bottomLeftPane.add(openButton, 1, 2);
        bottomLeftPane.add(saveButton, 1, 3);
        bottomLeftPane.add(convertButton, 1, 4);
        bottomLeftPane.add(clearQuestionsButton, 1, 5);
        GridPane.setHalignment(addButton, HPos.CENTER);
        GridPane.setHalignment(openButton, HPos.CENTER);
        GridPane.setHalignment(saveButton, HPos.CENTER);
        GridPane.setHalignment(convertButton, HPos.CENTER);
        GridPane.setHalignment(clearQuestionsButton, HPos.CENTER);

        // labels
        GridPane.setHalignment(questionLabel, HPos.RIGHT);
        GridPane.setHalignment(answerLabel, HPos.RIGHT);
        GridPane.setValignment(answerLabel, VPos.TOP);

        // Buttons bottom right
        GridPane bottomRightPanel = new GridPane();
        bottomRightPanel.add(showSolutionButton, 2, 1);
        bottomRightPanel.add(solveButton, 2, 2);
        bottomRightPanel.add(previousButton, 1, 3);
        bottomRightPanel.add(nextButton, 2, 3);
        GridPane.setHalignment(solveButton, HPos.CENTER);
        GridPane.setHalignment(previousButton, HPos.CENTER);
        GridPane.setHalignment(nextButton, HPos.CENTER);
        bottomRightPanel.setAlignment(Pos.BOTTOM_RIGHT);


        this.addColumn(1, questionLabel, answerLabel, bottomLeftPane);
        this.addColumn(2, questionText, answerText, bottomRightPanel);

        // Add Fields to Questionnaire
        questionnaire.setQuestionField(questionText);
        questionnaire.setAnswerField(answerText);

        addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() != null) {
                switch (e.getCode()) {
                    case C:
                        questionnaire.clearQuestions();
                        break;
                    case N:
                        if (questionnaire.size() < 1) noElementsAvailable.showAndWait();
                        else questionnaire.showNext(showSolutionButton.isSelected());
                        break;
                    case P:
                        if (questionnaire.size() < 1) noElementsAvailable.showAndWait();
                        else questionnaire.showPrevious(showSolutionButton.isSelected());
                        break;
                    case S:
                        if (questionnaire.getCurrent() < 0) noElementsAvailable.showAndWait();
                        else questionnaire.solveQuestion();
                        break;
                    case F:
                        if (showSolutionButton.isSelected()) {
                            questionnaire.showNext(true);
                        } else {
                            if (solve) {
                                solve = false;
                                questionnaire.solveQuestion();
                            } else {
                                solve = true;
                                questionnaire.showNext(false);
                            }
                        }
                        break;
                    case R:
                        questionnaire.showRandomQuestion(showSolutionButton.isSelected());
                        break;
                }
            }
        });
    }
}
