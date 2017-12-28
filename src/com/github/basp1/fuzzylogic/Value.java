package com.github.basp1.fuzzylogic;

import com.github.basp1.fuzzylogic.Point;

import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Value {
    Variable variable;
    String name;
    Fuzzyset fuzzyset;

    public Value(Variable variable, String name, Fuzzyset fuzzyset) {
        this.variable = variable;
        this.name = name;
        this.fuzzyset = fuzzyset;
    }

    public Variable getVariable() {
        return variable;
    }

    public String getName() {
        return name;
    }

    public Fuzzyset getFuzzyset() {
        return fuzzyset;
    }

    public double defuzzification() {
        Point[] points = fuzzyset.getPoints();

        double numerator = 0;
        double denominator = 0;

        for (int i = 0; i < points.length; i++) {
            numerator += (points[i].getX() * points[i].getY());
            denominator += points[i].getY();
        }

        double value = numerator / denominator;

        return value;
    }

    public Value map(Function<Double, Double> func) {
        return new Value(getVariable(), UUID.randomUUID().toString(),
                getFuzzyset().map(func));
    }

    public Value mapWith(Value other, BiFunction<Double, Double, Double> func) {
        return new Value(getVariable(), UUID.randomUUID().toString(),
                getFuzzyset().mapWith(other.getFuzzyset(), func));
    }
}