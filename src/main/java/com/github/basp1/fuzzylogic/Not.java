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
}