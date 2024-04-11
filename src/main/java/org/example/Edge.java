package org.example;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Edge {
    int start;
    int end;
    char value;

    public Edge(int a, int b, char c) {
        start = a;
        end = b;
        value = c;
    }

    //预处理将边转换为HashMap储存，方便后续操作
    static HashMap<Character,Map<Integer,List<Integer>>> preEdge(List<Edge> edgeList) {
        HashMap<Character, Map<Integer, List<Integer>>> edges = new HashMap<>();
        for (Edge edge : edgeList) {
            char key = edge.getValue();
            Map<Integer, List<Integer>> a;
            List<Integer> b;
            if (edges.containsKey(key)) { //判断创建过了吗
                a = edges.get(key);
                if (a.containsKey(edge.getStart())) {
                    b = a.get(edge.getStart());
                    b.add(edge.getEnd());
                } else {
                    b = new ArrayList<>();
                    b.add(edge.getEnd());
                    a.put(edge.getStart(), b);
                }
                edges.put(key, a);
            } else {
                a = new HashMap<>();
                b = new ArrayList<>();
                b.add(edge.end);
                a.put(edge.getStart(), b);
                edges.put(key, a);
            }
        }
        return edges;
    }
}
