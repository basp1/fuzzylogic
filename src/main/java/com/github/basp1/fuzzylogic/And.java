package com.github.basp1.fuzzylogic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class And implements Term {
    ArrayList<Term> terms;

    public And(Term term, Term... terms) {
        super();

        this.terms = new ArrayList<>();
        this.terms.add(term);
        this.terms.addAll(Arrays.asList(terms));
    }

    public And(Collection<Term> terms) {
        super();

        this.terms = new ArrayList<>(terms);
    }

    public ArrayList<Term> getTerms() {
        return terms;
    }

    @Override
    public double apply(Basis basis) {
        double acc = terms.get(0).apply(basis);

        for (int i = 1; i < terms.size(); i++) {
            double a = terms.get(i).apply(basis);
            acc = basis.and(acc, a);
        }

        return acc;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < terms.size(); i++) {
            Term term = terms.get(i);
            sb.append("(");
            sb.append(term.toString());
            sb.append(")");
            if (i < (terms.size() - 1)) {
                sb.append(" AND ");
            }
        }

        return sb.toString();
    }
}