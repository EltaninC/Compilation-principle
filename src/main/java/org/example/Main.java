package org.example;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
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



}