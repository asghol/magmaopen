package no.anderska.wta.questions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import no.anderska.wta.game.Question;

public class LoepereQuestionGenerator extends LoepereAbstractQuestionGenerator {
    private static final String DESCRIPTION = "Hvor mange måter fins det å plassere k løpere på brett med størrelse n*n på?";

    private LoepereQuestionGenerator(int numberOfQuestions) {
        super(numberOfQuestions, DESCRIPTION);
    }

    public LoepereQuestionGenerator() {
        this(4);
    }

    protected void createAnswer(String question){

    }

    @Override
    protected final Question createQuestion() {
        int first = new Random().nextInt(9);
        int second = new Random().nextInt(first-1);
        second ++;
        String testCase = ""+first+" "+second;

        return new Question(testCase.toString(), calculateResult(testCase.toString()));

    }
    static Integer[][] resultat=new Integer[9][65];
    static Integer[][] svarte=new Integer[9][65];
    static Integer[][] hvite=new Integer[9][65];
    private String calculateResult(String inputString){

        int n = Integer.parseInt(inputString.split(" ")[0]);
        int k = Integer.parseInt(inputString.split(" ")[1]);
        int res=0;
        if (resultat[n][k]!=null)res=resultat[n][k];
        else {
            res=0;
            for (int j=k-1;j>=1;j--){ //k til 1 pa svrte
                res+=met(n,j,false)*met(n,k-j,true);
            }
            if (k==0)res=1;
            else res+=met(n,k,true)+met(n,k,false);
            resultat[n][k]=res;
        }
        return ""+res;
    }
    //brikker plasseres paa svarte ruter
    static int met(int n,int k,boolean farge){ //false-svart
        if (k==0)return 1;
        if (k==1){
            if (n%2==0)return n*n/2;
            else if (farge) return n*n/2;
            else return n*n/2+1;
        }
        //Par par=new Par(0,0);
        //ArrayList<Par> l= new ArrayList<Par>();
        if (farge)if (hvite[n][k]!=null)return hvite[n][k];
        if (!farge) if (svarte[n][k]!=null)return svarte[n][k];
        int res=backtrack(new ArrayList<Par>(),n,k,farge);
        if (n%2==0){hvite[n][k]=res;svarte[n][k]=res;}
        else
        if (farge)hvite[n][k]=res;
        else svarte[n][k]=res;
        return res;
    }
    static int backtrack(ArrayList<Par> liste,int n,int k,boolean farge){

        if (k==0){
            return 1;
        }
        else {
            int res=0;
            if (!liste.isEmpty()){
                int a=liste.get(liste.size()-1).a;
                int b=liste.get(liste.size()-1).b;
                for (int i=a;i<n;i++){
                    if (i==a)
                        for (int j=b;j<n;j=j+2){
                            if (plasser(liste,i,j)){
                                liste.add(new Par(i,j));
                                res+=backtrack(liste,n,k-1,farge);
                                liste.remove(liste.size()-1);
                            }
                        }
                    else {
                        if (farge){
                            if (i%2==0){
                                for (int j=1;j<n;j=j+2){
                                    if (plasser(liste,i,j)){
                                        liste.add(new Par(i,j));
                                        res+=backtrack(liste,n,k-1,farge);
                                        liste.remove(liste.size()-1);
                                    }
                                }
                            }else {
                                for (int j=0;j<n;j=j+2){
                                    if (plasser(liste,i,j)){
                                        liste.add(new Par(i,j));
                                        res+=backtrack(liste,n,k-1,farge);
                                        liste.remove(liste.size()-1);
                                    }
                                }
                            }
                        }
                        else {
                            if (i%2==0){
                                for (int j=0;j<n;j=j+2){
                                    if (plasser(liste,i,j)){
                                        liste.add(new Par(i,j));
                                        res+=backtrack(liste,n,k-1,farge);
                                        liste.remove(liste.size()-1);
                                    }
                                }
                            }
                            else {
                                for (int j=1;j<n;j=j+2){
                                    if (plasser(liste,i,j)){
                                        liste.add(new Par(i,j));
                                        res+=backtrack(liste,n,k-1,farge);
                                        liste.remove(liste.size()-1);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            else {

                if (farge){
                    for (int i=0;i<n;i++){
                        if (i%2==0){
                            for (int j=1;j<n;j=j+2){
                                liste.add(new Par(i,j));
                                res+=backtrack(liste,n,k-1,farge);
                                liste.remove(0);
                            }

                        }else {
                            for (int j=0; j<n;j=j+2){
                                liste.add(new Par(i,j));
                                res+=backtrack(liste,n,k-1,farge);
                                liste.remove(0);
                            }
                        }
                    }
                }
                else {
                    for (int i=0;i<n;i++){
                        if (i%2==0){
                            for (int j=0;j<n;j=j+2){
                                liste.add(new Par(i,j));
                                res+=backtrack(liste,n,k-1,farge);
                                liste.remove(0);
                            }

                        }else {
                            for (int j=1; j<n;j=j+2){
                                liste.add(new Par(i,j));
                                res+=backtrack(liste,n,k-1,farge);
                                liste.remove(0);
                            }
                        }
                    }
                }
            }
            return res;
        }
    }
    static boolean plasser(ArrayList<Par> liste,int i,int j){
        for (Par l:liste){
            if (Math.abs(l.a-i)==Math.abs(l.b-j))return false;
        }
        return true;
    }
}
class Par{
    int a;
    int b;
    public Par(int a,int b){
        this.a=a;
        this.b=b;
    }
}
