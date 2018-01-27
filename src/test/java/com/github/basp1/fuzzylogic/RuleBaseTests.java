package com.github.basp1.fuzzylogic;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RuleBaseTests {
    @Test
    public void testTsukamoto() throws Throwable {
        Vocabulary voc = new Vocabulary();

        Variable a1 = voc.make("a1");
        a1.assume("a11", new Fuzzyset.CValue(1, 0.7));
        a1.assume("a12", new Fuzzyset.CValue(1, 0.6));

        Variable a2 = voc.make("a2");
        a2.assume("a21", new Fuzzyset.CValue(2, 0.3));
        a2.assume("a22", new Fuzzyset.CValue(2, 0.8));

        Variable b = voc.make("b");
        b.assume("b1", new Fuzzyset.CValue(8));
        b.assume("b2", new Fuzzyset.CValue(4));

        RuleBase rb = new RuleBase(new Basis.Mamdani(), voc);

        rb.add(new Rule(new And(new Is(a1, "a11"), new Is(a2, "a21")), new Is(b, "b1")));
        rb.add(new Rule(new And(new Is(a1, "a12"), new Is(a2, "a22")), new Is(b, "b2")));

        rb.set("a1", 1);
        rb.set("a2", 2);

        double answer = rb.eval().defuzzification();

        assertEquals(5.3333333333, answer, 1e-8);
    }

    @Test
    public void testKosko() throws Throwable {
        Vocabulary voc = new Vocabulary();

        Variable temperature = voc.make("temperature");
        temperature.assume("cold", new Fuzzyset.PiecewiseLinear(new double[]{5, 10}, new double[]{1, 0}));
        temperature.assume("cool", new Fuzzyset.PiecewiseLinear(new double[]{5, 12, 17}, new double[]{0, 1, 0}));
        temperature.assume("right", new Fuzzyset.PiecewiseLinear(new double[]{15, 20, 25}, new double[]{0, 1, 0}));
        temperature.assume("warm", new Fuzzyset.PiecewiseLinear(new double[]{20, 26, 32}, new double[]{0, 1, 0}));
        temperature.assume("hot", new Fuzzyset.PiecewiseLinear(new double[]{30, 35}, new double[]{0, 1}));

        Variable speed = voc.make("speed");
        speed.assume("stop", new Fuzzyset.PiecewiseLinear(new double[]{0, 20}, new double[]{1, 0}));
        speed.assume("slow", new Fuzzyset.PiecewiseLinear(new double[]{10, 30, 50}, new double[]{0, 1, 0}));
        speed.assume("medium", new Fuzzyset.PiecewiseLinear(new double[]{40, 60, 80}, new double[]{0, 1, 0}));
        speed.assume("fast", new Fuzzyset.PiecewiseLinear(new double[]{60, 80, 100}, new double[]{0, 1, 0}));
        speed.assume("blast", new Fuzzyset.PiecewiseLinear(new double[]{80, 100}, new double[]{0, 1}));

        RuleBase rb = new RuleBase(new Basis.Mamdani(), voc);

        rb.add(new Rule(new And(new Is(temperature, "cold")), new Is(speed, "stop")));
        rb.add(new Rule(new And(new Is(temperature, "cool")), new Is(speed, "slow")));
        rb.add(new Rule(new And(new Is(temperature, "right")), new Is(speed, "medium")));
        rb.add(new Rule(new And(new Is(temperature, "warm")), new Is(speed, "fast")));
        rb.add(new Rule(new And(new Is(temperature, "hot")), new Is(speed, "blast")));

        rb.set("temperature", 16);

        double answer = rb.eval().defuzzification();

        assertEquals(41.42857142857142, answer, 1e-8);
    }
}