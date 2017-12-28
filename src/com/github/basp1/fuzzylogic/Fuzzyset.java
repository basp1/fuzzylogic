package com.github.basp1.fuzzylogic;

import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface Fuzzyset {
    double apply(double value);

    Point[] getPoints();

    Fuzzyset map(Function<Double, Double> func);

    Fuzzyset mapWith(Fuzzyset other, BiFunction<Double, Double, Double> func);

    double getHeight();

    class PiecewiseLinear implements Fuzzyset {
        double[] xs;
        double[] ys;

        public PiecewiseLinear(double[] xs, double[] ys) {
            if (xs.length != ys.length) {
                throw new IndexOutOfBoundsException();
            }
            this.xs = Arrays.copyOf(xs, xs.length);
            this.ys = Arrays.copyOf(ys, ys.length);
        }

        public PiecewiseLinear(int scatter, double[] xs, double[] ys) {
            if (xs.length != ys.length) {
                throw new IndexOutOfBoundsException();
            }

            this.xs = xs;
            this.ys = ys;

            int n = xs.length + scatter * (xs.length - 1);

            double[] xs_ = new double[n];
            double[] ys_ = new double[n];
            double step = 0;
            for (int i = 0; i < (n - 1); i++) {
                int j = i / (1 + scatter);
                if (0 == i % (1 + scatter)) {
                    xs_[i] = xs[j];
                    ys_[i] = ys[j];
                    step = (xs[j + 1] - xs[j]) / (1 + scatter);
                } else {
                    xs_[i] = xs_[i - 1] + step;
                    ys_[i] = apply(xs_[i]);
                }
            }
            xs_[xs_.length - 1] = xs[xs.length - 1];
            ys_[ys_.length - 1] = ys[ys.length - 1];

            this.xs = xs_;
            this.ys = ys_;
        }

        @Override
        public double apply(double x) {
            int index = Arrays.binarySearch(xs, x);

            if (index >= 0 && index < xs.length) {
                return ys[index];
            }

            if (-1 == index) {
                return ys[0];
            }

            index = Math.abs(index) - 2;

            if (index >= (xs.length - 1)) {
                return ys[xs.length - 1];
            }

            double y = (ys[index + 1] - ys[index]) * (x - xs[index]) / (xs[index + 1] - xs[index]);

            return y + ys[index];
        }

        @Override
        public Point[] getPoints() {
            Point[] points = new Point[xs.length];

            for (int i = 0; i < points.length; i++) {
                points[i] = new Point(xs[i], ys[i]);
            }

            return points;
        }

        @Override
        public Fuzzyset map(Function<Double, Double> func) {
            double[] xs_ = Arrays.copyOf(xs, xs.length);
            double[] ys_ = new double[ys.length];

            for (int i = 0; i < ys.length; i++) {
                ys_[i] = func.apply(ys[i]);
            }

            return new PiecewiseLinear(xs_, ys_);
        }

        @Override
        public Fuzzyset mapWith(Fuzzyset other, BiFunction<Double, Double, Double> func) {
            int i = 0;
            SortedSet<Double> set = new TreeSet<>();

            for (i = 0; i < xs.length; i++) {
                set.add(xs[i]);
            }

            Point[] points = other.getPoints();
            for (i = 0; i < points.length; i++) {
                set.add(points[i].getX());
            }

            i = 0;
            double[] xs_ = new double[set.size()];
            double[] ys_ = new double[set.size()];
            for (Double a : set) {
                xs_[i] = a;
                ys_[i] = func.apply(apply(a), other.apply(a));
                i++;
            }

            return new PiecewiseLinear(xs_, ys_);
        }

        @Override
        public double getHeight() {
            return Arrays.stream(ys).max().getAsDouble();
        }
    }

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