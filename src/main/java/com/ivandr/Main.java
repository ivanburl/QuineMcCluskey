package com.ivandr;



import org.openjdk.jmh.annotations.Benchmark;
import java.util.*;

public class Main {

    public static void main(String... args) {
        String s = "(a|!b)&(a&!c|!d)|(!a&b&c&d)";
//        TreeSet<PairMy> a = new TreeSet<PairMy>();
//        a.add(new PairMy(1,2));
//        a.add(new PairMy(1,3));
//        a.add(new PairMy(2,3));
//        a.add(new PairMy(2,4));
//        a.add(new PairMy(3,5));
//        a.add(new PairMy(4,3));
//        a.add(new PairMy(5,3));
//        a.add(new PairMy(6,3));
//        for (var i: a) {
//            System.out.println(i.getA() + " " + i.getB());
//        }
//        var b = a.tailSet(new PairMy(2,4));
//        System.out.println();
//        for (var i: b) {
//            System.out.println(i.getA() + " " + i.getB());
//        }
        QuineMcCluskey test = new QuineMcCluskey(s);
        test.minimizeBoolFunc();
    }



}

