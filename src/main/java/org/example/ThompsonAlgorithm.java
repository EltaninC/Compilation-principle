package org.example;

import java.util.*;

public class ThompsonAlgorithm {
    private static int number;

    //获取优先级
    public static int getPriority(char c) {
        switch (c) {
            case '#':
                return 1;
            case '(':
                return 2;
            case '|':
                return 3;
            case '.':
                return 4;
            case ')':
                return 5;
            case '*':
                return 6;
            default:
                return 0; //不是操作符
        }
    }

    //判断是否加入连接运算符
    public static boolean if_cat(char l, char r) {
        if (getPriority(l) == 0 && r == '(')  //char+(
            return true;
        else if (getPriority(l) == 0 && getPriority(r) == 0) //char+char
            return true;
        else if (l == ')' && getPriority(r) == 0)  //)+char
            return true;
        else if (l == '*' && getPriority(r) == 0)  //*+char
            return true;
        else if (l == ')' && r == '(')  //)+(
            return true;
        else if (l == '*' && r == '(')  //*+(
            return true;
        else return false;
    }

    //比较优先级
    static boolean if_high(char a, char b) {
        if (getPriority(a) <= getPriority(b)) {
            return true;
        }
        return false;
    }

    //符号运算
    static NFA operateNFA(NFA b, NFA a, char c) {
        //或构造
        if (c == '|') {
            a.getState().addAll(b.getState());
            a.getEdge().addAll(b.getEdge());
            a.getState().add(++number);
            a.getEdge().add(new Edge(number,a.getStart(),'ε'));
            a.getEdge().add(new Edge(number,b.getStart(),'ε'));
            a.setStart(number);
            a.getState().add(++number);
            a.getEdge().add(new Edge(a.getEnd(),number,'ε'));
            a.getEdge().add(new Edge(b.getEnd(),number,'ε'));
            a.setEnd(number);
            a.setLength(a.getState().size());
        }
        //闭包构造
        else if (c == '*') {
            a.getState().add(++number);
            a.getEdge().add(new Edge(a.getEnd(),a.getStart(),'ε'));
            a.getEdge().add(new Edge(number,a.getStart(),'ε'));
            a.setStart(number);
            a.getState().add(++number);
            a.getEdge().add(new Edge(a.getEnd(),number,'ε'));
            a.getEdge().add(new Edge(number - 1,number,'ε'));
            a.setEnd(number);
            a.setLength(a.getState().size());
        }
        //连接构造
        else if (c == '.') {
            a.getState().addAll(b.getState());
            a.getEdge().addAll(b.getEdge());
            char[] t = new char[3];
            a.getEdge().add(new Edge(a.getEnd(),b.getStart(),'ε'));
            a.setEnd(b.getEnd());
            a.setLength(a.getState().size());
        } else {
            System.out.println("fault!");
        }
        return a;
    }

    //基本单位
    static NFA createNFA(char c) {
        NFA n = new NFA();
        List<Integer> l = new ArrayList<>();
        n.start = ++number;
        l.add(number);
        n.end = ++number;
        l.add(number);
        n.setState(l);
        List<Edge> e = new ArrayList<>();
        e.add(new Edge(n.start, n.end, c));
        n.setEdge(e);
        n.setLength(2);
        return n;
    }

    //正规式转NFA
    public static NFA regexToNFA(StringBuilder regex) {
        ArrayDeque<Character> op_stack = new ArrayDeque<Character>();
        ArrayDeque<NFA> n_stack = new ArrayDeque<NFA>();
        number = 0;
        regex.append('#');
        regex.insert(0, '#');
        //将正则表达式加入cat连接符
        int l = 0, r = 1;
        while (regex.charAt(r) != '#') {
            if (if_cat(regex.charAt(l), regex.charAt(r)))
                regex.insert(l + 1, ".");
            l++;
            r++;
        }
        op_stack.addLast(regex.charAt(0));
        //扫描
        for (int i = 1; i < regex.length(); i++) {
            char c = regex.charAt(i);
            if (getPriority(c) == 0) //字符内容
                n_stack.add(createNFA(c)); //压入字符栈
            else//符号
            {
                if (c == '(') {
                    op_stack.add('(');//直接入栈
                } else if (c == ')') //将遇到'('之间栈清空
                {
                    while (op_stack.getLast() != '(') {
                        char op = op_stack.pollLast();
                        NFA nfa;
                        if (op == '*') {
                            nfa = operateNFA(null, n_stack.pollLast(), op);
                        } else {
                            nfa = operateNFA(n_stack.pollLast(), n_stack.pollLast(), op);
                        }
                        n_stack.add(nfa);
                    }
                    op_stack.pollLast();
                } else if (c == '*') //遇到最高运算符，直接操作
                {
                    NFA nfa = operateNFA(null, n_stack.pollLast(), '*');
                    n_stack.add(nfa);
                } else {
                    while (!if_high(op_stack.peekLast(), c)) //循环比较，直到比栈顶操作符优先级高
                    {
                        char op = op_stack.pollLast();
                        NFA nfa = operateNFA(n_stack.pollLast(), n_stack.pollLast(), op);
                        n_stack.add(nfa);
                    }
                    op_stack.add(c);
                }
            }
        }
        //添加起点
        NFA nfa = n_stack.pop();
        nfa.getState().add(0);
        nfa.getEdge().add(new Edge(0,nfa.getStart(),'ε'));
        nfa.start = 0;
        nfa.setLength(nfa.getLength()+1);
        return nfa;
    }
}
