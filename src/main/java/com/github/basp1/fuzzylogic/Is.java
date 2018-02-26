package com.github.basp1.fuzzylogic;

import com.github.basp1.fuzzylogic.exceptions.UnexpectedValueException;

public class Is implements Term {
    Variable variable;
    Value value;

    public Is(Variable variable, Value value) {
        this.variable = variable;
        this.value = value;
    }

    public Is(Variable variable, String value) throws UnexpectedValueException {
        this(variable, variable.get(value));
    }

    @Override
    public double apply(Basis basis) {
        double val = variable.getAttachedValue();

        return value.getFuzzyset().apply(val);
    }

    public Variable getVariable() {
        return variable;
    }

    public Value getValue() {
        return value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(variable.getName());
        sb.append(" IS ");
        sb.append(value.getName());

        return sb.toString();
    }
}