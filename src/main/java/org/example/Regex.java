package org.example;

import java.util.*;
import java.util.regex.*;

public class Regex {
    /**
     * 给出语法规则的正则表达式集
     * a)	常量可以为实数和整数类型，其定义方式为实数的最一般书写方式，如：123.321。具体要求：不支持整数部分大于0时首数字为0；不支持小数点后结尾为0；不支持科学记数法；不支持仅为整数时有小数点；
     * b)	变量采用与C语言的标识符定义一样的方式：首字符为字母或下划线；其他的为字母、数字及下划线的混合串；区分大小写；变量长度不超过32个字符；
     * c)	需要处理的关键字仅为给出的源程序中所列举的内容；
     * d)	支持四则运算，其计算的符号与C语言相同，为：+-* /；
     * e)   合法的分隔符包括：空格、制表符、分行符圆括号（左、右）、分号。其中空格、制表符、分行符可以出现在任何两个不同的单词中间；圆括号（左、右）用于表达式中，用于改变运算的优先级，以及标识函数的参数，分号用于标识一个语句的结束。
     */
    String[] regexPattern = {
            "[+-]?\\d+(\\.\\d+)|[+-]?\\d+", //Literal（这里要把小数匹配放在前面，小数匹配要比整数更严格; 此处偷懒并没有预防可能出现的一些错误输入如10.0）
            "[a-zA-Z_][a-zA-Z0-9_]{0,31}",  //Identifier
            "\\bvoid\\b|\\bmain\\b|\\bint\\b|\\bfloat\\b|\\bif\\b|\\belse\\b",  //Keyword
            "[+\\-*/><]",                   //Operator
            "[();{}\\s]"                    //Punctuation
    };

//    public static void main(String[] args) {
//        String input = "" +
//                "void main(){\n" +
//                "int x,a,b;\n" +
//                "float y,c,d;\n" +
//                "x=a+b;\n" +
//                "y=c/d;\n" +
//                "if(x>y)\tx=10;\n" +
//                "else\t\ty=100.5;\n" +
//                "}\n";
//        Regex regex = new Regex();
//
//        TreeMap<Integer, List<String>> map = new TreeMap<>();
//
//        for (int i = 0; i < 5; i++)
//        {
//            String regexPn = regex.regexPattern[i];
//            Pattern pattern = Pattern.compile(regexPn);
//            Matcher matcher = pattern.matcher(input);
//            while (matcher.find()){
//                switch (i){
//                    case 0: map.put(matcher.start(), Arrays.asList("Literal", matcher.group())); break;
//                    case 1: map.put(matcher.start(), Arrays.asList("Identifier", matcher.group())); break;
//                    case 2: map.put(matcher.start(), Arrays.asList("Keyword", matcher.group())); break;
//                    case 3: map.put(matcher.start(), Arrays.asList("Operator", matcher.group())); break;
//                    case 4: map.put(matcher.start(), Arrays.asList("Punctuation", matcher.group())); break;
//                    default: break;
//                }
////                System.out.println("Pattern:" + regexPn);
////                System.out.println("Match:" + matcher.group());
////                System.out.println("Start index:" + matcher.start());
////                System.out.println("End index:" + matcher.end());
////                System.out.println();
//            }
//        }
//
//        for (Map.Entry<Integer, List<String>> entry : map.entrySet())
//        {
//            System.out.println(entry.getValue());
//        }
//    }
    TreeMap<Integer, List<String>> lexer(String input)
    {
        Regex regex = new Regex();
        TreeMap<Integer, List<String>> map = new TreeMap<>();
        for (int i = 0; i < 5; i++)
        {
            String regexPn = regex.regexPattern[i];
            Pattern pattern = Pattern.compile(regexPn);
            Matcher matcher = pattern.matcher(input);
            while (matcher.find()){
                switch (i){
                    case 0: map.put(matcher.start(), Arrays.asList("Literal", matcher.group())); break;
                    case 1: map.put(matcher.start(), Arrays.asList("Identifier", matcher.group())); break;
                    case 2: map.put(matcher.start(), Arrays.asList("Keyword", matcher.group())); break;
                    case 3: map.put(matcher.start(), Arrays.asList("Operator", matcher.group())); break;
                    case 4: map.put(matcher.start(), Arrays.asList("Punctuation", matcher.group())); break;
                    default: break;
                }
            }
        }
        return map;
    }
}
