package com.ivandr;


import org.apache.commons.math3.distribution.IntegerDistribution;

import java.util.*;

public class QuineMcCluskey {

    private final List<TreeSet<Element>> table;
    private final BoolFunction boolFunc;
    private final int MAXX;
    private final int cntOfArguments;

    public QuineMcCluskey(String str) {
        boolFunc = new BoolFunction(str);
        cntOfArguments = boolFunc.getNumberOfArguments();
        table = new ArrayList<>(cntOfArguments + 1);
        MAXX = (1 << (cntOfArguments)) - 1;
        computeAllPossibleVariants();
    }

    public void minimizeBoolFunc() {

        for (int i = cntOfArguments; i > 0; i--) {

            var tmpTree = (TreeSet<Element>) table.get(i).clone();
            for (var j : table.get(i)) {

                boolean used = false;
                var possibleMerge = (TreeSet<Element>) table.get(i).tailSet(new Element(j.getCountOfBits() + 1, 0, 0));


                for (Element elem : possibleMerge) {

                    if (elem.getCountOfBits() > j.getCountOfBits() + 1) break;

                    if (isMergeable(j, elem)) {
                        table.get(i - 1).add(merge(j,elem));
                        tmpTree.remove(j);
                        tmpTree.remove(elem);
                        used = true;
                    }
                }
            }

            table.set(i,tmpTree);
        }
        int a = 1+1;

    }

    private void computeAllPossibleVariants() {

        for (int i=0;i<=cntOfArguments;i++) table.add(new TreeSet<>());

        for (int i = 0; i <= MAXX; i++) {
            if (boolFunc.parse(i)) {
                table.get(cntOfArguments).add(new Element(Integer.bitCount(i), i, (~i & MAXX)));
            }
        }

        System.out.println(table.get(cntOfArguments).size());
    }

    private boolean isMergeable(Element a, Element b) {
        int tmp = a.getSetBits() ^ b.getSetBits();
        boolean checkPowOfTwo = ((tmp & (tmp - 1)) == 0);
        boolean checkTheSameBit = ((a.getUnsetBits() ^ b.getUnsetBits()) == tmp);
        return (checkPowOfTwo && checkTheSameBit);
    }

    private Element merge(Element a, Element b) {
        int tmp = a.getSetBits() & b.getSetBits();
        return new Element(Integer.bitCount(tmp), tmp, a.getUnsetBits() & b.getUnsetBits());
    }

    private class Element implements Comparable<Element> {
        private int countOfBits;
        private int setBits, unsetBits;

        public Element(int cnt) {
            new Element(cnt, MAXX+1, MAXX+1);
        }

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
            int tmp = Integer.compare(this.getCountOfBits(),o.getCountOfBits());
            if (tmp==0) tmp = Integer.compare(this.getSetBits(), o.getSetBits());
            return tmp;
        }
    }
}
