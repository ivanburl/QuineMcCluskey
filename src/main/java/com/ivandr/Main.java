package com.ivandr;



import org.openjdk.jmh.annotations.Benchmark;
import java.util.*;

public class Main {

    @Benchmark
    public static void test1() {
        //boolean t = BoolExpression.eval("1&(0|1)^(1&0)|0^1^0^1^(1|1&0)");
    }

    public static void main(String... args) {
        System.out.println(1<<0);
    }

}
