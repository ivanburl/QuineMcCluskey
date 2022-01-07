package com.ivandr;

public class PairMy implements Comparable<PairMy> {
    private int a, b;

    public PairMy(int a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public int compareTo(PairMy o) {
        int tmp = Integer.compare(this.getA(), o.getA());
        if (tmp == 0) tmp = Integer.compare(this.getB(), o.getB());
        return tmp;
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }
}
