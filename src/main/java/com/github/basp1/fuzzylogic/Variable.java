package com.github.basp1.fuzzylogic;

import com.github.basp1.fuzzylogic.exceptions.UnexpectedValueException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Variable {
    String name;
    Map<String, Value> domain;
    double attachedValue;

    private Variable(String name) {
        this.name = name;
        this.domain = new HashMap<>();
    }

    public static Variable assume(Vocabulary voc, String name) {
        Variable var = new Variable(name);
        voc.add(var);
        return var;
    }

    public Value assume(Value value) {
        domain.put(value.getName(), value);
        return value;
    }

    public Value assume(String value, Fuzzyset fs) {
        return assume(new Value(this, value, fs));
    }

    public String getName() {
        return name;
    }

    public double getAttachedValue() {
        return attachedValue;
    }

    public void setAttachedValue(double newValue) {
        this.attachedValue = newValue;
    }

    public Value get(String name) throws UnexpectedValueException {
        if (!domain.containsKey(name)) {
            throw new UnexpectedValueException(name);
        }
        return domain.get(name);
    }

    public Collection<Value> getAll() {
        return domain.values();
    }
}