package com.github.basp1.fuzzylogic;

public class Not implements Term {
    Term term;

    public Not(Term term) {
        super();

        this.term = term;
    }

    @Override
    public double apply(Basis basis) {
        return basis.not(term.apply(basis));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("NOT (");
        sb.append(term.toString());
        sb.append(")");

        return sb.toString();
    }
}