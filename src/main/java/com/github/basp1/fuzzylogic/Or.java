package com.github.basp1.fuzzylogic;

import java.util.Arrays;

public class Or implements Term {
    Term[] terms;

    public Or(Term... terms) {
        super();

        this.terms = Arrays.copyOf(terms, terms.length);
    }

    @Override
    public double apply(Basis basis) {
        double acc = terms[0].apply(basis);

        for (int i = 1; i < terms.length; i++) {
            double a = terms[i].apply(basis);
            acc = basis.or(acc, a);
        }

        return acc;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < terms.length; i++) {
            Term term = terms[i];
            sb.append("(");
            sb.append(term.toString());
            sb.append(")");
            if (i < (terms.length - 1)) {
                sb.append(" OR ");
            }
        }

        return sb.toString();
    }
}