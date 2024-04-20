package org.example;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.*;

public class Lexer extends JFrame{
    public Lexer(){
        setSize(800, 1000);

        var panel = new JPanel();
        FlowLayout layout = new FlowLayout(FlowLayout.CENTER);
        layout.setHgap(140);
        panel.setLayout(layout);
        panel.add(new JLabel("输入"));
        panel.add(new JLabel("词法分析的结果"));

        var panel1 = new JPanel();
        FlowLayout layout1 = new FlowLayout(FlowLayout.CENTER);
        layout1.setHgap(50);
        panel1.setLayout(layout1);
        var inputText = new JTextArea(40, 30);
        var output = new JTextArea(40, 30);
        var scrollPane = new JScrollPane(output);
        panel1.add(inputText);
        panel1.add(scrollPane);

        var panel2 = new JPanel();
        FlowLayout layout2 = new FlowLayout(FlowLayout.CENTER);
        panel2.setLayout(layout2);
        var button = new JButton("开始分析");
        panel2.add(button);

        button.addActionListener(event->
        {
            String input = inputText.getText();
            TreeMap<Integer, List<String>> map = new Regex().lexer(input);
            for (Map.Entry<Integer, List<String>> entry : map.entrySet())
            {
                output.append(entry.getValue().toString());
                output.append("\n");
                System.out.println(entry.getValue().toString());
            }
        });

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panel, BorderLayout.NORTH);
        getContentPane().add(panel1, BorderLayout.CENTER);
        getContentPane().add(panel2, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(()->
        {
            Lexer frame = new Lexer();
            frame.setTitle("SimpleLexer");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}