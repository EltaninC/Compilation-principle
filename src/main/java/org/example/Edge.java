package org.example;

import lombok.Data;

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
}
