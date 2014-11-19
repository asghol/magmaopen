package no.anderska.wta.questions;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;


import no.anderska.wta.game.Question;


public class TaxiturenQuestionGenerator extends TaxiturenAbstractQuestionGenerator{
    private static final String DESCRIPTION = "Fins det en vei til destinasjonen som involverer færre veistrekninger?";

    private TaxiturenQuestionGenerator(int numberOfQuestions) {
        super(numberOfQuestions, DESCRIPTION);
    }

    public TaxiturenQuestionGenerator() {
        this(4);
    }

    protected void createAnswer(String question){

    }

    @Override
    protected final Question createQuestion() {
        ArrayList<String> testCases = new ArrayList<String>();
        testCases.add("AB AC CB BE:5");
        testCases.add("AC CB BA AD DE EB:5");
        testCases.add("AC BC BA AD DE EB:3");
        testCases.add("AC GA CD DE EG BE EB DF AF FC:4");
        testCases.add("AC GA CD DE EG BE EB DF AF FC:5");
        testCases.add("AC GA CD DE EG BE DF AF FC:5");
        testCases.add("AC TB GA CD DE EG BE DF AF FC GT:6");
        testCases.add("AC TB GA CD DE EG BE DF AF FC GT:7");
        testCases.add("AC TB GA CD DE FB EG BE DF AF FC GT:5");
        testCases.add("AC TB GA CD DE FB EG BE DF AF FC GT:2");


        String testCase = testCases.get(new Random().nextInt(testCases.size()));

        return new Question(testCase.toString(), calculateResult(testCase.toString()));
    }

    private String calculateResult(String inputString){
        String res = "";

        int price = Integer.parseInt(inputString.split(":")[1]);
        Set<Character> nodeSet = new HashSet();
        for (int i=0; i<inputString.length(); i++){
            char c = inputString.charAt(i);
            if (c == ':'){
                break;
            }
            if (!(c == ' ')){
                nodeSet.add(c);
            }
        }
        ArrayList<Character> nodeIndexes = new ArrayList<>();
        int index=0;
        for (Character c: nodeSet){
            nodeIndexes.add(index,c);
            index++;
        }

        boolean[][] edges = new boolean[nodeSet.size()][nodeSet.size()];
        for (String edgeString:(inputString.split(":")[0]).split(" ")){
            edges[(Integer)nodeIndexes.indexOf(edgeString.charAt(0))][(Integer)nodeIndexes.indexOf(edgeString.charAt(1))] = true;
        }

        int depth = 0;
        Set<Character> thisNodeSet = new HashSet();
        thisNodeSet.add('A');
        Set<Character> visited = new HashSet();

        while (depth < price){
            Set<Character> nextNodeSet = new HashSet();
            for(Character c : thisNodeSet){
                if (c.equals(new Character('B'))){
                    return ""+depth;
                }
                if (visited.size()==0 || visited.size()>0 && !visited.contains(c)){
                    for (int j=0;j<edges[nodeIndexes.indexOf(c)].length;j++){
                        if (edges[nodeIndexes.indexOf(c)][j]){
//                            int newNode = nodeIndexes.indexOf(j);
                            nextNodeSet.add(nodeIndexes.get(j));
                        }
                    }
                    visited.add(c);
                }
            }

            thisNodeSet = new HashSet<>(nextNodeSet);
            depth ++;
        }
        return "OK";
    }
}
