package frank.lucassen.java;

import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Questionnaire implements Iterable<Question> {
    private final ArrayList<Question> questions = new ArrayList<>();
    private TextField questionField;
    private TextArea answerField;

    private int current = -1;

    public Questionnaire() {

    }

    public void clearQuestions() {
        questions.clear();
        current = -1;
        questionField.clear();
        answerField.clear();

    }

    public void setQuestionField(TextField questionField) {
        this.questionField = questionField;
    }

    public void setAnswerField(TextArea answerField) {
        this.answerField = answerField;
    }

    public void insertQuestion(Question question) {
        questions.add(question);
    }

    public int size() {
        return questions.size();
    }

    public int getCurrent() {
        return current;
    }

    @Override
    public Iterator<Question> iterator() {
        return questions.iterator();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Questionnaire {\n");
        for (Question q : questions) {
            builder.append(q).append("\n");
        }
        builder.append("\n}");
        return builder.toString();
    }

    public void showNext(boolean solve) {
        current++;
        if (current >= questions.size()) current = 0;
        answerField.clear();
        questionField.setText(questions.get(current).getQuestion());
        if (solve) solveQuestion();
    }

    public void showPrevious(boolean solve) {
        current--;
        if (current < 0) current = questions.size() - 1;
        answerField.clear();
        questionField.setText(questions.get(current).getQuestion());
        if (solve) solveQuestion();
    }

    public void solveQuestion() {
        answerField.setText(questions.get(current).getAnswer());
    }

    public void showRandomQuestion(boolean solve) {
        current = (int) (Math.random() * size());
        //current--;
        showNext(solve);
    }

    public void addQuestion() {
        questions.add(new Question(questionField.getText(), answerField.getText()));
    }

    public void saveToFile(File file) {
        System.out.println(file.getPath());
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file.getPath()))) {
            for (Question q : this) {
                out.writeObject(q);
            }
            out.flush();
        } catch (IOException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error while trying to write questions to file");
            alert.showAndWait();
        }
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void loadFromFile(File file, boolean solve) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            try {
                while (true) {
                    questions.add((Question) in.readObject());
                }
            } catch (IOException ignored) {

            }
        } catch (IOException | ClassNotFoundException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error while trying to load questions from file");
            alert.showAndWait();
        }
        showNext(solve);
    }

    public void convertFile(File file, boolean solve) {
        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            String line = in.readLine();
            String question;
            String answer;
            while (line != null) {
                if (line.length() < 2) {
                    line = in.readLine();
                    continue;
                }
                question = line;
                line = in.readLine();
                StringBuilder build = new StringBuilder();
                while (line.length() > 2) {
                    build.append(line).append("\n");
                    line = in.readLine();
                    if (line == null) {
                        questions.add(new Question(question, build.toString()));
                        return;
                    }
                }
                answer = build.toString();

                questions.add(new Question(question, answer));
                line = in.readLine();
            }

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error while trying to convert file");
            alert.showAndWait();
        }
        showNext(solve);
    }
}
