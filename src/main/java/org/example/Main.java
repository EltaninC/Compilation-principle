package org.example;

import java.io.IOException;
import java.util.*;

import static java.lang.Thread.sleep;

public class Main {

    public static void main(String[] args) throws IOException {
        boolean flag = true;
        while(flag){
            try{
                testDFA();
                flag = false;
            }
            catch(Exception e){
                System.out.println("输入错误");
                System.out.println("请重新输入");
                flag = true;
            }
        }

    }

    public static void testNFA() {
        GraphStream graphStream = new GraphStream();
        ThompsonAlgorithm thompsonAlgorithm = new ThompsonAlgorithm();
        System.out.println("请输入正规式");
        Scanner scanner = new Scanner(System.in);
        StringBuilder stringBuilder = new StringBuilder(scanner.next());
        try{
            NFA nfa = thompsonAlgorithm.regexToNFA(stringBuilder);
            graphStream.display(nfa);
            System.out.println("Start:" + nfa.getStart());
            System.out.println("End:" + nfa.getEnd());
            System.out.println("状态");
            for (Integer integer : nfa.getState()) {
                System.out.print(integer);
                System.out.print(",");
            }
            System.out.println();
            System.out.println("转移：");
            for (Edge edge : nfa.getEdge()) {
                System.out.println(edge.getStart()+","+ edge.getEnd()+","+edge.getValue());
            }
        } catch (Exception e) {
            System.out.println("错误输入");
        }
    }
    public static void testDFA() {

        Scanner in = new Scanner(System.in);
        String regex = in.next();
        SubsetConstruction subsetConstruction = new SubsetConstruction();
        ThompsonAlgorithm thompsonAlgorithm = new ThompsonAlgorithm();
        SubsetPartition subsetPartition = new SubsetPartition();
        NFA nfa = thompsonAlgorithm.regexToNFA(new StringBuilder(regex));
        System.out.println(nfa);
        GraphStream graphStream = new GraphStream();
        graphStream.display(nfa);
        DFA dfa =subsetConstruction.constructDFAFromNFA(nfa);
        System.out.println(dfa);
        DFA dfa1 = subsetPartition.makeMinDFA(dfa);
        System.out.println(dfa1);
    }

}