package no.anderska.wta.questions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import no.anderska.wta.game.Question;

public class LunsjQuestionGenerator extends LunsjAbstractQuestionGenerator {

    private static final String DESCRIPTION = "Finn ut om Sofija, Asgeir og Jonathan får spise lunsj sammen.";

    private LunsjQuestionGenerator(int numberOfQuestions) {
        super(numberOfQuestions, DESCRIPTION);
    }

    public LunsjQuestionGenerator() {
        this(3);
    }

    protected void createAnswer(String question){

    }

    @Override
    protected final Question createQuestion() {

        //Sofija 11:15-12:00;Asgeir;Jonathan 11:30-11:35 12:10-12:17

        ArrayList<String> testCases = new ArrayList<String>();
        testCases.add("Sofija 11:15-12:00;Asgeir 10:00-11:00 12:15-13:00;Jonathan 11:30-11:35 12:10-12:17"); //2 spiser sammen
        testCases.add("Sofija 11:15-12:00;Asgeir;Jonathan 11:30-11:35 12:10-12:17"); //Hurra


        String testCase = testCases.get(new Random().nextInt(testCases.size()));

        return new Question(testCase.toString(), calculateResult(testCase.toString()));
    }

    private String calculateResult(String inputString){
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<ArrayList<Pair>> timeStrings = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            String line = inputString.split(";")[i];
            if (line.contains(" ")){
                names.add(line.split(" ")[0]);
            } else {
                names.add(line);
            }
            //save DateTimes
            ArrayList<Pair> availableTimes = new ArrayList<>();
            if (line.split(" ").length > 1) {
                Double start = timeToDouble("10:55");
                for (int j = 0; j < Arrays.copyOfRange(line.split(" "), 1, line.split(" ").length).length; j++) {
                    String str = Arrays.copyOfRange(line.split(" "), 1, line.split(" ").length)[j];
                    // "10:30-11:40
                    String meetingStartString = str.split("-")[0];
                    String meetingEndString = str.split("-")[1];
                    if (start < timeToDouble(meetingStartString)) {
                        Pair newPair = new Pair(start, timeToDouble(meetingStartString));
                        if (newPair.b - newPair.a >= 0.5) {
                            availableTimes.add(newPair);
                        }
                    }
                    start = java.lang.Math.max(start, timeToDouble(meetingEndString));
                    if (j == Arrays.copyOfRange(line.split(" "), 1, line.split(" ").length).length - 1 && start < timeToDouble("13:00")) {
                        Pair newPair = new Pair(start, timeToDouble("13:00"));
                        if (newPair.b - newPair.a >= 0.5) {
                            availableTimes.add(newPair);
                        }
                    }
                }
            } else {
                availableTimes.add(new Pair(timeToDouble("10:55"), timeToDouble("13:00")));
            }
            timeStrings.add(availableTimes);
        }
        int res = haveIntersection(timeStrings);
        if (res == 1) {
            return "Ingen spiser med noen";
        } else if (res == 2) {
            return ("2 spiser sammen");
        } else if (res == 3) {
            return ("Hurra");
        }
        return "null";
    }



    static double timeToDouble(String timeString){
        Double res = Integer.parseInt(timeString.split(":")[0])*1.0;
        res += Integer.parseInt(timeString.split(":")[1])/(60.0);
        return res;
    }

    static int haveIntersection(ArrayList<ArrayList<Pair>> liste){
        int res = 1;
        for (Pair sofija: liste.get(0)) {
            for (Pair asgeir: liste.get(1)){
                for (Pair jonathan: liste.get(2)){
                    if (2 > res && (sofija.hasIntersectionWith(asgeir) || asgeir.hasIntersectionWith(jonathan) || sofija.hasIntersectionWith(jonathan))) {
                        res = 2;
                    }
                    if (3 > res && sofija.hasIntersectionWith(asgeir) && asgeir.hasIntersectionWith(jonathan) && sofija.hasIntersectionWith(jonathan)) {
                        res = 3;
                        return res;
                    }
                }
            }
        }
        return res;
    }
    class Pair{
        Double a;
        Double b;
        Pair(Double a2, Double b2){
            a=a2;
            b=b2;
        }
        boolean hasIntersectionWith(Pair pair){
            Double newA = java.lang.Math.max(a, pair.a);
            Double newB = java.lang.Math.min(b, pair.b);
            if (newB - newA >= 0.5) {
                return true;
            }
            return false;
        }
    }
}
