package no.anderska.wta.questions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import no.anderska.wta.game.Question;

public class TurutgifterQuestionGenerator extends TurutgifterAbstractQuestionGenerator{
    private static final String DESCRIPTION = "Finn ut hva er den minste summen av penger som skal bli vekslet for å kompensere for froskjeller.";

    private TurutgifterQuestionGenerator(int numberOfQuestions) {
        super(numberOfQuestions, DESCRIPTION);
    }

    public TurutgifterQuestionGenerator() {
        this(3);
    }

    protected void createAnswer(String question){

    }

    @Override
    protected final Question createQuestion() {
        ArrayList<String> testCases = new ArrayList<String>();
        testCases.add("10.00 20.00 30.00"); //10.00
        testCases.add("15.00 15.01 3.00 3.01"); //11.99
        testCases.add("18.50 13.03 16.9"); //$2.84
        testCases.add("7.04 3.09 5.89"); //$2.25
        testCases.add("6.99 18.20 45.37 81.50"); //$50.83
        testCases.add("4.55 7.80 8.99"); //$2.56
        testCases.add("5.67 8.41 9.73 6.45 7.00 5.45"); //$3.90
        testCases.add("6.73 7.46 6.22 8.56 8.88"); //$2.30
        testCases.add("6.32 6.73 9.71 9.00"); //$2.83
        testCases.add("6.77 8.00 8.00 5.20"); //$2.01
        testCases.add("4.50 6.80"); //$1.15
        testCases.add("3.67 7.70"); //$2.01


        String testCase = testCases.get(new Random().nextInt(testCases.size()));

        return new Question(testCase.toString(), calculateResult(testCase.toString()));
    }
    static ArrayList<Integer> costs = new ArrayList<Integer>(){{}};
    static int sumCosts() {
        int sum = 0;

        for (Integer i:costs) {
            sum += i;
        }
        return sum;
    }
    private String calculateResult(String inputString) {
        costs = new ArrayList<Integer>(){{}};
        int numberOfElements = inputString.split(" ").length;
        for (int i=0; i < numberOfElements; i++) {
            String line = inputString.split(" ")[i];
            Integer dollars = Integer.parseInt(line.split("\\.")[0]);
            Integer cents = Integer.parseInt(line.split("\\.")[1]);
            costs.add(dollars * 100 + cents);
        }
        int sumCents = sumCosts();
        double avgCents = ((double) sumCents) / numberOfElements;
        double taken = 0;
        double given = 0;

        for (int i = 0; i < numberOfElements; i++) {
            double deltaCents = costs.get(i) - avgCents;
            if (deltaCents < 0) {
                taken += -((int) deltaCents) / 100.0;
            } else {
                given += ((int) deltaCents) / 100.0;
            }
        }
        return String.format("$%.2f", taken > given ? taken : given);
    }
}
