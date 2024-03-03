package com.example.calculator;

import junit.framework.TestCase;

import org.junit.Test;

public class UtilsTest extends TestCase {

    @Test
    public void testValidExpression() {
        String expression = "2+3*4";
        String expected = "14";
        String result = Utils.getResult(expression);
        assertEquals(expected, result);
    }

    @Test
    public void testEmptyExpression() {
        String expression = "";
        String expected = "NaN";
        String result = Utils.getResult(expression);
        assertEquals(expected, result);
    }

    @Test
    public void testInvalidExpression() {
        String expression = "2+*";
        String expected = "Error";
        String result = Utils.getResult(expression);
        assertEquals(expected, result);
    }

    @Test
    public void testExpressionWithWhitespace() {
        String expression = " 1 + 2 * 3 ";
        String expected = "7";
        String result = Utils.getResult(expression);
        assertEquals(expected, result);
    }

    @Test
    public void testNegativeNumber() {
        String expression = "-10";
        String expected = "-10";
        String result = Utils.getResult(expression);
        assertEquals(expected, result);
    }

    @Test
    public void testDivision() {
        String expression = "6/2";
        String expected = "3";
        String result = Utils.getResult(expression);
        assertEquals(expected, result);
    }

    @Test
    public void testExponentiation() {
        String expression = "2^3";
        String expected = "8";
        String result = Utils.getResult(expression);
        assertEquals(expected, result);
    }

    @Test
    public void testExpressionWithVariables() {
        String expression = "a+b*c";
        String expected = "Error";
        String result = Utils.getResult(expression);
        assertEquals(expected, result);
    }

    @Test
    public void testExpressionWithSquareRoot() {
        String expression = "sqrt(9)";
        String expected = "3";
        String result = Utils.getResult(expression);
        assertEquals(expected, result);
    }

    @Test
    public void testSinFunctionWithDegrees() {
        String expression = "sin(45)";
        String expected = "0.7071067812";
        String result = Utils.getResult(expression);
        assertEquals(expected, result);
    }

    @Test
    public void testCosFunctionWithDegrees() {
        String expression = "cos(45)";
        String expected = "0.7071067812";
        String result = Utils.getResult(expression);
        assertEquals(expected, result);
    }

    @Test
    public void testTanFunctionWithDegrees() {
        String expression = "tan(45)";
        String expected = "1";
        String result = Utils.getResult(expression);
        assertEquals(expected, result);
    }

    @Test
    public void testArcSinFunction() {
        String expression = "asin(0.5)";
        String expected = "30";
        String result = Utils.getResult(expression);
        assertEquals(expected, result);
    }

    @Test
    public void testArcCosFunction() {
        String expression = "acos(0.5)";
        String expected = "60";
        String result = Utils.getResult(expression);
        assertEquals(expected, result);
    }

    @Test
    public void testArcTanFunction() {
        String expression = "atan(1)";
        String expected = "45";
        String result = Utils.getResult(expression);
        assertEquals(expected, result);
    }

    @Test
    public void testLnFunction() {
        String expression = "log(10)";
        String expected = "2.302585093";
        String result = Utils.getResult(expression);
        assertEquals(expected, result);
    }

    @Test
    public void testLog10Function() {
        String expression = "log10(100)";
        String expected = "2";
        String result = Utils.getResult(expression);
        assertEquals(expected, result);
    }

    @Test
    public void testReciprocalFunction() {
        String expression = "1/5";
        String expected = "0.2";
        String result = Utils.getResult(expression);
        assertEquals(expected, result);
    }

    @Test
    public void testExponentialFunction() {
        String expression = "e^3";
        String expected = "20.08553691";
        String result = Utils.getResult(expression);
        assertEquals(expected, result);
    }

    @Test
    public void testSquareFunction() {
        String expression = "2^2";
        String expected = "4";
        String result = Utils.getResult(expression);
        assertEquals(expected, result);
    }

    @Test
    public void testHyperbolicSinFunction() {
        String expression = "sinh(1)";
        String expected = "1.175201194";
        String result = Utils.getResult(expression);
        assertEquals(expected, result);
    }

    @Test
    public void testHyperbolicCosFunction() {
        String expression = "cosh(1)";
        String expected = "1.543080635";
        String result = Utils.getResult(expression);
        assertEquals(expected, result);
    }

    @Test
    public void testHyperbolicTanFunction() {
        String expression = "tanh(1)";
        String expected = "0.761594156";
        String result = Utils.getResult(expression);
        assertEquals(expected, result);
    }

    @Test
    public void testFactorialFunction() {
        String expression = "5! + 30";
        String expected = "150";
        String result = Utils.getResult(expression);
        assertEquals(expected, result);
    }

    @Test
    public void testPiConstant() {
        String expression = "pi";
        String expected = "3.141592654";
        String result = Utils.getResult(expression);
        assertEquals(expected, result);
    }
}