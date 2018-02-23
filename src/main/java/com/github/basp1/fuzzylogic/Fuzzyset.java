package com.github.basp1.fuzzylogic;

import java.util.function.BiFunction;
import java.util.function.Function;

public interface Fuzzyset {
    double apply(double value);

    Point[] getPoints();

    Fuzzyset map(Function<Double, Double> func);

    Fuzzyset mapWith(Fuzzyset other, BiFunction<Double, Double, Double> func);

    double getHeight();

    class CValue extends PiecewiseLinear {
        final static double tol = 1e-8;

        public CValue(double value) {
            this(value, 1);
        }

        public CValue(double value, double membership) {
            super(new double[]{value - tol, value, value + tol}, new double[]{0, membership, 0});
        }
    }
}