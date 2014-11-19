package no.anderska.wta.questions;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

import no.anderska.wta.game.Question;

public class SittepermutasjonerQuestionGenerator extends SittepermutasjonerAbstractQuestionGenerator{
    private static final String DESCRIPTION = "Hvor mange måter fins det å plassere folk på, slik at alle blir fornøyd.";

    private SittepermutasjonerQuestionGenerator(int numberOfQuestions) {
        super(numberOfQuestions, DESCRIPTION);
    }

    public SittepermutasjonerQuestionGenerator() {
        this(3);
    }

    protected void createAnswer(String question){

    }

    @Override
    protected final Question createQuestion() {
        ArrayList<String> testCases = new ArrayList<String>();
        int first = new Random().nextInt(9);
        first += 2;
        int second = new Random().nextInt(first-2);
        second +=2;

        String testCase = "" + first + " " + second;

        return new Question(testCase.toString(), calculateResult(testCase.toString()));
    }
    static BigInteger[] factorials = new BigInteger[150];
    private String calculateResult(String inputString) {
        factorials[1] = new BigInteger("1");
        int totalNumber = Integer.parseInt(inputString.split(" ")[0]);
        int friends = Integer.parseInt(inputString.split(" ")[1]);
        int biggest = Math.max(totalNumber,friends);
        if (factorials[biggest]==null){
            for (int i=1;i<=biggest;i++){
                if (factorials[i]==null){
                    factorials[i]=factorials[i-1].multiply(new BigInteger(""+i));
                }
            }
        }
        BigInteger result = factorials[friends].multiply(factorials[totalNumber-friends+1]);
        return result.toString();
    }
}
