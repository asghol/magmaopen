package no.anderska.wta.questions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import no.anderska.wta.Validate;
import no.anderska.wta.game.Question;
import no.anderska.wta.game.QuestionGenerator;
import no.anderska.wta.game.QuestionSet;

abstract class AbstractQuestionGenerator implements QuestionGenerator {

    protected static final Random random = new Random();
    private final int numberOfQuestions;
    private final String description;

    protected abstract Question createQuestion();

    AbstractQuestionGenerator(int numberOfQuestions, String description) {
        this.numberOfQuestions = Validate.positiveNumber(numberOfQuestions, "numberOfQuestions");
        this.description = description;
    }

    @Override
    public QuestionSet generateQuestionSet(String playerid, String categoryid) {
        List<Question> result = new ArrayList<>();
        for (int i=0;i<numberOfQuestions;i++) {
            result.add(createQuestion());
        }
        return new QuestionSet(result, this, categoryid);
    }

    @Override
    public final String description() {
        return description;
    }

    protected static String randomString(int length) {
        StringBuilder res = new StringBuilder();
        for (int i=0;i<length;i++) {
            char c = (char) ('a' + random.nextInt(26));
            res.append(c);
        }
        return res.toString();
    }

    protected static <T> T random(@SuppressWarnings("unchecked") T... options) {
        return options[random.nextInt(options.length)];
    }

    protected static String randomSentence() {
        StringBuilder result = new StringBuilder();
        for (int i=0; i<random.nextInt(10)+1; i++) {
            if (result.length() > 0) {
                if (random.nextInt(50) == 0) {
                    result.append(" &");
                }
                result.append(" ");
            }
            result.append(randomWord());
        }
        return result.toString();
    }

    protected static String randomWord() {
        return randomString(random.nextInt(10) + 5);
    }

}