package com.github.basp1.fuzzylogic;

public class Rule {
    And antecedent;
    Is consequent;

    public Rule(And antecedent, Is consequent) {
        this.antecedent = antecedent;
        this.consequent = consequent;
    }

    public Value apply(Basis basis) {
        double alpha = antecedent.apply(basis);

        Value deduct = consequent.getValue().map(a -> basis.implicate(alpha, a));

        return deduct;
    }

    public And getAntecedent() {
        return antecedent;
    }

    public Is getConsequent() {
        return consequent;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("IF\n    ");
        sb.append(antecedent.toString());
        sb.append("\nTHEN\n    ");
        sb.append(consequent.toString());

        return sb.toString();
    }
}