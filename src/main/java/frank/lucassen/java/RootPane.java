package frank.lucassen.java;

import javafx.event.ActionEvent;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

public class RootPane extends GridPane {
    static boolean solve = true;

    public RootPane() {
        Questionnaire questionnaire = new Questionnaire();
        Alert noElementsAvailable = new Alert(Alert.AlertType.ERROR, "No elements available");

        // Buttons
        Button defaultPathButton = new Button("Set default File Path");
        defaultPathButton.addEventHandler(ActionEvent.ACTION, e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select default File");
            File file = fileChooser.showOpenDialog(new Stage());
            if (file == null) return;
            try (BufferedWriter out = new BufferedWriter(new FileWriter(".\\defaultSavePath"))) {
                out.write(file.getPath());
            } catch (IOException ex) {
                System.out.println("Couldn't write default Path");
            }
        });

        Button openButton = new Button("Open");
        openButton.addEventHandler(ActionEvent.ACTION, e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open file");
            File file = fileChooser.showOpenDialog(new Stage());
            if (file != null) questionnaire.loadFromFile(file);
        });

        ToggleButton showSolutionButton = new ToggleButton("show Solution (Y/N)");
        Button nextButton = new Button("_Next");
        nextButton.addEventHandler(ActionEvent.ACTION, e -> {
            if (questionnaire.size() < 1) noElementsAvailable.showAndWait();
            else questionnaire.showNext(showSolutionButton.isSelected());
        });

        Button selectQuestionButton = new Button("Select Question");
        selectQuestionButton.addEventHandler(ActionEvent.ACTION, e -> {
            SelectQuestionPane root = new SelectQuestionPane(questionnaire);
            Scene scene = new Scene(root, SelectQuestionPane.WIDTH, SelectQuestionPane.HEIGHT);

            Stage stage = new Stage();

            stage.setTitle("Question Select");
            stage.setScene(scene);
            stage.showAndWait();
            questionnaire.showNext(showSolutionButton.isSelected());
        });

        Button editQuestionsButton = new Button("Edit Questions");
        editQuestionsButton.addEventHandler(ActionEvent.ACTION, e -> {
            startQuestionPane(questionnaire);
            questionnaire.resetCurrent();
            questionnaire.showNext(showSolutionButton.isSelected());
        });

        Button helpButton = new Button("Help/Readme");
        helpButton.addEventHandler(ActionEvent.ACTION, e -> {
            File file = new File(".\\readme.txt");
            System.out.println(file);
            try {
                Runtime.getRuntime().exec(new String[]{"notepad.exe", "\"" + file.getAbsolutePath() + "\""});
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
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

        Button forwardButton = new Button("_Forward");
        forwardButton.addEventHandler(ActionEvent.ACTION, e -> {
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
        });

        Button randomQuestionButton = new Button("_random Question");
        randomQuestionButton.addEventHandler(ActionEvent.ACTION, e -> questionnaire.showRandomQuestion(showSolutionButton.isSelected()));


        // Label
        Label questionLabel = new Label("Question:");
        Label answerLabel = new Label("Answer:");

        // Texts
        TextField questionText = new TextField();
        TextArea answerText = new TextArea();
        questionText.setPrefWidth(0.9 * App.APP_WIDTH);
        answerText.setPrefWidth(0.9 * App.APP_WIDTH);
        answerText.setPrefHeight(0.6 * App.APP_HEIGHT);

        // put together
        // Buttons bottom left
        GridPane bottomLeftPane = new GridPane();
        bottomLeftPane.add(openButton, 1, 1);
        bottomLeftPane.add(selectQuestionButton, 1, 2);
        bottomLeftPane.add(editQuestionsButton, 1, 3);
        bottomLeftPane.add(defaultPathButton, 1, 4);
        bottomLeftPane.add(helpButton,1,5);
        GridPane.setHalignment(openButton, HPos.CENTER);
        GridPane.setHalignment(selectQuestionButton, HPos.CENTER);
        GridPane.setHalignment(editQuestionsButton, HPos.CENTER);
        GridPane.setHalignment(defaultPathButton, HPos.CENTER);
        GridPane.setHalignment(helpButton, HPos.CENTER);

        // labels
        GridPane.setHalignment(questionLabel, HPos.RIGHT);
        GridPane.setHalignment(answerLabel, HPos.RIGHT);
        GridPane.setValignment(answerLabel, VPos.TOP);

        // Buttons bottom right
        GridPane bottomRightPanel = new GridPane();
        bottomRightPanel.add(showSolutionButton, 3, 1);
        bottomRightPanel.add(solveButton, 3, 2);
        bottomRightPanel.add(previousButton, 2, 3);
        bottomRightPanel.add(nextButton, 3, 3);
        bottomRightPanel.add(forwardButton, 1, 2);
        bottomRightPanel.add(randomQuestionButton, 1, 1);
        GridPane.setHalignment(solveButton, HPos.CENTER);
        GridPane.setHalignment(previousButton, HPos.CENTER);
        GridPane.setHalignment(nextButton, HPos.CENTER);
        GridPane.setHalignment(forwardButton, HPos.CENTER);
        GridPane.setHalignment(randomQuestionButton, HPos.CENTER);
        bottomRightPanel.setAlignment(Pos.BOTTOM_RIGHT);


        addColumn(1, questionLabel, answerLabel, bottomLeftPane);
        addColumn(2, questionText, answerText, bottomRightPanel);

        // Add Fields to Questionnaire
        questionnaire.setQuestionField(questionText);
        questionnaire.setAnswerField(answerText);

        addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() != null) {
                switch (e.getCode()) {
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

        try (BufferedReader in = new BufferedReader(new FileReader(".\\defaultSavePath"))) {
            String path = in.readLine();
            File file = new File(path);
            if (file.isFile()) {
                questionnaire.loadFromFile(file);
                questionnaire.showNext(showSolutionButton.isSelected());
            }
        } catch (IOException ex) {
            System.out.println("Couldn't read default File");
        }
    }

    private void startQuestionPane(Questionnaire questionnaire) {
        EditQuestionPane root = new EditQuestionPane(questionnaire);
        Scene scene = new Scene(root, EditQuestionPane.WIDTH, EditQuestionPane.HEIGHT);

        Stage stage = new Stage();

        stage.setTitle("Question Edit");
        stage.setScene(scene);
        stage.showAndWait();
    }
}
