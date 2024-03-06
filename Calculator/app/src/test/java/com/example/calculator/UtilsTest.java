package com.example.calculator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.example.calculator.customenum.ENumber;

import org.junit.Test;

public class UtilsTest {

    @Test
    public void testGetResult_ValidExpression() {
        String expression = "2+3*4";
        String expected = "14";
        String result = Utils.getResult(expression);
        assertEquals(expected, result);
    }

    @Test
    public void testGetResult_EmptyExpression() {
        String expression = "";
        String expected = "NaN";
        String result = Utils.getResult(expression);
        assertEquals(expected, result);
    }

    @Test
    public void testGetResult_InvalidExpression() {
        String expression = "2+*";
        String expected = "Error";
        String result = Utils.getResult(expression);
        assertEquals(expected, result);
    }

    @Test
    public void testGetResult_ExpressionWithWhitespace() {
        String expression = " 1 + 2 * 3 ";
        String expected = "7";
        String result = Utils.getResult(expression);
        assertEquals(expected, result);
    }

    @Test
    public void testGetResult_NegativeNumber() {
        String expression = "-10";
        String expected = "-10";
        String result = Utils.getResult(expression);
        assertEquals(expected, result);
    }

    @Test
    public void testGetResult_Division() {
        String expression = "6/2";
        String expected = "3";
        String result = Utils.getResult(expression);
        assertEquals(expected, result);
    }

    @Test
    public void testGetResult_Exponentiation() {
        String expression = "2^3";
        String expected = "8";
        String result = Utils.getResult(expression);
        assertEquals(expected, result);
    }

    @Test
    public void testGetResult_ExpressionWithVariables() {
        String expression = "a+b*c";
        String expected = "Error";
        String result = Utils.getResult(expression);
        assertEquals(expected, result);
    }

    @Test
    public void testGetResult_ExpressionWithSquareRoot() {
        String expression = "sqrt(9)";
        String expected = "3";
        String result = Utils.getResult(expression);
        assertEquals(expected, result);
    }

    @Test
    public void testGetResult_SinFunctionWithDegrees() {
        String expression = "sin(45)";
        String expected = "0.7071067812";
        String result = Utils.getResult(expression);
        assertEquals(expected, result);
    }

    @Test
    public void testGetResult_CosFunctionWithDegrees() {
        String expression = "cos(45)";
        String expected = "0.7071067812";
        String result = Utils.getResult(expression);
        assertEquals(expected, result);
    }

    @Test
    public void testGetResult_TanFunctionWithDegrees() {
        String expression = "tan(45)";
        String expected = "1";
        String result = Utils.getResult(expression);
        assertEquals(expected, result);
    }

    @Test
    public void testGetResult_ArcSinFunction() {
        String expression = "asin(0.5)";
        String expected = "30";
        String result = Utils.getResult(expression);
        assertEquals(expected, result);
    }

    @Test
    public void testGetResult_ArcCosFunction() {
        String expression = "acos(0.5)";
        String expected = "60";
        String result = Utils.getResult(expression);
        assertEquals(expected, result);
    }

    @Test
    public void testGetResult_ArcTanFunction() {
        String expression = "atan(1)";
        String expected = "45";
        String result = Utils.getResult(expression);
        assertEquals(expected, result);
    }

    @Test
    public void testGetResult_LnFunction() {
        String expression = "log(10)";
        String expected = "2.302585093";
        String result = Utils.getResult(expression);
        assertEquals(expected, result);
    }

    @Test
    public void testGetResult_Log10Function() {
        String expression = "log10(100)";
        String expected = "2";
        String result = Utils.getResult(expression);
        assertEquals(expected, result);
    }

    @Test
    public void testGetResult_ReciprocalFunction() {
        String expression = "1/5";
        String expected = "0.2";
        String result = Utils.getResult(expression);
        assertEquals(expected, result);
    }

