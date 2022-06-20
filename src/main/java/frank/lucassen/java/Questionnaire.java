package frank.lucassen.java;

import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
import java.util.*;

public class Questionnaire implements Iterable<Question> {
    private final ArrayList<Question> questions = new ArrayList<>();
    private TextField questionField;
    private TextArea answerField;

    private int current = -1;

    private ArrayList<Integer> randomInts = null;
    private int randomCurrent = 0;

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
        builder.append("}");
        return builder.toString();
    }

    public void showNext(boolean solve) {
        if (size() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No questions available");
            alert.showAndWait();
            return;
        }
        current++;
        if (current >= questions.size()) current = 0;
        answerField.clear();
        questionField.setText(questions.get(current).getQuestion());
        if (solve) solveQuestion();
    }

    public void showPrevious(boolean solve) {
        if (size() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No questions available");
            alert.showAndWait();
            return;
        }
        current--;
        if (current < 0) current = questions.size() - 1;
        answerField.clear();
        questionField.setText(questions.get(current).getQuestion());
        if (solve) solveQuestion();
    }

    public void solveQuestion() {
        if (size() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No questions available");
            alert.showAndWait();
            return;
        }
        answerField.setText(questions.get(current).getAnswer());
    }

    public void showRandomQuestion(boolean solve) {
        int size = questions.size();
        if (randomInts == null || size != randomInts.size()) {
            randomInts = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                randomInts.add(i);
            }
            Collections.shuffle(randomInts);
            randomCurrent = 0;
            System.out.println(randomInts);
        }
        current = randomInts.get(randomCurrent);
        Question tmp = questions.get(current);
        questionField.setText(tmp.getQuestion());
        if (solve) answerField.setText(tmp.getAnswer());

        randomCurrent++;
        if (randomCurrent == randomInts.size()) randomInts = null;
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
                if (line == null) {
                    questions.add(new Question(question, ""));
                    return;
                }
                StringBuilder build = new StringBuilder();
                while (line.length() > 2) {
                    build.append(line).append("\n");
                    line = in.readLine();
                    if (line == null) {
                        questions.add(new Question(question, build.toString()));
                        showNext(solve);
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
