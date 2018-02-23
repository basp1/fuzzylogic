package com.github.basp1.fuzzylogic;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Vocabulary {
    Map<String, Variable> variables;

    public Vocabulary() {
        this.variables = new HashMap<>();
    }

    public Variable make(String name) {
        Variable var = Variable.assume(this, name);
        return var;
    }

    public boolean contains(String name) {
        return variables.containsKey(name);
    }

    public void add(Variable var) {
        variables.put(var.getName(), var);
    }

    public Variable get(String name) {
        return variables.get(name);
    }

    public Collection<Variable> getAll() {
        return variables.values();
    }
}