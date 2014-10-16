package no.anderska.wta.questions.assertions;

import no.anderska.wta.game.Question;
import org.fest.assertions.Assertions;
import org.fest.assertions.GenericAssert;

public class QuestionAssert extends GenericAssert<QuestionAssert, Question> {
    QuestionAssert(Question actual) {
        super(QuestionAssert.class, actual);
    }

    public static QuestionAssert assertThat(Question actual) {
        return new QuestionAssert(actual);
    }

    public QuestionAssert hasQuestionEqualTo(String expected) {
        Assertions.assertThat(actual).isNotNull();
        Assertions.assertThat(actual.getQuestion()).describedAs("Question").isEqualTo(expected);
        return this;
    }

    public QuestionAssert hasAnswerEqualTo(String expected) {
        Assertions.assertThat(actual).isNotNull();
        Assertions.assertThat(actual.getCorrectAnswer()).describedAs("Answer").isEqualTo(expected);
        return this;
    }
}
