package com.github.basp1.fuzzylogic;

public interface Basis {
    double and(double a, double b);

    double or(double a, double b);

    double implicate(double a, double b);

    double not(double a);

    class Mamdani implements Basis {
        @Override
        public double and(double a, double b) {
            return Math.min(a, b);
        }

        @Override
        public double or(double a, double b) {
            return Math.max(a, b);
        }

        @Override
        public double implicate(double a, double b) {
            return Math.min(a, b);
        }

        @Override
        public double not(double a) {
            return 1 - a;
        }
    }

    class Lukasiewicz implements Basis {

        @Override
        public double and(double a, double b) {
            return Math.max(a + b - 1, 0);
        }

        @Override
        public double or(double a, double b) {
            return Math.min(a + b, 1);
        }

        @Override
        public double implicate(double a, double b) {
            return Math.min(1, 1 - (a + b));
        }

        @Override
        public double not(double a) {
            return 1 - a;
        }
    }

    class Strong implements Basis {

        @Override
        public double and(double a, double b) {
            if (a >= 0 && a < 1 && b >= 0 && b < 1) {
                return 0;
            } else {
                return Math.min(a, b);
            }
        }

        @Override
        public double or(double a, double b) {
            if (a > 0 && a <= 1 && b > 0 && b <= 1) {
                return 1;
            } else {
                return Math.max(a, b);
            }
        }

        @Override
        public double implicate(double a, double b) {
            return Math.max(1 - a, b);
        }

        @Override
        public double not(double a) {
            return 1 - a;
        }
    }
}