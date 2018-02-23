package com.github.basp1.fuzzylogic;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FuzzysetMapTests {
    @Test
    public void testMapWithTriangle() {
        Fuzzyset fs1 = new PiecewiseLinear(new double[]{1, 3, 5}, new double[]{0, 1, 0});
        Fuzzyset fs2 = new PiecewiseLinear(new double[]{4, 6, 8}, new double[]{0, 1, 0});
        Fuzzyset fs12 = fs1.mapWith(fs2, (a, b) -> Math.max(a, b));

        assertEquals(0, fs12.apply(1), 1e-8);
        assertEquals(1, fs12.apply(3), 1e-8);
        assertTrue(fs12.apply(4) > 0);
        assertEquals(fs1.apply(4), fs12.apply(4), 1e-8);
        assertTrue(fs12.apply(5) > 0);
        assertEquals(fs2.apply(5), fs12.apply(5), 1e-8);
        assertEquals(fs12.apply(3.5), fs12.apply(5.5), 1e-8);
        assertEquals(1, fs12.apply(6), 1e-8);
        assertEquals(fs12.apply(5.5), fs12.apply(6.5), 1e-8);
        assertEquals(0, fs12.apply(8), 1e-8);
    }

    @Test
    public void testMapTriangle() {
        double alpha = 0.8;

        Fuzzyset fs = new PiecewiseLinear(4, new double[]{1, 3, 5}, new double[]{0, 1, 0});
        assertEquals(1.0, fs.getHeight(), 1e-8);
        assertEquals(11, fs.getPoints().length);

        fs = fs.map(a -> Math.min(alpha, a));
        assertEquals(11, fs.getPoints().length);
        assertEquals(alpha, fs.getHeight(), 1e-8);
        assertEquals(alpha, fs.apply(3), 1e-8);
        assertEquals(alpha, fs.apply(2.9), 1e-8);
        assertEquals(alpha, fs.apply(3.1), 1e-8);
        assertEquals(0, fs.apply(1), 1e-8);
        assertEquals(0, fs.apply(5), 1e-8);
    }
}