package ubs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class StringAccumulatorTest {

    private final StringAccumulator sa = new StringAccumulator();

    @Test
    public void testNull() throws Exception {
        assertEquals(0, sa.add(null));
    }

    @Test
    public void testEmptyString() throws Exception {
        assertEquals(0, sa.add(""));
    }

    @Test
    public void testOneNumber() throws Exception {
        assertEquals(1, sa.add("1"));
    }

    @Test
    public void testTwoNumber() throws Exception {
        assertEquals(3, sa.add("1,2"));
    }

    @Test
    public void testThreeNumber() throws Exception {
        assertEquals(6, sa.add("1,2,3"));
    }

    @Test
    public void testTenNumber() throws Exception {
        assertEquals(55, sa.add("1,2,3,4,5,6,7,8,9,10"));
    }

    @Test
    public void testMixedDelimiters() throws Exception {
        assertEquals(6, sa.add("1\n2,3"));
    }

    @Test
    public void testOneCustomDelimiter() throws Exception {
        assertEquals(3, sa.add("//;\n1;2"));
    }
    
    @Test
    public void testOneCustomDelimiterMixedDefaultDelimiters() throws Exception {
        assertEquals(10, sa.add("//;\n1;2\n3,4"));
    }

    @Test
    public void testOneNegativeNumber() {
        try {
            sa.add("1\n-2,3");
            fail("Expected exception was not occured.");
        } catch (Exception ex) {
            assertEquals("negatives not allowed-2", ex.getMessage());
        }
    }

    @Test
    public void testTwoNegativeNumbers() {
        try {
            sa.add("-1\n2,-3");
            fail("Expected exception was not occured.");
        } catch (Exception ex) {
            assertEquals("negatives not allowed-1,-3", ex.getMessage());
        }
    }

    @Test
    public void testNumberExcess1000() throws Exception {
        assertEquals(2, sa.add("2,1001"));
    }

    @Test
    public void testOneCustomDelimiterWithOneSpecialCharacter() throws Exception {
        assertEquals(6, sa.add("//*\n1*2*3"));
    }

    @Test
    public void testOneCustomDelimiterWithMultipleSpecialCharacters() throws Exception {
        assertEquals(6, sa.add("//***\n1***2***3"));
    }

    @Test
    public void testOneCustomDelimiterWithOneCharacter() throws Exception {
        assertEquals(6, sa.add("//a\n1a2a3"));
        assertEquals(3, sa.add("//;\n1;2"));
    }

    @Test
    public void testOneCustomDelimiterWithMultipleCharacters() throws Exception {
        assertEquals(6, sa.add("//aaa\n1aaa2aaa3"));
    }

    @Test
    public void testTwoCustomDelimiterWithOneCharacters() throws Exception {
        assertEquals(6, sa.add("//*|%\n1*2%3"));
    }

    @Test
    public void testTwoCustomDelimiterWithMultipleCharacters() throws Exception {
        assertEquals(6, sa.add("//***|%%\n1***2%%3"));
    }
}
