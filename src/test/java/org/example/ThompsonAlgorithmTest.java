package org.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ThompsonAlgorithmTest {

    @Test
    void createNFA() {
        List<Integer> l = Arrays.asList(1, 2);
        int length = 2;
        Integer start = Integer.valueOf(1);
        Integer end = Integer.valueOf(2);
        List<Edge> edge = Arrays.asList(new Edge(1, 2, 'a'));
        NFA nfa = new NFA(l, length, start, end, edge);
        assertEquals(nfa, ThompsonAlgorithm.createNFA('a'));
    }

    @Test
    void TestOperateOr(){
        List<Integer> l = Arrays.asList(1, 2, 3, 4, 5, 6);
        int length = 6;
        Integer start = Integer.valueOf(5);
        Integer end = Integer.valueOf(6);
        List<Edge> edge = Arrays.asList(
                new Edge(1, 2, 'a'),
                new Edge(3, 4, 'b'),
                new Edge(5, 1, 'ε'),
                new Edge(5, 3, 'ε'),
                new Edge(2, 6, 'ε'),
                new Edge(4, 6, 'ε')
        );

        NFA nfa = new NFA(l, length, start, end, edge);
        NFA nfa1 = ThompsonAlgorithm.createNFA('a');
        NFA nfa2 = ThompsonAlgorithm.createNFA('b');
        assertEquals(nfa, ThompsonAlgorithm.operateNFA(nfa2, nfa1, '|'));
    //压栈过程中最先遇到的压在下面
    }

    @Test
    void TestOperateClosure(){
        List<Integer> l = Arrays.asList(1, 2, 5, 6);
        int length = 4;
        Integer start = Integer.valueOf(5);
        Integer end = Integer.valueOf(6);
        List<Edge> edge = Arrays.asList(
                new Edge(1, 2, 'a'),
                new Edge(2, 1, 'ε'),
                new Edge(5, 1, 'ε'),
                new Edge(2, 6, 'ε'),
                new Edge(5, 6, 'ε')
        );

        NFA nfa = new NFA(l, length, start, end, edge);
        NFA nfa1 = ThompsonAlgorithm.createNFA('a');
        NFA nfa2 = ThompsonAlgorithm.createNFA('b');
        assertEquals(nfa, ThompsonAlgorithm.operateNFA(nfa2, nfa1, '*'));
    }

    @Test
    void TestOperateConcatenation(){
        List<Integer> l = Arrays.asList(1, 2, 3, 4);
        int length = 4;
        Integer start = Integer.valueOf(1);
        Integer end = Integer.valueOf(4);
        List<Edge> edge = Arrays.asList(
                new Edge(1, 2, 'a'),
                new Edge(3, 4, 'b'),
                new Edge(2, 3, 'ε')
        );

        NFA nfa = new NFA(l, length, start, end, edge);
        NFA nfa1 = ThompsonAlgorithm.createNFA('a');
        NFA nfa2 = ThompsonAlgorithm.createNFA('b');
        assertEquals(nfa, ThompsonAlgorithm.operateNFA(nfa2, nfa1, '.'));
    }

    @Test
    void TestOperateError(){
        NFA nfa1 = ThompsonAlgorithm.createNFA('a');
        NFA nfa2 = ThompsonAlgorithm.createNFA('b');
        assertEquals(nfa1, ThompsonAlgorithm.operateNFA(nfa2, nfa1, '@'));
    }


    @Test
    void regexToNFA() {
        StringBuilder regex = new StringBuilder("a*0|10");

        List<Integer> l = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 0);
        int length = 13;
        Integer start = Integer.valueOf(0);
        Integer end = Integer.valueOf(12);
        List<Edge> edge = Arrays.asList(
                new Edge(1, 2, 'a'),
                new Edge(2, 1, 'ε'),
                new Edge(3, 1, 'ε'),
                new Edge(2, 4, 'ε'),
                new Edge(3,4,  'ε'),
                new Edge(5, 6, '0'),
                new Edge(4, 5, 'ε'),
                new Edge(7, 8, '1'),
                new Edge(9, 10, '0'),
                new Edge(8, 9, 'ε'),
                new Edge(11, 3, 'ε'),
                new Edge(11, 7, 'ε'),
                new Edge(6, 12, 'ε'),
                new Edge(10, 12, 'ε'),
                new Edge(0, 11, 'ε')

        );
        NFA nfa = new NFA(l, length, start, end, edge);
        assertEquals(nfa, ThompsonAlgorithm.regexToNFA(regex));
    }
}