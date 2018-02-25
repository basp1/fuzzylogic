package com.github.basp1.fuzzylogic;

import com.github.basp1.fuzzylogic.exceptions.DecisionEvaluationException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RuleBase {
    Basis basis;
    Vocabulary vocabulary;
    ArrayList<Rule> rules;

    public RuleBase(Basis basis, Vocabulary vocabulary) {
        this.basis = basis;
        this.rules = new ArrayList<>();
        this.vocabulary = vocabulary;
    }

    public RuleBase add(Rule rule) {
        rules.add(rule);
        return this;
    }

    public RuleBase addAll(List<Rule> rules) {
        this.rules.addAll(rules);
        return this;
    }

    public Value eval() throws DecisionEvaluationException {
        Map<String, Value> deducts = new HashMap<>();

        for (Rule rule : rules) {
            Value value = rule.apply(basis);
            String name = value.getVariable().getName();
            if (!deducts.containsKey(name)) {
                deducts.put(name, value);
            } else {
                Value acc = value.mapWith(deducts.get(name), (a, b) -> basis.or(a, b));
                deducts.put(name, acc);
            }
        }

        Value deduct = null;
        double max = 0;
        for (Value value : deducts.values()) {
            double height = value.getFuzzyset().getHeight();
            if (height >= max) {
                max = height;
                deduct = value;
            }
        }

        return deduct;
    }

    public void set(String name, double value) {
        if(!vocabulary.contains(name)) return;
        vocabulary.get(name).setAttachedValue(value);
    }

    public ArrayList<Rule> getRules() {
        return rules;
    }

    public void setRules(ArrayList<Rule> rules) {
        this.rules = rules;
    }

    public Basis getBasis() {
        return basis;
    }

    public Vocabulary getVocabulary() {
        return vocabulary;
    }

    public Map<String, ArrayList<Rule>> getNamedRules() {
        Map<String, ArrayList<Rule>> namedRules = new HashMap<>();

        for (Rule rule : getRules()) {
            String name = rule.getConsequent().getVariable().getName();
            if (!namedRules.containsKey(name)) {
                namedRules.put(name, new ArrayList<>());
            }
            namedRules.get(name).add(rule);
        }

        return namedRules;
    }
}