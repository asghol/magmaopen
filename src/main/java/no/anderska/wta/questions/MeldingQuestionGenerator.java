package no.anderska.wta.questions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import no.anderska.wta.game.Question;

public class MeldingQuestionGenerator extends MeldingAbstractQuestionGenerator{
    private static final String DESCRIPTION = "Spansk dame eller trøtt kollega?";

    private MeldingQuestionGenerator(int numberOfQuestions) {
        super(numberOfQuestions, DESCRIPTION);
    }

    public MeldingQuestionGenerator() {
        this(4);
    }

    protected void createAnswer(String question){

    }

    @Override
    protected final Question createQuestion() {
        ArrayList<String> testCases = new ArrayList<String>();
        testCases.add("sjna ewkfhew jfksghel kaghadk:kaghodk ewkfhew sjna jfktghel");
        testCases.add("sjna ewkfhew jfksghel kaghadk:sjna kaghodk ewkfhew jfktghel");

        testCases.add("sjna ewkfhew jfksghel kaghadk:kaghdk ewkfhew sjna jfktghel");
        testCases.add("sjna ewkfhew jfksghel kaghadk:kaghdk ewkfggw sjna jfktghel");

        String testCase = testCases.get(new Random().nextInt(testCases.size()));

        return new Question(testCase.toString(), calculateResult(testCase.toString()));

    }
    static Set<String> dic;
    private String calculateResult(String inputString){
        String dicString = inputString.split(":")[0];

        dic = new HashSet<>();
        for (int i=0; i<dicString.split(" ").length; i++){
            dic.add(dicString.split(" ")[i]);
        }

        String messageString = inputString.split(":")[1];
        Set<String> message = new HashSet<>();
        for (int i=0; i<messageString.split(" ").length; i++){
            message.add(messageString.split(" ")[i]);
        }
        int numberOfMismatches=0;
        for (String messageWord:message){
            int res=numberOfMismatches(messageWord);
            if (res>1){
                return "kollega";
            }
            numberOfMismatches+=res;
        }
        if (numberOfMismatches > 2){
            return "kollega";
        }else{
            return "spansk dame";
        }
    }

    private int numberOfMismatches(String str){
        for (String realWord:dic){
            if (realWord.length() == str.length()){
                if (str.equals(realWord)){
                    return 0;
                }
                int numberOfMismatches=0;
                for (int i=0;i<str.length();i++){
                    if (str.charAt(i) != realWord.charAt(i)){
                        numberOfMismatches++;
                    }
                }

                if (numberOfMismatches>1){
                    continue;
                }else {
                    return numberOfMismatches;
                }
            }
        }
        return 100;
    }
}
