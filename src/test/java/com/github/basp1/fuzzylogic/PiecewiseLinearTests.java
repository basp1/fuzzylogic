package com.github.basp1.fuzzylogic;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PiecewiseLinearTests {
    @Test
    public void testPoint() {
        Fuzzyset fs = new PiecewiseLinear(new double[]{10}, new double[]{1});

        assertEquals(1, fs.apply(0), 1e-8);
        assertEquals(1, fs.apply(10), 1e-8);
        assertEquals(1, fs.apply(100), 1e-8);
    }

    @Test
    public void testLinearUp() {
        Fuzzyset fs = new PiecewiseLinear(new double[]{5, 10}, new double[]{0, 1});

        assertEquals(0, fs.apply(0), 1e-8);
        assertEquals(0, fs.apply(5), 1e-8);
        assertEquals(1, fs.apply(10), 1e-8);
        assertEquals(1, fs.apply(100), 1e-8);

        assertTrue(1 > fs.apply(9.87));
        assertTrue(fs.apply(9.87) > 0);
        assertTrue(fs.apply(9.88) > fs.apply(9.87));
        assertTrue(fs.apply(9.87) > fs.apply(9.86));
    }

    @Test
    public void testLinearDown() {
        Fuzzyset fs = new PiecewiseLinear(new double[]{5, 10}, new double[]{1, 0});

        assertEquals(1, fs.apply(0), 1e-8);
        assertEquals(1, fs.apply(5), 1e-8);
        assertEquals(0, fs.apply(10), 1e-8);
        assertEquals(0, fs.apply(100), 1e-8);

        assertTrue(1 > fs.apply(9.87));
        assertTrue(fs.apply(9.87) > 0);
        assertTrue(fs.apply(9.88) < fs.apply(9.87));
        assertTrue(fs.apply(9.87) < fs.apply(9.86));
    }

    @Test
    public void testTriangle() {
        Fuzzyset fs = new PiecewiseLinear(new double[]{5, 10, 15}, new double[]{0, 1, 0});

        assertEquals(0, fs.apply(0), 1e-8);
        assertEquals(0, fs.apply(5), 1e-8);
        assertEquals(1, fs.apply(10), 1e-8);
        assertEquals(0, fs.apply(50), 1e-8);

        assertTrue(1 > fs.apply(9.87));
        assertTrue(fs.apply(9.87) > 0);
        assertTrue(fs.apply(9.88) > fs.apply(9.87));
        assertTrue(fs.apply(9.87) > fs.apply(9.86));
        assertEquals(fs.apply(9.87), fs.apply(10.13), 1e-8);
    }
}