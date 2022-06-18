package frank.lucassen.java;

import java.io.Serializable;

public class Question implements Serializable {
    private final String question;
    private final String answer;

    public Question(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    @Override
    public String toString() {
        return "Question:\n" + question + "\nAnswer:\n" + answer;
    }
}
