package com.ivandr;


import org.apache.commons.math3.distribution.IntegerDistribution;

import java.util.*;
import java.util.stream.Collectors;

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
    }

    public void minimizeBoolFunc() {
        computeAllPossibleVariants();

        for (int i = cntOfArguments; i > 0; i--) {

            var tmpTree = (TreeSet<Element>) table.get(i).clone();
            for (var j : table.get(i)) {

                boolean used = false;
                var possibleMerge = (TreeSet<Element>) table.get(i).tailSet(new Element(j.getCountOfBits() + 1, 0, 0));


                for (Element elem : possibleMerge) {

                    if (elem.getCountOfBits() > j.getCountOfBits() + 1) break;

                    if (isMergeable(j, elem)) {
                        table.get(i - 1).add(merge(j, elem));
                        tmpTree.remove(j);
                        tmpTree.remove(elem);
                        used = true;
                    }
                }
            }

            table.set(i, tmpTree);
        }
        int a = 1 + 1;

    }

    private void computeAllPossibleVariants() {

        for (int i = 0; i <= cntOfArguments; i++) table.add(new TreeSet<>());

        for (int i = 0; i <= MAXX; i++) {
            System.out.println(Integer.toBinaryString(i) + " " + boolFunc.parse(i));
            if (boolFunc.parse(i)) {
                table.get(cntOfArguments).add(new Element(Integer.bitCount(i), i, (~i & MAXX)));
            }
        }
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

    public String getMinimizedFunction() {
        StringBuilder res = new StringBuilder();
        if (table.size() == 0) minimizeBoolFunc();

        char[] arguments = new char[cntOfArguments];
        for (var i : boolFunc.getArguments().entrySet()) arguments[i.getValue()] = i.getKey();

        for (int i = cntOfArguments; i >= 1; i--) {
            if (table.get(i).size()==0) continue;
            for (Element elem : table.get(i)) {
                res.append('(');
                for (int j = 1; j <= cntOfArguments; j++) {;
                    int tmp = (1<<(cntOfArguments-j));
                    if ((tmp&elem.getSetBits())==tmp) res.append(String.format("%c&",arguments[j-1]));
                    if ((tmp&elem.getUnsetBits())==tmp) res.append(String.format("!%c&",arguments[j-1]));
                }
                res.deleteCharAt(res.length()-1);
                res.append(")|");
            }
        }
        res.deleteCharAt(res.length()-1);
        return res.toString();
    }

    private class Element implements Comparable<Element> {
        private int countOfBits;
        private int setBits, unsetBits;

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
            int tmp = Integer.compare(this.getCountOfBits(), o.getCountOfBits());
            if (tmp == 0) tmp = Integer.compare(this.getSetBits(), o.getSetBits());
            if (tmp == 0) tmp = Integer.compare(this.getUnsetBits(), o.getUnsetBits());
            return tmp;
        }
    }
}
