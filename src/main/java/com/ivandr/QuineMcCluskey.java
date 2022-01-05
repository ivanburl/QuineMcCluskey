package com.ivandr;


import com.sun.source.tree.Tree;

import java.util.*;

public class QuineMcCluskey {

    public static final int N = 30;

    //private String boolExpr;
    private List<TreeSet<Element>> table;
    private BoolFunction boolFunc;
    private final int MAXX;
    private final int cntOfArguments;

    public QuineMcCluskey(String str) {
            boolFunc = new BoolFunction(str);
            cntOfArguments = boolFunc.getNumberOfArguments();
            table = new ArrayList<TreeSet<Element>>(cntOfArguments+1);
            MAXX = (1 << (cntOfArguments)) - 1;
    }

    public String minimizeBoolFunc() {
        return null;
    }

    private void computeAllPossibleVariants() {
        for (int i = 0; i<= MAXX; i++)
            if (boolFunc.parse(i))
                table.get(cntOfArguments).add(new Element(Integer.bitCount(i),(~i&MAXX),i));
    }

    private boolean isMergeable(Element a, Element b) {
        int tmp = a.getSetBits()^b.getSetBits();
        boolean checkPowOfTwo = ( ( (tmp&(tmp-1)) == 0 ) || ( tmp == 0 ) );
        boolean checkTheSameBit = ((a.getSetBits() ^ b.getSetBits()) == tmp);
        return (checkPowOfTwo && checkTheSameBit);
    }

    private class Element implements Comparable<Element>{
        private int countOfBits;
        private int setBits,unsetBits;

        public Element(int cnt, int sBit, int usBit) {
            countOfBits = cnt;
            setBits = sBit;
            unsetBits = usBit;
        }

        public int getCountOfBits() {
            return countOfBits;
        }

        public int getSetBits() {
            return setBits;
        }

        public int getUnsetBits() {
            return unsetBits;
        }

        @Override
        public int compareTo(Element o) {
            return this.getCountOfBits()-o.getCountOfBits();
        }
    }
}
