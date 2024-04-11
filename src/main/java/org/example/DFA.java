package org.example;

import lombok.Data;
import java.util.List;
import java.util.Set;

@Data
public class DFA {
    List<Set<Integer>> state; //状态集，重新编号方式(下标即为新的编号）
    int length; //状态数量
    Integer start;  //起点
    Set<Integer> end;    //终点
    List<Edge> edge;   //边

    public DFA(){};

    public DFA(List<Set<Integer>> state, int length, Integer start, Set<Integer> end, List<Edge> edge)
    {
        this.state = state;
        this.length = length;
        this.start = start;
        this.end = end;
        this.edge = edge;
    }
}