    @Test
    public void testGetResult_ExponentialFunction() {
        String expression = "e^3";
        String expected = "20.08553691";
        String result = Utils.getResult(expression);
        assertEquals(expected, result);
    }

    @Test
    public void testGetResult_SquareFunction() {
        String expression = "2^2";
        String expected = "4";
        String result = Utils.getResult(expression);
        assertEquals(expected, result);
    }

    @Test
    public void testGetResult_HyperbolicSinFunction() {
        String expression = "sinh(1)";
        String expected = "1.175201194";
        String result = Utils.getResult(expression);
        assertEquals(expected, result);
    }

    @Test
    public void testGetResult_HyperbolicCosFunction() {
        String expression = "cosh(1)";
        String expected = "1.543080635";
        String result = Utils.getResult(expression);
        assertEquals(expected, result);
    }

    @Test
    public void testGetResult_HyperbolicTanFunction() {
        String expression = "tanh(1)";
        String expected = "0.761594156";
        String result = Utils.getResult(expression);
        assertEquals(expected, result);
    }

    @Test
    public void testGetResult_FactorialFunction() {
        String expression = "5! + 30";
        String expected = "150";
        String result = Utils.getResult(expression);
        assertEquals(expected, result);
    }

    @Test
    public void testGetResult_PiConstant() {
        String expression = "pi";
        String expected = "3.141592654";
        String result = Utils.getResult(expression);
        assertEquals(expected, result);
    }

    @Test
    public void testFindLastNumberIndex_EmptyExpression() {
        int result = Utils.findLastNumberIndex("");
        assertEquals(-1, result);
    }

    @Test
    public void testFindLastNumberIndex_PositiveNumber() {
        String expression = "234-2+3";
        int expectedIndex = 6;
        int result = Utils.findLastNumberIndex(expression);
        assertEquals(expectedIndex, result);
    }

    @Test
    public void testFindLastNumberIndex_NegativeNumber() {
        String expression = "19/2+(-3)";
        int expectedIndex = 5;
        int result = Utils.findLastNumberIndex(expression);
        assertEquals(expectedIndex, result);
    }

    @Test
    public void testFindLastNumberIndex_NoNumber() {
        String expression = "log(5)";
        int expectedIndex = -1;
        int result = Utils.findLastNumberIndex(expression);
        assertEquals(expectedIndex, result);
    }

    @Test
    public void testFindLastNumberIndex_FloatNumber() {
        String expression = "12.34";
        int expectedIndex = 0;
        int result = Utils.findLastNumberIndex(expression);
        assertEquals(expectedIndex, result);
    }

    @Test
    public void testFindLastNumberIndex_ComplexFloatNumber() {
        String expression = "34+64-log(5)+12.34";
        int expectedIndex = 13;
        int result = Utils.findLastNumberIndex(expression);
        assertEquals(expectedIndex, result);
    }

    @Test
    public void testToggleLastNumberSign_NullExpression() {
        String result = Utils.toggleLastNumberSign(null);
        assertNull(result);
    }

    @Test
    public void testToggleLastNumberSign_EmptyExpression() {
        String result = Utils.toggleLastNumberSign("");
        assertEquals("", result);
    }

    @Test
    public void testToggleLastNumberSign_ZeroExpression() {
        String result = Utils.toggleLastNumberSign(ENumber.ZERO.getValue());
        assertEquals(ENumber.ZERO.getValue(), result);
    }

    @Test
    public void testToggleLastNumberSign_NoLastNumber() {
        String expression = "2+3-";
        String result = Utils.toggleLastNumberSign(expression);
        assertEquals(expression, result);
    }

    @Test
    public void testToggleLastNumberSign_LastNumberPositive() {
        String expression = "2+3";
        String expected = "2+(-3)";
        String result = Utils.toggleLastNumberSign(expression);
        assertEquals(expected, result);
    }

