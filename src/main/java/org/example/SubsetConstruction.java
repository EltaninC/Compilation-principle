package org.example;

import java.util.*;

public class SubsetConstruction {
    HashMap<Character,Map<Integer,List<Integer>>> edges;    //使用Map储存边集合，避免重复遍历边

    public DFA constructDFAFromNFA(NFA nfa) {
        edges = Edge.preEdge(nfa.getEdge());

        //设置初始状态集
        List<Set<Integer>> dfaStates = new ArrayList<>();
        Set<Integer> startState = new HashSet<>();
        Set<Integer> closures = new HashSet<>();
        getClosures(nfa.start,closures);
        startState.addAll(closures);
        dfaStates.add(startState);
        List<Edge> edge = new ArrayList<>();

        //获取所有转移条件
        List<Character> inputs = new ArrayList<>(edges.keySet());
        inputs.remove(inputs.indexOf('ε'));

        //构建
        for (int i = 0; i < dfaStates.size(); i++) {
            Set<Integer> currentState = dfaStates.get(i);
            for (char input : inputs) {  // inputs是所有可能的输入符号
                Set<Integer> newState = new HashSet<>(move(currentState, input));   //创建下一个状态集
                if(newState.isEmpty())  continue;
                if (!dfaStates.contains(newState)) {
                    dfaStates.add(newState);
                }
                edge.add(new Edge(i,dfaStates.indexOf(newState),input));
            }
        }

        Set<Integer> endStates = new HashSet<>();
        for (Set<Integer> state : dfaStates) {
            if (state.contains(nfa.end)) {
                endStates.add(dfaStates.indexOf(state));
            }
        }

        return new DFA(dfaStates,dfaStates.size(),0,endStates,edge);
    }

    //求闭包
    void getClosures(Integer a,Set<Integer> res){
        res.add(a);
        //获取边
        Map<Integer, List<Integer>> listMap = edges.get('ε');
        List<Integer> list = listMap.get(a);

        if(list==null) return;

        for (Integer integer : list) {
            if(!res.contains(integer)) getClosures(integer,res);
        }
    }

    //跳转
    Set<Integer> move(Set<Integer> a, char c){
        Set<Integer> res = new HashSet<Integer>();

        //获取边
        Map<Integer, List<Integer>> listMap = edges.get(c);
        for (Integer i : a) {
            List<Integer> list = listMap.get(i);
            if(list==null) continue;
            for (Integer integer : list) {
                Set<Integer> closures = new HashSet<>();
                getClosures(integer,closures);
                res.addAll(closures);
            }

        }
        return res;
    }
}
