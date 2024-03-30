package org.example;

import lombok.Data;

import java.util.List;

@Data
class NFA {
    List<Integer> state;    //状态集
    int length; //状态数量
    Integer start;  //起点
    Integer end;    //终点
    List<Edge> edge;   //边

    public NFA(){};

    public NFA(List<Integer> state, int length, Integer start, Integer end, List<Edge> edge)
    {
        this.state = state;
        this.length = length;
        this.start = start;
        this.end = end;
        this.edge = edge;
    }
}
