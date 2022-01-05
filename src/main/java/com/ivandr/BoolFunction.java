package com.ivandr;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.*;


public class BoolFunction {
    private CharacterIterator it;
    private final String expr;
    private Expression compiledResult;
    private HashMap<Character,Integer> arguments;
    private int binaryRepresentation;

    public BoolFunction(String expression) {
        expr = expression;
        arguments = new HashMap<>();
        it = new StringCharacterIterator(expression);
        getAllArguments();
    }

    public boolean parse(int bin) {
        binaryRepresentation = bin;
        if (compiledResult == null) compile();
        return compiledResult.eval();
    }

    private boolean accept(char charToAccept) {
        while (it.current() == ' ') it.next();
        if (it.current() == charToAccept) {
            it.next();
            return true;
        }
        return false;
    }


    private void compile() {
        compiledResult = parseExpression();
        if (it.current() != CharacterIterator.DONE)
            throw new RuntimeException("Unexpected char: " + it.current());
        else it = new StringCharacterIterator(expr);
    }



    private Expression parseExpression() {
        Expression x = parseTerm();

        for (; ; ) {
            if (accept('&')) {
                Expression a = x, b = parseTerm();
                x = (() -> a.eval() && b.eval());
            }
            else if (accept('|')) {
                Expression a = x, b = parseTerm();
                x = (() -> a.eval() || b.eval());
            }
            else if (accept('^')) {
                Expression a = x, b = parseTerm();
                x = (() -> a.eval() ^ b.eval());
            }
            else if (accept('/')) {
                Expression a = x, b = parseTerm();
                x = (() -> a.eval() && (!b.eval()));
            }
            else return x;
        }
    }

    private Expression parseTerm() {
        Expression x;
        if (accept('!')) x = ( () -> !(parseTerm().eval()));
        else if (accept('(')) {
            x = parseExpression();
            if (!accept(')'))
                throw new RuntimeException("Expected parentheses: " + it.current());
            return x;
        }
        else if (accept('0')) {
            x = ( () -> false);
        }
        else if (accept('1')) {
            x = ( () -> true);
        }
        else {
            //System.out.println(it.current()+" "+argumentParser.toString() + " "+argumentParser.get(it.current()));
            char tmp = it.current();
            x = ( () -> ((1 << arguments.get(tmp)) & binaryRepresentation) != 0);

            it.next();
        }

        return x;
    }

    private void getAllArguments() {
        for (int i=0;i<expr.length();i++) {
            if (expr.charAt(i) >= 'a' && expr.charAt(i) <= 'z') arguments.put(expr.charAt(i), arguments.size());
        }
    }

    public int getNumberOfArguments() {
        return expr.length();
    }

    @FunctionalInterface
    interface Expression {
        boolean eval();
    }
}
