package org.example;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

public class GraphStream {
    void display(NFA n){
        System.setProperty("org.graphstream.ui", "swing");
        Graph graph = new SingleGraph("Tutorial 1");
        // 设置全局样式，使所有节点和边的字体变大
        graph.setAttribute("ui.stylesheet", "node { fill-color: lightblue; text-mode: normal; text-size: 25; text-color: black; } edge { fill-color: black; text-size: 25; shape: cubic-curve;}");


        for (Integer character : n.getState()) {
            Node nodeA = graph.addNode(String.valueOf(character));
            nodeA.addAttribute("ui.label", nodeA.getId());
            if(character == 0){
                nodeA.setAttribute("ui.style", "fill-color: red;");
            }
            if(character == n.getEnd()){
                nodeA.setAttribute("ui.style", "fill-color: green;");
            }
        }
        int number = 0;
        for (Edge e : n.getEdge()) {
            number++;
            String edgeId = String.valueOf(number);
            graph.addEdge(edgeId, String.valueOf(e.getStart()), String.valueOf(e.getEnd()),true);

            // 设置边的标签
            graph.getEdge(edgeId).addAttribute("ui.label", String.valueOf(e.getValue()));
        }

        graph.display();
    }
}
