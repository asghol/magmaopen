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
        ArrayList<String> dic = new ArrayList<String>();
        int numberOfWordsInDic = new Random().nextInt(6);
        numberOfWordsInDic += 1;
        for (int i =0; i<numberOfWordsInDic; i++){
            int lengthOfWord = new Random().nextInt(5);
            lengthOfWord += 1;
            String dicWord = randomString(lengthOfWord);
            dic.add(dicWord);
        }
        String dicString ="";
        for (String word : dic){
            dicString = dicString + word + " ";
        }
        dicString = dicString.substring(0,dicString.length()-1);

        ArrayList<String> messageList = new ArrayList<>();
        int numberOfWordsInMessage = new Random().nextInt(numberOfWordsInDic);
        numberOfWordsInMessage += 2;
        for (int i =0; i<numberOfWordsInMessage; i++){
            int wordIndexInDic = new Random().nextInt(numberOfWordsInDic);
            messageList.add(dic.get(wordIndexInDic));
        }

        String result = "";
        for (String word: messageList){
            result = result + word + " ";
        }
        result = result.substring(0,result.length()-1);

        int numberOfMistakes = new Random().nextInt(6);
        for (int i = 0; i<numberOfMistakes; i++){
            int numberOfPositionToBeMistaken = new Random().nextInt(result.length());
            if (result.charAt(numberOfPositionToBeMistaken)==' '){
                numberOfPositionToBeMistaken --;
            }
            char c = (char) ('a' + random.nextInt(26));
            result = result.substring(0,numberOfPositionToBeMistaken)+c+result.substring(numberOfPositionToBeMistaken+1);
        }

        String testCase =dicString + ":" + result;

//        ArrayList<String> testCases = new ArrayList<String>();
//        testCases.add("sjna ewkfhew jfksghel kaghadk:kaghodk ewkfhew sjna jfktghel");
//        testCases.add("sjna ewkfhew jfksghel kaghadk:sjna kaghodk ewkfhew jfktghel");
//
//        testCases.add("sjna ewkfhew jfksghel kaghadk:kaghdk ewkfhew sjna jfktghel");
//        testCases.add("sjna ewkfhew jfksghel kaghadk:kaghdk ewkfggw sjna jfktghel");
//
//        String testCase = testCases.get(new Random().nextInt(testCases.size()));

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