    @Test
    public void testToggleLastNumberSign_LastNumberNegative() {
        String expression = "2+(-3)";
        String expected = "2+3";
        String result = Utils.toggleLastNumberSign(expression);
        assertEquals(expected, result);
    }

    @Test
    public void testToggleLastNumberSign_LastFunction() {
        String expression = "log(5)";
        String expected = "log(5)";
        String result = Utils.toggleLastNumberSign(expression);
        assertEquals(expected, result);
    }

    @Test
    public void testToggleLastNumberSign_FloatNumber() {
        String expression = "23+3.";
        String expected = "23+(-3.)";
        String result = Utils.toggleLastNumberSign(expression);
        assertEquals(expected, result);
    }

    @Test
    public void testToggleLastNumberSign_FloatNumber2() {
        String expression = "23+3.234";
        String expected = "23+(-3.234)";
        String result = Utils.toggleLastNumberSign(expression);
        assertEquals(expected, result);
    }

    @Test
    public void testDeleteCharacterOrFunction_NonNullExpressionWithLengthGreaterThan1() {
        String expression = "12345";
        String expectedOutput = "1234";
        String result = Utils.deleteCharacterOrFunction(expression);
        assertEquals(expectedOutput, result);
    }

    @Test
    public void testDeleteCharacterOrFunction_NonNullExpressionWithLengthEquals1() {
        String expression = "6";
        String expectedOutput = "0";
        String result = Utils.deleteCharacterOrFunction(expression);
        assertEquals(expectedOutput, result);
    }

    @Test
    public void testDeleteCharacterOrFunction_EndsWithFunction() {
        String expression = "sin(30)+";
        String expectedOutput = "sin(30)";
        String result = Utils.deleteCharacterOrFunction(expression);
        assertEquals(expectedOutput, result);
    }

    @Test
    public void testDeleteCharacterOrFunction_EndsWithFunction2() {
        String expression = "sin(";
        String expectedOutput = "0";
        String result = Utils.deleteCharacterOrFunction(expression);
        assertEquals(expectedOutput, result);
    }

    @Test
    public void testDeleteCharacterOrFunction_DoesNotEndWithFunction() {
        String expression = "2*3+";
        String expectedOutput = "2*3";
        String result = Utils.deleteCharacterOrFunction(expression);
        assertEquals(expectedOutput, result);
    }

    @Test
    public void testDeleteCharacterOrFunction_NullExpression() {
        String expression = null;
        String expectedOutput = "0";
        String result = Utils.deleteCharacterOrFunction(expression);
        assertEquals(expectedOutput, result);
    }

    @Test
    public void testDeleteCharacterOrFunction_ConsistingOfZero() {
        String expression = "0";
        String expectedOutput = "0";
        String result = Utils.deleteCharacterOrFunction(expression);
        assertEquals(expectedOutput, result);
    }

    @Test
    public void testDeleteCharacterOrFunction_EndsWithFunctionAndAdditionalCharacters() {
        String expression = "sqrt(16)+3";
        String expectedOutput = "sqrt(16)+";
        String result = Utils.deleteCharacterOrFunction(expression);
        assertEquals(expectedOutput, result);
    }

    @Test
    public void testDeleteCharacterOrFunction_EndsWithFunctionAndNoAdditionalCharacters() {
        String expression = "tan(45)";
        String expectedOutput = "tan(45";
        String result = Utils.deleteCharacterOrFunction(expression);
        assertEquals(expectedOutput, result);
    }

    @Test
    public void testDeleteCharacterOrFunction_EndsWithNumberButNotSingleDigit() {
        String expression = "12345*6";
        String expectedOutput = "12345*";
        String result = Utils.deleteCharacterOrFunction(expression);
        assertEquals(expectedOutput, result);
    }

    @Test
    public void testDeleteCharacterOrFunction_EndsWithZero() {
        String expression = "50/10";
        String expectedOutput = "50/1";
        String result = Utils.deleteCharacterOrFunction(expression);
        assertEquals(expectedOutput, result);
    }
}