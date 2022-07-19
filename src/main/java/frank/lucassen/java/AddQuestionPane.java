package frank.lucassen.java;

import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class AddQuestionPane extends GridPane {
    public AddQuestionPane(ArrayList<Question> questions) {
        // Label
        Label questionLabel = new Label("Question:");
        Label answerLabel = new Label("Answer:");
        GridPane.setHalignment(questionLabel, HPos.RIGHT);
        GridPane.setHalignment(answerLabel, HPos.RIGHT);
        GridPane.setValignment(answerLabel, VPos.TOP);

        // Texts
        TextField questionText = new TextField();
        TextArea answerText = new TextArea();
        questionText.setPrefWidth(0.9 * App.APP_WIDTH);
        answerText.setPrefWidth(0.9 * App.APP_WIDTH);
        answerText.setPrefHeight(0.6 * App.APP_HEIGHT);


        // Buttons
        Button addButton = new Button("Add");
        addButton.addEventHandler(ActionEvent.ACTION, e -> {
            questions.add(new Question(questionText.getText(), answerText.getText()));
            questionText.clear();
            answerText.clear();
        });
        GridPane.setHalignment(addButton, HPos.LEFT);

        Button cancelButton = new Button("Cancel");
        cancelButton.addEventHandler(ActionEvent.ACTION, e -> ((Stage) getScene().getWindow()).close());
        GridPane.setHalignment(cancelButton, HPos.RIGHT);

        add(questionLabel,1,1);
        add(questionText,2,1);
        add(answerLabel,1,2);
        add(answerText,2,2);

        add(new HBox(cancelButton, addButton), 2,3);
    }
}
