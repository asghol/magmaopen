package no.anderska.wta.questions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import no.anderska.wta.game.Question;

public class FotballkampbilletterQuestionGenerator extends FotballkampbilletterAbstractQuestionGenerator{
    private static final String DESCRIPTION = "Finn ut om Kris finner billetter slik at ingen må sitte alene.";

    private FotballkampbilletterQuestionGenerator(int numberOfQuestions) {
        super(numberOfQuestions, DESCRIPTION);
    }

    public FotballkampbilletterQuestionGenerator() {
        this(5);
    }

    protected void createAnswer(String question){

    }

    @Override
    protected final Question createQuestion() {
        ArrayList<String> testCases = new ArrayList<String>();
        testCases.add("-***- -**-- --*--:6"); //false
        testCases.add("--**-- -**--- ---**-:5"); //false
        testCases.add("-**--**:3"); //false
        testCases.add("-**--** **---**:7"); //false
        testCases.add("-**--**:1"); //false

        testCases.add("--**-- -**--- ---**-:6"); //true
        testCases.add("-***- -**-- --*--:5"); //true
        testCases.add("*-*-*---- --******- -***-----:9"); //true
        testCases.add("--**-- **--*- -***--:7"); //true
        testCases.add("-**--** -****--:7"); //true

        String testCase = testCases.get(new Random().nextInt(testCases.size()));

        return new Question(testCase.toString(), calculateResult(testCase.toString()));

    }

    private String calculateResult(String inputString){
        int numberOfPeople = Integer.parseInt(inputString.split(":")[1]);
        if (numberOfPeople == 1) {
            return "false";
        }
        if (numberOfPeople == 0){
            return "true";
        }
        Set<Integer> valuesForRows = new HashSet<>();
        int sumOfSeats = 0;
        for (String row: inputString.split(" ")){
            int numberOfStars = 0;
            for (int i = 0; i < row.length(); i++){
                if (row.charAt(i) == '*'){
                    numberOfStars ++;
                    if (i == row.length()-1){
                        valuesForRows.add(new Integer(numberOfStars));
                        sumOfSeats += numberOfStars;
                    }
                }else if (numberOfStars > 1){
                    valuesForRows.add(new Integer(numberOfStars));
                    sumOfSeats += numberOfStars;
                    numberOfStars = 0;
                } else {
                    numberOfStars = 0;
                }
            }
        }
        if (numberOfPeople % 2 == 1){
            for (Integer group: valuesForRows){
                if (group > 2) {
                    return "true";
                }
            }
            return "false";
        } else {
            if (sumOfSeats >= numberOfPeople){
                return "true";
            } else {
                return "false";
            }
        }
    }
}
