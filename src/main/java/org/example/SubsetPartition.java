package org.example;

import lombok.val;

import java.util.*;

public class SubsetPartition {
    List<Set<Integer>> setList = new ArrayList<>();
    HashMap<Character,Map<Integer,List<Integer>>> edges;    //使用Map储存边集合，避免重复遍历边

    public DFA makeMinDFA(DFA dfa) {
        //获取预处理的边集合，使用HashMap，减少遍历耗时
        edges = Edge.preEdge(dfa.getEdge());
        boolean change = true;
        divideByEnd(dfa);
        //分区
        while(change){
            change = false;
            for (Character v : edges.keySet()) {
                for (int j = 0; j < setList.size(); j++) {
                    Set<Integer> set = setList.get(j);
                    Map<Integer,Set<Integer>> m = new HashMap<>(); //存储同类的状态
                    for (Integer i : set) {
                        int next = getNextState(i,v);
                        if(m.containsKey(next)){
                            m.get(next).add(i);
                        }
                        else{
                            Set<Integer> t = new HashSet<>();
                            t.add(i);
                            m.put(next,t);
                        }
                    }
                    if(m.size() != 1){
                        change = true;
                        setList.remove(j);
                        for (Map.Entry<Integer, Set<Integer>> entry : m.entrySet()) {
                            setList.add(entry.getValue());
                        }
                        j--;
                    }
                }
            }
        }
        //构造最小DFA
        int start = 0;
        Set<Integer> end = new HashSet<>();
        List<Edge> edgeList = new ArrayList<>();
        for(int i = 0; i < setList.size(); i++){
            if (setList.get(i).contains(dfa.getStart())){
                start = i;
            }
            for (Integer s : setList.get(i)) {
                if (dfa.getEnd().contains(s)) {
                    end.add(i);
                }
                for (Edge edge : dfa.getEdge()) {
                    if(edge.getStart() == s){
                        int to = -1;
                        for(int j = 0; j < setList.size(); j++){
                            if (setList.get(j).contains(edge.getEnd())) {
                                to = j;
                            }
                        }
                        edgeList.add(new Edge(i,to,edge.getValue()));
                    }
                }
            }
        }

        DFA dfa1 =  new DFA(setList,setList.size(),start,end,edgeList);
        return dfa1;
    }

    //根据接受状态划分
    public void divideByEnd(DFA dfa) {
        Set<Integer> noOver = new HashSet<>();
        Set<Integer> over = new HashSet<>();
        for(int i = 0; i < dfa.getState().size(); i++){
            if(dfa.getEnd().contains(i)){
                over.add(i);
            }
            else{
                noOver.add(i);
            }
        }
        setList.add(noOver);
        setList.add(over);
    }

    //判断输入后的状态
    int getNextState(int a, char c){
        Map<Integer, List<Integer>> listMap = edges.get(c);
        if(listMap == null) return -1;
        List<Integer> integers = listMap.get(a);
        if(integers == null) return -1;
        int t = integers.get(0);
        for (int j = 0; j < setList.size(); j++) {
            if(setList.get(j).contains(t)) return j;
        }
        return -1;
    }
}
