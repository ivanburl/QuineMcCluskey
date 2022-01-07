package com.ivandr;



import org.openjdk.jmh.annotations.Benchmark;
import java.util.*;

public class Main {

    public static void main(String... args) {
        String s = "(a|!b)&(a&!c|!d)|(!a&b&c&d)";
        QuineMcCluskey test = new QuineMcCluskey(s);
        test.minimizeBoolFunc();
        System.out.println(test.getMinimizedFunction());
    }



}

