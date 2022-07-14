package frank.lucassen.java;

import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;


public class SelectQuestionPane extends GridPane {
    public static final double HEIGHT = 600;
    public static final double WIDTH = 1300;



    public SelectQuestionPane(Questionnaire questionnaire) {
        // get Questions
        ArrayList<Question> questions = questionnaire.getQuestions();
        HashMap<Button, Integer> map = new HashMap<>();
        ListView<Button> list = new ListView<>();

        list.getItems().clear();
        int i = 0;
        for (Question question : questions) {
            Button button = new Button(question.getQuestion());
            list.getItems().add(button);
            map.put(button, i++);
            button.addEventHandler(ActionEvent.ACTION, e -> {
                questionnaire.setCurrent(map.get(e.getSource()) - 1);
                ((Stage) getScene().getWindow()).close();
            });
        }


        // Add Button
        Button cancelButton = new Button("Cancel");
        cancelButton.addEventHandler(ActionEvent.ACTION, e -> ((Stage) getScene().getWindow()).close());

        // add List
        add(list, 1, 1);
        list.setPrefWidth(WIDTH);
        list.setPrefHeight(0.9 * HEIGHT);

        add(cancelButton,1,2);
        GridPane.setHalignment(cancelButton, HPos.RIGHT);
        GridPane.setValignment(cancelButton, VPos.CENTER);
    }
}
