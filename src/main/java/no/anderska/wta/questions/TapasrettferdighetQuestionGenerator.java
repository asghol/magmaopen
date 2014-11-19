package no.anderska.wta.questions;

import java.util.ArrayList;
import java.util.Random;

import no.anderska.wta.game.Question;

public class TapasrettferdighetQuestionGenerator extends TapasrettferdighetAbstractQuestionGenerator{
    private static final String DESCRIPTION = "Hvor mange tapas engeter får hver? Hvor mange som blir til overs?";

    private TapasrettferdighetQuestionGenerator(int numberOfQuestions) {
        super(numberOfQuestions, DESCRIPTION);
    }

    public TapasrettferdighetQuestionGenerator() {
        this(4);
    }

    protected void createAnswer(String question){

    }

    @Override
    protected final Question createQuestion() {
        int numberOfRounds = new Random().nextInt(8);
        numberOfRounds += 2;
        String input="";
        for (int i = 0; i<numberOfRounds; i++){
            int number = new Random().nextInt(20);
            if (i==0){
                input = input + number;
            } else {
                input = input + " " + number;
            }
        }
        input= input+ ":" + new Random().nextInt(30);




        return new Question(input, calculateResult(input));

    }
    private String calculateResult(String inputString){
        int folk = Integer.parseInt(inputString.split(":")[1]);
        int sum = 0;
        for (String s: (inputString.split(":")[0]).split(" ")) {
            sum += Integer.parseInt(s);
        }
        return ""+sum/folk+" til hver "+ (sum % folk) + " til overs" ;
    }

}
