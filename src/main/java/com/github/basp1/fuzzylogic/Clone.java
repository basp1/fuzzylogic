package com.github.basp1.fuzzylogic;

public class Clone implements Term {
    Term parent;

    public Clone(Term term) {
        if (term instanceof Clone && ((Clone) term).getRoot() instanceof Clone) {
            throw new NullPointerException();
        }
        this.parent = term;
    }

    public Term getParent() {
        return parent;
    }

    public Term getRoot() {
        Term root = parent;
        while (root != null && root instanceof Clone) {
            root = ((Clone) root).getParent();
        }
        return root;
    }

    @Override
    public double apply(Basis basis) {
        return getRoot().apply(basis);
    }
}